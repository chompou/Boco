package boco.service.rental;

import boco.models.http.ListingRequest;
import boco.models.http.UpdateListingRequest;
import boco.models.profile.Profile;
import boco.models.rental.Lease;
import boco.models.rental.Listing;
import boco.models.rental.Review;
import boco.repository.profile.ProfileRepository;
import boco.repository.rental.ListingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final ProfileRepository profileRepository;

    Logger logger = LoggerFactory.getLogger(ListingService.class);

    @Autowired
    public ListingService(ListingRepository listingRepository, ProfileRepository profileRepository) {
        this.listingRepository = listingRepository;
        this.profileRepository = profileRepository;
    }


    public List<Listing> getAllListings(){
        return listingRepository.findAll();
    }

    public ResponseEntity<List<Listing>> getListings(int page, int size, String search, String sort, double priceFrom, double priceTo, String priceType){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort));

        List<Listing> listings;
        if (priceFrom == -1){
            listings = listingRepository.findByDescriptionContainingOrNameContaining(search, search, pageable).getContent();
        } else {
            listings = listingRepository.findByDescriptionContainingOrNameContainingAndPriceBetween(search, search, priceFrom, priceTo, pageable).getContent();
        }
        return new ResponseEntity<>(listings, HttpStatus.OK);
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

    public ResponseEntity<Listing> getListingById(Long listingId){
        Optional<Listing> listing = listingRepository.findById(listingId);
        if (!listing.isPresent()) {
            logger.debug("listingId=" + listingId + " was not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listing.get(), HttpStatus.OK);
    }

    public ResponseEntity<Listing> createListing(ListingRequest listingRequest) {
        try {
            Profile profileOfRequest = profileRepository.getById(listingRequest.getProfileId());
            Listing newListing = new Listing(listingRequest.getName(), listingRequest.getDescription(),
                    listingRequest.getCategory(), listingRequest.getAddress(), listingRequest.isAvailable(),
                    listingRequest.isActive(), listingRequest.getPrice(), listingRequest.getPriceType(),
                    profileOfRequest);
            Listing savedListing = listingRepository.save(newListing);
            return new ResponseEntity<>(savedListing, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Listing> updateListing(UpdateListingRequest updateListingRequest) {
        Optional<Listing> listingData = listingRepository.findById(updateListingRequest.getListingId());
        if (!listingData.isPresent()) {
            logger.debug("listingId=" + updateListingRequest.getListingId() + " was not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Setting the new data
        Listing listing = listingData.get();
        // Update all values, even null from request?
        listing.setDescription(updateListingRequest.getDescription());
        listing.setCategory(updateListingRequest.getCategory());
        listing.setAddress(updateListingRequest.getAddress());
        listing.setAvailable(updateListingRequest.isAvailable());
        listing.setActive(updateListingRequest.isActive());
        listing.setPrice(updateListingRequest.getPrice());
        listing.setPriceType(updateListingRequest.getPriceType());

        Listing savedListing = listingRepository.save(listing);
        logger.debug("listingId=" + updateListingRequest.getListingId() + " was updated to:\n" + savedListing);
        return new ResponseEntity<>(savedListing, HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> deleteListing(Long listingId) {
        try {
            listingRepository.deleteById(listingId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
