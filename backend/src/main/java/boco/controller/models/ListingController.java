package boco.controller.models;

import boco.models.rental.Listing;
import boco.service.rental.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listing")
public class ListingController {
    private final ListingService listingService;

    @Autowired
    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @GetMapping("/")
    public List<Listing> getListings(@RequestParam int perPage,
                                  @RequestParam int page,
                                  @RequestParam(defaultValue  = "") String search,
                                  @RequestParam(defaultValue  = "id") String sort,
                                  @RequestParam(defaultValue  =  "-1") double priceFrom,
                                  @RequestParam(defaultValue  = "-1") double priceTo,
                                  @RequestParam(defaultValue  = "") String priceType
    ){

        return listingService.getListings(page, perPage, search, sort, priceFrom, priceTo, priceType).getContent();
    }

    @GetMapping("/profile/{profile_id}")
    public ResponseEntity<Listing> getListing(@PathVariable long id){
        return listingService.getListingById(id);
    }


}
