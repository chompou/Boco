package boco.controller.model;

import boco.model.http.rental.ListingResponse;
import boco.model.http.rental.ListingResultsResponse;
import boco.service.profile.ProfileService;
import boco.service.rental.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class defines endpoints for listing operations
 */
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

    /**
     * Gets listings based on search, sort and filters
     *
     * @param perPage Number of listings per page
     * @param page Page number
     * @param search String to search for
     * @param sort Sort type on format TYPE:DIRECTION.
     *             TYPE can be: distance/rating/lastChanged/id/price
     *             DIRECTION can be: ASC or DESC
     * @param location Location on format latitude:longitude
     * @param priceFrom Starting price for price-filter
     * @param priceTo Ending price for price-filter
     * @param category Category to get listings for
     * @param profileId ID of the profile to get listings of.
     *                  If this field is provided any other parameters will be ignored.
     * @return List of listings
     */
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

    /**
     * Gets listing of ID
     *
     * @param listingId ID of the listing
     * @return The listing
     */
    @GetMapping("/{listing_id}")
    public ResponseEntity<ListingResponse> getListing(@PathVariable(value = "listing_id") Long listingId){
        return listingService.getListingById(listingId);
    }
}
