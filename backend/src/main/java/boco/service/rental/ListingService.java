package boco.service.rental;

import boco.models.rental.Lease;
import boco.models.rental.Listing;
import boco.models.rental.Review;
import boco.repository.rental.ListingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ListingService {
    private final ListingRepository listingRepository;

    Logger logger = LoggerFactory.getLogger(ListingService.class);

    @Autowired
    public ListingService(ListingRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    public List<Listing> getAllListings(){
        return listingRepository.findAll();
    }

    public Page<Listing> getListings(int page, int size, String search, String sort, double priceFrom, double priceTo, String priceType){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sort));

        if (priceFrom == -1){
            return listingRepository.findByDescriptionContainingOrNameContaining(search, search, pageable);
        } else {
            return null;
        }
    }



    public ResponseEntity<List<Review>> getListingReviews(Long listingId, int perPage, int page) {
        Optional<Listing> listingData = listingRepository.findById(listingId);

        if (!listingData.isPresent()) {
            logger.debug("listingId=" + listingId + " was not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Lease> listingLeases = listingData.get().getLeases();

        List<Review> reviews = new ArrayList<>();
        for (int i = 0; i < listingLeases.size(); i++) {
            reviews.add(listingLeases.get(i).getLeaseeReview());
        }

        List<Review> reviewsSublist = reviews.subList((page-1)*perPage, Math.min(page*perPage, reviews.size()));
        return new ResponseEntity<>(reviewsSublist, HttpStatus.OK);
    }
}
