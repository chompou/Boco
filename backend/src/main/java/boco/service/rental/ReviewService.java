package boco.service.rental;

import boco.model.http.rental.ReviewResponse;
import boco.model.profile.Profile;
import boco.model.rental.Review;
import boco.repository.rental.ReviewRepository;
import boco.service.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

/**
 * Service responsible for operations on reviews
 */
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final JwtUtil jwtUtil;

    Logger logger = LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, JwtUtil jwtUtil) {
        this.reviewRepository = reviewRepository;
        this.jwtUtil = jwtUtil;
    }

    /** @return All reviews in database */
    public ResponseEntity<List<ReviewResponse>> getAllReviews(){
        return new ResponseEntity<>(convertReviews(reviewRepository.findAll()), HttpStatus.OK);
    }

    /**
     * Gets reviews written by profile of authHeader
     * @param authHeader Authorization header. JWT token with "Bearer " prefix.
     * @param perPage Number of reviews to return per page
     * @param page Page to return
     * @return
     */
    public ResponseEntity<List<ReviewResponse>> getMyWrittenReviews(String authHeader, int perPage, int page){
        Profile profile = jwtUtil.extractProfileFromAuthHeader(authHeader);
        if (profile == null){
            logger.warn("Profile of token not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Pageable pageable = PageRequest.of(page, perPage);
        List<Review> reviews = reviewRepository.getWhereWrittenByAuthor(profile.getId(), pageable).getContent();

        return new ResponseEntity<>(convertReviews(reviews), HttpStatus.OK);
    }

    public static List<ReviewResponse> convertReviews(List<Review> reviews){
        List<ReviewResponse> reviewResponse = new ArrayList<>();
        for (Review review : reviews) {
            reviewResponse.add(new ReviewResponse(review));
        }
        return reviewResponse;
    }
}
