package boco.service.profile;

import boco.component.BocoHasher;
import boco.model.http.profile.*;
import boco.model.http.rental.ListingResultsResponse;
import boco.model.http.rental.ReviewResponse;
import boco.model.profile.PasswordCode;
import boco.model.profile.Personal;
import boco.model.profile.Professional;
import boco.model.profile.Profile;
import boco.model.rental.Lease;
import boco.model.rental.Listing;
import boco.model.rental.Review;
import boco.repository.profile.PasswordCodeRepository;
import boco.repository.profile.PersonalRepository;
import boco.repository.profile.ProfessionalRepository;
import boco.repository.profile.ProfileRepository;
import boco.repository.rental.LeaseRepository;
import boco.repository.rental.ListingRepository;
import boco.service.rental.ListingService;
import boco.service.rental.ReviewService;
import boco.service.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final PersonalRepository personalRepository;
    private final ProfessionalRepository professionalRepository;
    private final LeaseRepository leaseRepository;
    private final ListingRepository listingRepository;
    private final ListingService listingService;
    private final PasswordCodeRepository passwordCodeRepository;


    private final JwtUtil jwtUtil;

    Logger logger = LoggerFactory.getLogger(ProfileService.class);

    @Autowired
    public ProfileService(ProfileRepository profileRepository,
                          PersonalRepository personalRepository,
                          ProfessionalRepository professionalRepository,
                          LeaseRepository leaseRepository,
                          JwtUtil jwtUtil, ListingRepository listingRepository,
                          ListingService listingService, PasswordCodeRepository passwordCodeRepository) {
        this.profileRepository = profileRepository;
        this.personalRepository = personalRepository;
        this.professionalRepository = professionalRepository;
        this.leaseRepository = leaseRepository;
        this.jwtUtil = jwtUtil;
        this.listingRepository = listingRepository;
        this.passwordCodeRepository = passwordCodeRepository;
        this.listingService = listingService;
    }

    /**
     * Gets the public version of a profile. Sensitive information is excluded. Email and Tlf is only
     * included if the profile of authHeader and the profile of profileId are contacts (has a lease together).
     *
     * @param profileId ID of profile to get
     * @param authHeader Authorization header. JWT token with "Bearer " prefix.
     * @return The public version of profile
     */
    public ResponseEntity<PublicProfileResponse> getPublicProfile(Long profileId, String authHeader) {
        Long userId = null;
        if (authHeader != null){
            Profile authHeaderProfile = jwtUtil.extractProfileFromAuthHeader(authHeader);
            if (authHeaderProfile == null){
                logger.warn("Profile of token not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            userId = authHeaderProfile.getId();
        }

        var profileData = profileRepository.findById(profileId);
        if (profileData.isEmpty()) {
            logger.warn("profileId={} not found", profileId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Profile profile = profileData.get();

        PublicProfileResponse publicProfile = new PublicProfileResponse(profile);
        if (userId == null || !profileHasContactWithProfile(profileId, userId)){
            publicProfile.setEmail(null);
            publicProfile.setTlf(null);
        }
        return new ResponseEntity<>(publicProfile, HttpStatus.OK);
    }

    /**
     * Gets the private version of a profile. Sensitive information is included (but never password).
     * The profile is retrieved from JWT token
     *
     * @param authHeader Authorization header. JWT token with "Bearer " prefix.
     * @return The private version of profile
     */
    public ResponseEntity<PrivateProfileResponse> getPrivateProfile(String authHeader){
        Profile profile = jwtUtil.extractProfileFromAuthHeader(authHeader);
        if (profile == null){
            logger.warn("Profile of token not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new PrivateProfileResponse(profile), HttpStatus.OK);
    }


    public ResponseEntity<PrivateProfileResponse> createProfile(ProfileRequest profileRequest) {
        if (profileRequest == null) {
            logger.debug("Profile is null and could not be created");
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (checkIfProfileEmailExists(profileRequest.getEmail()) != null){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        if (checkIfProfileUsernameExists(profileRequest.getUsername()) != null){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        try {
            if (profileRequest.getIsPersonal()) {
                Personal p = new Personal(profileRequest.getUsername(), profileRequest.getEmail(),
                        profileRequest.getDescription(), profileRequest.getDisplayName(), profileRequest.getPasswordHash(),
                        profileRequest.getAddress(), profileRequest.getLocation(), profileRequest.getTlf());
                Personal savedProfile = personalRepository.save(p);
                logger.debug("Personal profile was saved: " + p);
                return new ResponseEntity<>(new PrivateProfileResponse(savedProfile), HttpStatus.CREATED);
            } else {
                Professional p = new Professional(profileRequest.getUsername(), profileRequest.getEmail(),
                        profileRequest.getDescription(), profileRequest.getDisplayName(), profileRequest.getPasswordHash(),
                        profileRequest.getAddress(), profileRequest.getLocation(), profileRequest.getTlf());
                Professional savedProfile = professionalRepository.save(p);
                logger.debug("Professional profile was saved: " + p);
                return new ResponseEntity<>(new PrivateProfileResponse(savedProfile), HttpStatus.CREATED);
            }

        } catch (Exception e) {
            logger.debug("Error when saving profile:\n" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ListingResultsResponse> getProfileListings(Long profileId, int perPage, int page){
        Optional<Profile> profileData = profileRepository.findById(profileId);

        if (!profileData.isPresent()){
            logger.debug("profileId=" + profileId + "was not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Listing> listingsByProfile = profileData.get().getListings();

        if (listingsByProfile == null) {
            logger.debug("listings is null for profileId=" + profileId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Listing> listings = new ArrayList<>(listingsByProfile).subList(page*perPage, Math.min((page+1)*perPage, listingsByProfile.size()));
        ListingResultsResponse listingResultsResponse = new ListingResultsResponse(ListingService.convertListings(listings), listingsByProfile.size());
        return new ResponseEntity<>(listingResultsResponse, HttpStatus.OK);

    }

    public ResponseEntity<List<ReviewResponse>> getReviewsAsOwner(Long profileId, int perPage, int page) {
        Optional<Profile> profileData = profileRepository.findById(profileId);

        if (!profileData.isPresent()) {
            logger.debug("profileId=" + profileId + " was not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Listing> listingsByProfile = profileData.get().getListings();

        List<Lease> leasesFromProfile = new ArrayList<>();
        for (int i = 0; i < listingsByProfile.size(); i++) {
            leasesFromProfile.addAll(listingsByProfile.get(i).getLeases());
        }

        List<Review> reviews = new ArrayList<>();
        for (int i = 0; i < leasesFromProfile.size(); i++) {
            Review newReview = leasesFromProfile.get(i).getOwnerReview();
            if (newReview != null) reviews.add(newReview);
        }

        List<Review> reviewsSublist = reviews.subList(page*perPage, Math.min((page+1)*perPage, reviews.size()));
        return new ResponseEntity<>(ReviewService.convertReviews(reviewsSublist), HttpStatus.OK);
    }

    public ResponseEntity<List<ReviewResponse>> getReviewsAsLeasee(Long profileId, int perPage, int page) {
       Optional<Profile> profileData = profileRepository.findProfileById(profileId);

        if (!profileData.isPresent()) {
            logger.debug("profile of token not found found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Lease> leases = leaseRepository.getLeasesByProfile_Id(profileId);

        if (leases == null) {
            logger.debug("leases is null");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Review> reviews = new ArrayList<>();
        for (int i = 0; i < leases.size(); i++) {
            Review newReview = leases.get(i).getLeaseeReview();
            if (newReview != null) reviews.add(newReview);
        }

        List<Review> reviewsSublist = reviews.subList(page*perPage, Math.min((page+1)*perPage, reviews.size()));
        return new ResponseEntity<>(ReviewService.convertReviews(reviewsSublist), HttpStatus.OK);
    }

    public ResponseEntity<PrivateProfileResponse> updateProfile(UpdateProfileRequest updateProfileRequest, String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        Optional<Profile> profileData = profileRepository.findProfileByUsername(username);

        if (!profileData.isPresent()){
            logger.debug("profileId=" + profileData.get().getId() + " was not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Setting the new data
        Profile profile = profileData.get();
        // Update all values, even null from request?
        if(updateProfileRequest.getEmail()!=null && !updateProfileRequest.getEmail().isEmpty()){
            profile.setEmail(updateProfileRequest.getEmail());
        }
        if (updateProfileRequest.getDescription()!=null && !updateProfileRequest.getEmail().isEmpty()){
            profile.setDescription(updateProfileRequest.getDescription());
        }
        if (updateProfileRequest.getDisplayName()!=null && !updateProfileRequest.getDisplayName().isEmpty()){
            profile.setDisplayName(updateProfileRequest.getDisplayName());
        }
        if (updateProfileRequest.getPasswordHash()!=null && !updateProfileRequest.getPasswordHash().isEmpty()){
            profile.setPasswordHash(BocoHasher.encode(updateProfileRequest.getPasswordHash()));
        }
        if (updateProfileRequest.getAddress()!=null && !updateProfileRequest.getAddress().isEmpty()){
            profile.setAddress(updateProfileRequest.getAddress());
        }
        if (updateProfileRequest.getTlf()!=null && !updateProfileRequest.getTlf().isEmpty()){
            profile.setTlf(updateProfileRequest.getTlf());
        }
        if (updateProfileRequest.getLocation()!=null && !updateProfileRequest.getLocation().isEmpty()){
            profile.setLocation(updateProfileRequest.getLocation());
        }


        Profile savedProfile = profileRepository.save(profile);
        logger.debug("profileId=" + profileData.get().getId() + " was updated to:\n" + savedProfile);
        return new ResponseEntity<>(new PrivateProfileResponse(savedProfile), HttpStatus.OK);
    }

    public ResponseEntity<PrivateProfileResponse> deactivateProfile(String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        Optional<Profile> profileData = profileRepository.findProfileByUsername(username);

        if (!profileData.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Profile profile = profileData.get();
        profile.setDeactivated(Timestamp.valueOf(LocalDateTime.now()));
        Profile savedProfile = profileRepository.save(profile);

        List<Listing> listings = listingRepository.getListingsByProfile(profileData.get());
        for (int i =0; i<listings.size(); i++){
            listings.get(i).setIsActive(false);
            listingRepository.save(listings.get(i));
        }

        List<Lease> leasesProfile = leaseRepository.getLeasesByProfile(profile);
        for (int i = 0; i<leasesProfile.size(); i++){
            if (leasesProfile.get(i).getIsApproved()== null){
                leasesProfile.get(i).setIsApproved(false);
                leaseRepository.save(leasesProfile.get(i));
            }
        }
        List<Lease> leasesOwner = leaseRepository.getLeasesByOwner(profile);
        for (int i = 0; i<leasesOwner.size(); i++){
            if (leasesOwner.get(i).getIsApproved()== null){
                leasesOwner.get(i).setIsApproved(false);
                leaseRepository.save(leasesOwner.get(i));
            }
        }
        return new ResponseEntity<>(new PrivateProfileResponse(savedProfile), HttpStatus.OK);
    }

    public ResponseEntity<Profile> verifyProfile(Long profileId){
        Optional<Profile> profileData = profileRepository.findProfileById(profileId);
        if (profileData.isPresent()){
            profileData.get().setIsVerified(true);
            Profile profile = profileRepository.save(profileData.get());
            return new ResponseEntity<>(profile, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    public ResponseEntity<Profile> checkIfProfileEmailExists(String email){
        Optional<Profile> profileData = profileRepository.findProfileByEmail(email);
        if (profileData.isPresent()){
             return new ResponseEntity<>(profileData.get(), HttpStatus.OK);
        }
        return null;
    }
    public ResponseEntity<Profile> checkIfProfileUsernameExists(String username){
        Optional<Profile> profileData = profileRepository.findProfileByUsername(username);
        if (profileData.isPresent()){
            return new ResponseEntity<>(profileData.get(), HttpStatus.OK);
        }
        return null;
    }

    public ResponseEntity<Profile> changePassword(UpdatePasswordRequest updatePasswordRequest, String email){
        if (updatePasswordRequest.getPasswordHash()== null || updatePasswordRequest.getPasswordHash().isEmpty()){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (checkIfProfileEmailExists(email) == null) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Profile profile = profileRepository.findProfileByEmail(email).get();
        PasswordCode passwordCode = passwordCodeRepository.findPasswordCodeByProfile(profile).get();
        if (updatePasswordRequest.getGeneratedCode().equals(passwordCode.getGeneratedCode())) {
            profile.setPasswordHash(BocoHasher.encode(updatePasswordRequest.getPasswordHash()));
            profileRepository.save(profile);
            passwordCodeRepository.delete(passwordCode);
            return new ResponseEntity<>(profile, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    /**
     * Profile A and profile B has contact if either:
     * - profile A has had a lease with profile B.
     * - profile B has had a lease with profile A.
     *
     * @param profileId1 ID of profile A
     * @param profileId2 ID of profile B
     * @return Boolean indicating if profile A has contact with profile B
     */
    private boolean profileHasContactWithProfile(long profileId1, long profileId2) {
        Optional<Profile> profile = profileRepository.getIfContact(profileId1, profileId2);
        return profile.isPresent();
    }

    public Long getProfileIdByUsername(String username){
        return profileRepository.findProfileByUsername(username).get().getId();
    }

    private void checkLeaseAndPrepareForDelete(Lease lease, Profile profile){
        Profile deletedUser = profileRepository.getOne(1l);
        Listing deletedListing = listingRepository.getOne(1l);
            if(lease.getOwner().getId() == profile.getId()){
                lease.setOwner(deletedUser);
                leaseRepository.save(lease);
            }
            if (lease.getProfile().getId() == profile.getId()){
                lease.setProfile(deletedUser);
                leaseRepository.save(lease);
            }
            if (lease.getListing().getProfile().getId() == profile.getId()){
                lease.setListing(deletedListing);
                leaseRepository.save(lease);
            }
            if (lease.getProfile().equals(deletedUser) && lease.getOwner().equals(deletedUser)){
                leaseRepository.delete(lease);
            }
    }

    public void deleteDeactivatedProfiles(){
        try {
            Long bufferTime = 1000L *60*60*24*90;
            Long deleteBeforeTime = System.currentTimeMillis() - bufferTime;
            Timestamp deleteBefore = new Timestamp(deleteBeforeTime);
            List<Profile> deactivated = profileRepository.getAllByDeactivatedBefore(deleteBefore);
            for (Profile p: deactivated) {
                if (p.getDeactivated().before(deleteBefore)){
                    for (Lease owned:p.getOwned()) {
                        checkLeaseAndPrepareForDelete(owned, p);
                    }
                    System.out.println("1 ok");
                    for (Lease rental:p.getRentals()) {
                        checkLeaseAndPrepareForDelete(rental, p);
                    }
                    List<Lease> hanging = leaseRepository.getLeasesByOwner(p);
                    hanging.addAll(leaseRepository.getLeasesByProfile(p));
                    for (Lease l:hanging) {
                        checkLeaseAndPrepareForDelete(l, p);
                    }
                    System.out.println("2 ok");
                    for (Listing l: p.getListings()) {
                        listingService.deleteListing(l);
                    }
                    Profile delete = profileRepository.getOne(p.getId());
                    profileRepository.delete(delete);
                    System.out.println("4 ok");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
