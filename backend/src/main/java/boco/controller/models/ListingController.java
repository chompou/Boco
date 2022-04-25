package boco.controller.models;

import boco.models.rental.Listing;
import boco.service.profile.ProfileService;
import boco.service.rental.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listing")
public class ListingController {
    private final ListingService listingService;
    private final ProfileService profileService;

    @Autowired
    public ListingController(ListingService listingService, ProfileService profileService) {
        this.listingService = listingService;
        this.profileService = profileService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Listing>> getListings(@RequestParam int perPage,
                                     @RequestParam int page,
                                     @RequestParam(defaultValue  = "") String search,
                                     @RequestParam(defaultValue  = "id") String sort,
                                     @RequestParam(defaultValue  =  "-1") double priceFrom,
                                     @RequestParam(defaultValue  = "-1") double priceTo,
                                     @RequestParam(defaultValue  = "") String priceType,
                                     @RequestParam(required = false) Long profileId

    ){
        if (profileId != null){
            return profileService.getProfileListings(profileId, perPage, page);
        }

        return listingService.getListings(page, perPage, search, sort, priceFrom, priceTo, priceType);
    }

    @GetMapping("/{listing_id}")
    public ResponseEntity<Listing> getListing(@PathVariable(value = "listing_id") Long listingId){
        return listingService.getListingById(listingId);
    }



}
