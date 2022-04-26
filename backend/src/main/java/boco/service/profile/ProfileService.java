package boco.service.profile;

import boco.models.http.ListingResponse;
import boco.models.http.ProfileRequest;
import boco.models.http.UpdatePasswordRequest;
import boco.models.profile.Personal;
import boco.models.profile.Professional;
import boco.models.profile.Profile;
import boco.models.rental.Lease;
import boco.models.rental.Listing;
import boco.models.rental.Review;
import boco.repository.profile.PersonalRepository;
import boco.repository.profile.ProfessionalRepository;
import boco.repository.profile.ProfileRepository;
import boco.service.rental.ListingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final PersonalRepository personalRepository;
    private final ProfessionalRepository professionalRepository;

    Logger logger = LoggerFactory.getLogger(ListingService.class);

    @Autowired
    public ProfileService(ProfileRepository profileRepository,
                          PersonalRepository personalRepository,
                          ProfessionalRepository professionalRepository) {
        this.profileRepository = profileRepository;
        this.personalRepository = personalRepository;
        this.professionalRepository = professionalRepository;
    }

    public ResponseEntity<Profile> getProfile(Long profileId, Long profileId2) {
        var profileData = profileRepository.findById(profileId);
        if (profileData.isPresent()) {
            Profile profile = profileData.get();
            profile.setPasswordHash(null);
            profile.setUsername(null);
            profile.setAddress(null);

            profile.setRatingProfile(null);
            profile.setRatingListing(null);
            profile.setRatingGiven(null);
            if (!profileHasContactWithProfile(profileId, profileId2)){
                profile.setEmail(null);
                profile.setTlf(null);
            }
            return new ResponseEntity<>(profile, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<Profile> createProfile(ProfileRequest profileRequest) {
        if (profileRequest == null) {
            logger.debug("Profile is null and could not be created");
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (!isProfileRequestValid(profileRequest)) {
            logger.debug("Profile is invalid and could not be created: " + profileRequest);
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        try {
            if (profileRequest.getIsPersonal()) {
                Personal p = new Personal(profileRequest.getUsername(), profileRequest.getEmail(),
                        profileRequest.getDescription(), profileRequest.getDisplayName(), profileRequest.getPasswordHash(),
                        profileRequest.getAddress(), profileRequest.getTlf());
                Personal savedProfile = personalRepository.save(p);

                logger.debug("Personal profile was saved: " + p);
                return new ResponseEntity<>(savedProfile, HttpStatus.CREATED);
            } else {
                Professional p = new Professional(profileRequest.getUsername(), profileRequest.getEmail(),
                        profileRequest.getDescription(), profileRequest.getDisplayName(), profileRequest.getPasswordHash(),
                        profileRequest.getAddress(), profileRequest.getTlf());
                Professional savedProfile = professionalRepository.save(p);

                logger.debug("Professional profile was saved: " + p);
                return new ResponseEntity<>(savedProfile, HttpStatus.CREATED);
            }

        } catch (Exception e) {
            logger.debug("Error when saving profile:\n" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<ListingResponse>> getProfileListings(Long profileId, int perPage, int page){
        Optional<Profile> profileData = profileRepository.findById(profileId);

        if (!profileData.isPresent()){
            logger.debug("profileId=" + profileId + "was not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Listing> listingsByProfile = profileData.get().getListings();
        List<Listing> listings = new ArrayList<>(listingsByProfile).subList((page-1)*perPage, Math.min(page*perPage, listingsByProfile.size()));
        return new ResponseEntity<>(ListingService.convertListings(listings), HttpStatus.OK);

    }

    public ResponseEntity<List<Review>> getProfileReviews(Long profileId, int perPage, int page) {
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
            reviews.add(leasesFromProfile.get(i).getOwnerReview());
        }

        List<Review> reviewsSublist = reviews.subList((page-1)*perPage, Math.min(page*perPage, reviews.size()));
        return new ResponseEntity<>(reviewsSublist, HttpStatus.OK);
    }

    public ResponseEntity<Profile> verifyProfile(Long profileId){
        Optional<Profile> profileData = profileRepository.findById(profileId);
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

    public ResponseEntity<Profile> changePassword(UpdatePasswordRequest updatePasswordRequest, String email) {
        if (checkIfProfileEmailExists(email) != null) {
            if (updatePasswordRequest.getPasswordHash2().equals(updatePasswordRequest.getPasswordHash1())) {
                Profile profile = checkIfProfileEmailExists(email).getBody();
                profile.setPasswordHash(updatePasswordRequest.getPasswordHash1());
                profileRepository.save(profile);
                return new ResponseEntity<>(profile, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    /**
     * @param profileRequest ProfileRequest to be verified
     * @return True if profile is valid, else false
     */
    private boolean isProfileRequestValid(ProfileRequest profileRequest) {
        // TODO: IMPLEMENT
        return true;
    }

    private boolean profileHasContactWithProfile(long profileId1, long profileId2) {
        Optional<Profile> profile = profileRepository.getIfContact(profileId1, profileId2);
        return profile.isPresent();
    }
}
