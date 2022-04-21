package boco.service.profile;

import boco.models.profile.Profile;
import boco.models.rental.Lease;
import boco.models.rental.Listing;
import boco.models.rental.Review;
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

    Logger logger = LoggerFactory.getLogger(ListingService.class);

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public ResponseEntity<Profile> getProfile(Long profileId) {
        var profileData = profileRepository.findById(profileId);
        if (profileData.isPresent()) {
            return new ResponseEntity<>(profileData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Profile> createProfile(Profile profile) {
        if (!isProfileValid(profile)) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        try {
            Profile savedProfile = profileRepository.save(profile);
            return new ResponseEntity<>(savedProfile, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
     * @param profile Profile to be verified
     * @return True if profile is valid, else false
     */
    private boolean isProfileValid(Profile profile) {
        // TODO: IMPLEMENT
        if (profile == null) return false;
        return true;
    }
}
