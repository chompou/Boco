package boco.service.rental;

import boco.models.http.ListingResponse;
import boco.models.http.ReviewResponse;
import boco.models.rental.Listing;
import boco.models.rental.Review;
import boco.repository.rental.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public ResponseEntity<List<ReviewResponse>> getAllReviews(){
        return new ResponseEntity<>(convertReviews(reviewRepository.findAll()), HttpStatus.OK);
    }

    public static List<ReviewResponse> convertReviews(List<Review> reviews){
        List<ReviewResponse> reviewResponse = new ArrayList<>();
        for (Review review :
                reviews) {
            reviewResponse.add(new ReviewResponse(review));
        }
        return reviewResponse;
    }
}
