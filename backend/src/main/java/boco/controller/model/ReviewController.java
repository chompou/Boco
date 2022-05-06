package boco.controller.model;

import boco.model.http.rental.ReviewResponse;
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

    /**
     * Gets the reviews of a listing or profile.
     * Params listing_id or profile_id can be added to find reviews for a given
     * listing or profile, but not both at once.
     *
     * @param listingId The optional id of the listing we are searching for reviews for.
     * @param profileId The optional id of the profile we are searching for reviews for
     * @param perPage The number of reviews to be returned
     * @param page The page number we are on
     * @return A responseEntity containing a list of reviews.
     */
    @GetMapping("")
    public ResponseEntity<List<ReviewResponse>> getReviews(@RequestParam(name = "listingId", defaultValue  = "") Long listingId,
                                                           @RequestParam(name = "profileId", defaultValue  = "") Long profileId,
                                                           @RequestParam(name = "perPage") int perPage,
                                                           @RequestParam(name = "page") int page,
                                                           @RequestParam(name = "reviewType") String reviewType){

        if (listingId != null && profileId != null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if(reviewType.equals("item")){
            return this.listingService.getListingReviews(listingId, perPage, page);
        } else if(reviewType.equals("owner")){
            return this.profileService.getReviewsAsOwner(profileId, perPage, page);
        } else if(reviewType.equals("leasee")){
            return this.profileService.getReviewsAsLeasee(profileId, perPage, page);
        }else{
            return reviewService.getAllReviews();
        }
    }

}
