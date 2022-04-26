package boco.controller.models;

import boco.models.http.ListingRequest;
import boco.models.http.ListingResponse;
import boco.models.http.UpdateListingRequest;
import boco.models.rental.Listing;
import boco.models.rental.Review;
import boco.service.profile.ProfileService;
import boco.service.rental.ListingService;
import boco.service.security.JwtUtil;
import io.jsonwebtoken.JwtParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/my")
public class AuthorizedController {
    private final ListingService listingService;
    private final ProfileService profileService;
    @Autowired
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthorizedController(ListingService listingService, ProfileService profileService, JwtUtil jwtUtil) {
        this.listingService = listingService;
        this.profileService = profileService;
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

    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> getMyReviews(
            @RequestParam int perPage,
            @RequestParam int page,
            @RequestHeader(name="Authorization") String token) {
        return profileService.getMyProfileReviews(token, perPage, page);
    }

}
