package boco.controller.models;

import boco.models.http.*;
import boco.service.profile.ProfileService;
import boco.service.rental.ListingService;
import boco.service.rental.ReviewService;
import boco.service.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/my")
public class AuthorizedController {
    private final ListingService listingService;
    private final ProfileService profileService;
    private final ReviewService reviewService;
    @Autowired
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthorizedController(ListingService listingService, ProfileService profileService, ReviewService reviewService, JwtUtil jwtUtil) {
        this.listingService = listingService;
        this.profileService = profileService;
        this.reviewService = reviewService;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/listing")
    public void createListing(@RequestBody ListingRequest listingRequest, @RequestHeader(name="Authorization") String token) {
        listingService.createListing(listingRequest, token);
    }

    @PutMapping("/listing")
    public ResponseEntity<ListingResponse> updateListing(@RequestBody UpdateListingRequest updateListingRequest, @RequestHeader(name="Authorization") String token) {
        return listingService.updateListing(updateListingRequest, token);
    }

    @DeleteMapping("/listing/{listing_id}")
    public ResponseEntity<HttpStatus> deleteListing(@PathVariable("listing_id") Long listingId,
                                                    @RequestHeader(name="Authorization") String token) {
        return listingService.deleteListing(listingId, token);
    }


    @GetMapping("/profile")
    public ResponseEntity<PrivateProfileResponse> getMyProfile(@RequestHeader(name="Authorization") String token){
        return profileService.getPrivateProfile(token);
    }

    @GetMapping("/review")
    public ResponseEntity<List<ReviewResponse>> getGivenReviews(@RequestParam(name = "perPage") int perPage,
                                                                @RequestParam(name = "page") int page,
                                                                @RequestHeader(name="Authorization") String token){

        return reviewService.getMyWrittenReviews(token, perPage, page);
    }



}
