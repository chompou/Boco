package boco.controller.models;

import boco.models.http.ListingRequest;
import boco.models.http.UpdateListingRequest;
import boco.models.rental.Listing;
import boco.service.rental.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/my")
public class AuthorizedController {
    private final ListingService listingService;

    @Autowired
    public AuthorizedController(ListingService listingService) {
        this.listingService = listingService;
    }


    @PostMapping("/listing")
    public void createListing(@RequestBody ListingRequest listingRequest) {
        // TODO: Not allow users to create listings for each other (can be done through JWT token)
        listingService.createListing(listingRequest);
    }

    @PutMapping("/listing")
    public ResponseEntity<Listing> updateListing(@RequestBody UpdateListingRequest updateListingRequest) {
        return listingService.updateListing(updateListingRequest);
    }
}
