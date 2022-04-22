package boco.service.profile;

import boco.models.http.ProfileRequest;
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
import org.springframework.stereotype.Service;

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

    public ResponseEntity<Profile> getProfile(Long profileId) {
        var profileData = profileRepository.findById(profileId);
        if (profileData.isPresent()) {
            Profile profile = profileData.get();
            if (profileHasContactWithProfile(1, 1)){
                profile.setPasswordHash(null);
                profile.setUsername(null);
                profile.setAddress(null);
                profile.setRatingProfile(null);
                profile.setRatingListing(null);
                profile.setRatingGiven(null);
            } else {
                profile.setUsername(null);
                profile.setEmail(null);
                profile.setPasswordHash(null);
                profile.setAddress(null);
                profile.setTlf(null);
                profile.setRatingProfile(null);
                profile.setRatingListing(null);
                profile.setRatingGiven(null);
            }
            return new ResponseEntity<>(profileData.get(), HttpStatus.OK);
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

    public ResponseEntity<List<Listing>> getProfileListings(Long profileId, int perPage, int page){
        Optional<Profile> profileData = profileRepository.findById(profileId);

        if (!profileData.isPresent()){
            logger.debug("profileId=" + profileId + "was not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Listing> listingsByProfile = profileData.get().getListings();
        List<Listing> listings = new ArrayList<>(listingsByProfile).subList((page-1)*perPage, Math.min(page*perPage, listingsByProfile.size()));
        return new ResponseEntity<>(listings, HttpStatus.OK);

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

    /**
     * @param profileRequest ProfileRequest to be verified
     * @return True if profile is valid, else false
     */
    private boolean isProfileRequestValid(ProfileRequest profileRequest) {
        // TODO: IMPLEMENT
        return true;
    }

    private boolean profileHasContactWithProfile(int profileId1, int profileId2) {
        // TODO: IMPLEMENT
        return true;
    }
}
