package boco.controller.models;

import boco.models.rental.Listing;
import boco.service.rental.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public List<Listing> getNewLists(@RequestParam(name = "perPage") int perPage, @RequestParam(name = "page") int page){
        return listingService.getListingsByPage(page, perPage).getContent();
    }


}
