package boco.controller.models;

import boco.models.rental.Review;
import boco.service.rental.ListingService;
import boco.service.rental.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final ListingService listingService;

    @Autowired
    public ReviewController(ReviewService reviewService, ListingService listingService) {
        this.reviewService = reviewService;
        this.listingService = listingService;
    }

    @GetMapping("/listing/{listing_id}")
    public ResponseEntity<List<Review>> getListingReviews(@PathVariable(value = "listing_id") Long listingId) {
        this.listingService.getListingReviews(listingId);
    }
}
