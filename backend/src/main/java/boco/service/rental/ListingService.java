package boco.service.rental;

import boco.models.rental.Listing;
import boco.models.rental.Review;
import boco.repository.rental.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListingService {
    private final ListingRepository listingRepository;

    @Autowired
    public ListingService(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    public List<Listing> getAllListings(){
        return listingRepository.findAll();
    }

    public Page<Listing> getListingsByPage(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return listingRepository.findAll(pageable);
    }



    public ResponseEntity<List<Review>> getListingReviews(Long listingId) {
        var listingData = listingRepository.findById(listingId);

        if (!listingData.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<Review> reviews = new ArrayList<>();
        var listingLeases = listingData.get().getLeases();
        for (int i = 0; i < listingLeases.size(); i++) {
            reviews.add(listingLeases.get(i).getLeaseeReview());
        }
        return new ResponseEntity<List<Review>>(reviews, HttpStatus.OK);

    }
}
