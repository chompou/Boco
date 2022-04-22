package boco.controller.models;

import boco.models.http.ListingRequest;
import boco.models.rental.Listing;
import boco.service.rental.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        listingService.createListing(listingRequest);
        System.out.println(listingRequest);
    }
}
