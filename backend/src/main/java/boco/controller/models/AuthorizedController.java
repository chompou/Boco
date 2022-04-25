package boco.controller.models;

import boco.models.http.ListingRequest;
import boco.models.http.ListingResponse;
import boco.models.http.UpdateListingRequest;
import boco.models.rental.Listing;
import boco.service.rental.ListingService;
import boco.service.security.JwtUtil;
import io.jsonwebtoken.JwtParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/my")
public class AuthorizedController {
    private final ListingService listingService;
    @Autowired
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthorizedController(ListingService listingService, JwtUtil jwtUtil) {
        this.listingService = listingService;
        this.jwtUtil = null;
    }


    @PostMapping("/listing")
    public void createListing(@RequestBody ListingRequest listingRequest, HttpServletRequest request) {
        String username = jwtUtil.extractUsername(request.getHeader("Authorization").substring(7));
        // TODO: Not allow users to create listings for each other (can be done through JWT token)
        listingService.createListing(listingRequest);
    }

    @PutMapping("/listing")
    public ResponseEntity<ListingResponse> updateListing(@RequestBody UpdateListingRequest updateListingRequest) {
        return listingService.updateListing(updateListingRequest);
    }

    @DeleteMapping("/listing/{listing_id}")
    public ResponseEntity<HttpStatus> deleteListing(@PathVariable("listing_id") Long listingId) {
        return listingService.deleteListing(listingId);
    }

}
