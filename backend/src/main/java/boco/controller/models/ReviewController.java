package boco.controller.models;

import boco.models.rental.Review;
import boco.service.profile.ProfileService;
import boco.service.rental.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    private final ProfileService profileService;
    private final ListingService listingService;

    @Autowired
    public ReviewController(ProfileService profileService, ListingService listingService) {
        this.profileService = profileService;
        this.listingService = listingService;
    }

    /**
     * Gets reviews given to a listing defined by listing_id
     *
     * @param listingId ID of the listing
     * @param perPage Number of reviews per page (one page is returned for each request)
     * @param page The page to be returned. Example: page=1 would return the first perPage reviews
     * @return List of reviews
     */
    @GetMapping("/listing/{listing_id}")
    public ResponseEntity<List<Review>> getListingReviews(@PathVariable(value = "listing_id") Long listingId,
                                                          @RequestParam(name = "perPage") int perPage,
                                                          @RequestParam(name = "page") int page) {

        return this.listingService.getListingReviews(listingId, perPage, page);
    }

    /**
     * Gets reviews given to a profile defined by profile_id.
     *
     * @param profileId ID of the profile
     * @param perPage Number of reviews per page (one page is returned for each request)
     * @param page The page to be returned. Example: page=1 would return the first perPage reviews
     * @return List of reviews
     */
    @GetMapping("/profile/{profile_id}")
    public ResponseEntity<List<Review>> getProfileReviews(@PathVariable(value = "profile_id") Long profileId,
                                                          @RequestParam(name = "perPage") int perPage,
                                                          @RequestParam(name = "page") int page) {

        return this.profileService.getProfileReviews(profileId, perPage, page);
    }
}
