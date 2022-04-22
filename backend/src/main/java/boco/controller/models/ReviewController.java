package boco.controller.models;

import boco.models.rental.Review;
import boco.service.profile.ProfileService;
import boco.service.rental.ListingService;
import boco.service.rental.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    private final ProfileService profileService;
    private final ListingService listingService;
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ProfileService profileService, ListingService listingService, ReviewService reviewService) {
        this.profileService = profileService;
        this.listingService = listingService;
        this.reviewService = reviewService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Review>> getReviews(@RequestParam(name = "listing_id", defaultValue  = "") Long listingId,
                                                   @RequestParam(name = "profile_id", defaultValue  = "") Long profileId,
                                                   @RequestParam(name = "perPage") int perPage,
                                                   @RequestParam(name = "page") int page){

        if (listingId != null && profileId != null){
            //TODO
        } else if(listingId != null){
            return this.listingService.getListingReviews(listingId, perPage, page);
        } else if(profileId != null){
            return this.profileService.getProfileReviews(profileId, perPage, page);
        } else{
            return reviewService.getAllReviews();
        }

        return null;
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
