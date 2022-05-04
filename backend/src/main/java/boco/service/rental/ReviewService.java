package boco.service.rental;

import boco.model.http.ReviewResponse;
import boco.model.profile.Profile;
import boco.model.rental.Review;
import boco.repository.profile.ProfileRepository;
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
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProfileRepository profileRepository;

    @Autowired
    private JwtUtil jwtUtil;

    Logger logger = LoggerFactory.getLogger(ListingService.class);

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, JwtUtil jwtUtil, ProfileRepository profileRepository) {
        this.reviewRepository = reviewRepository;
        this.profileRepository = profileRepository;
        this.jwtUtil = jwtUtil;

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

    public ResponseEntity<List<ReviewResponse>> getMyWrittenReviews(String token, int perPage, int page){
        String username = jwtUtil.extractUsername(token.substring(7));
        Optional<Profile> profile = profileRepository.findProfileByUsername(username);

        if (!profile.isPresent()){
            logger.debug("profileId=" + profile.get().getId() + " was not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Long profileId = profile.get().getId();

        Pageable pageable = PageRequest.of(page, perPage);

        List<Review> reviews = reviewRepository.getWhereWrittenByAuthor(profileId, pageable).getContent();

        return new ResponseEntity<>(convertReviews(reviews), HttpStatus.OK);
    }
}
