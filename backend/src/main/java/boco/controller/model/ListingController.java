package boco.controller.model;

import boco.model.http.rental.ListingResponse;
import boco.model.http.rental.ListingResultsResponse;
import boco.service.profile.ProfileService;
import boco.service.rental.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("")
    public ResponseEntity<ListingResultsResponse> getListings(@RequestParam int perPage,
                                                              @RequestParam int page,
                                                              @RequestParam(defaultValue  = "") String search,
                                                              @RequestParam(defaultValue  = "id:DESC") String sort,
                                                              @RequestParam(defaultValue = "") String location, // latitude:longitude
                                                              @RequestParam(defaultValue  =  "-1") double priceFrom,
                                                              @RequestParam(defaultValue  = "-1") double priceTo,
                                                              @RequestParam(defaultValue = "") String category,
                                                              @RequestParam(required = false) Long profileId

    ){
        if (profileId != null){
            return profileService.getProfileListings(profileId, perPage, page);
        }

        return listingService.getListings(page, perPage, search, sort, priceFrom, priceTo, category, location);
    }

    @GetMapping("/{listing_id}")
    public ResponseEntity<ListingResponse> getListing(@PathVariable(value = "listing_id") Long listingId){
        return listingService.getListingById(listingId);
    }



}
