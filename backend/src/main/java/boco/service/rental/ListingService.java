package boco.service.rental;

import boco.models.http.ListingRequest;
import boco.models.http.ListingResponse;
import boco.models.http.ReviewResponse;
import boco.models.http.UpdateListingRequest;
import boco.models.profile.Profile;
import boco.models.rental.Lease;
import boco.models.rental.Listing;
import boco.models.rental.Review;
import boco.repository.profile.ProfileRepository;
import boco.repository.rental.ListingRepository;
import boco.service.security.JwtUtil;
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
    private JwtUtil jwtUtil;

    @Autowired
    public ListingService(ListingRepository listingRepository, ProfileRepository profileRepository) {
        this.listingRepository = listingRepository;
        this.profileRepository = profileRepository;
    }


    public List<ListingResponse> getAllListings(){
        return convertListings(listingRepository.findAll());
    }

    public ResponseEntity<List<ListingResponse>> getListings(int page, int size, String search, String sort, double priceFrom, double priceTo, String priceType){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sort));

        List<Listing> listings;
        if (priceFrom == -1){
            listings = listingRepository.findByDescriptionContainingOrNameContaining(search, search, pageable).getContent();
        } else {
            listings = listingRepository.findByDescriptionContainingOrNameContainingAndPriceBetween(search, search, priceFrom, priceTo, pageable).getContent();
        }
        return new ResponseEntity<>(convertListings(listings), HttpStatus.OK);
    }



    public ResponseEntity<List<ReviewResponse>> getListingReviews(Long listingId, int perPage, int page) {
        Optional<Listing> listingData = listingRepository.findById(listingId);

        if (!listingData.isPresent()) {
            logger.debug("listingId=" + listingId + " was not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Lease> listingLeases = listingData.get().getLeases();

        if (listingLeases == null) {
            logger.debug("leases is null for listingId=" + listingId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Review> reviews = new ArrayList<>();
        for (int i = 0; i < listingLeases.size(); i++) {
            Review  newReview = listingLeases.get(i).getItemReview();
            if (newReview != null)  reviews.add(newReview);
        }

        List<Review> reviewsSublist = reviews.subList(page*perPage, Math.min((page+1)*perPage, reviews.size()));
        return new ResponseEntity<>(ReviewService.convertReviews(reviewsSublist), HttpStatus.OK);
    }

    public ResponseEntity<ListingResponse> getListingById(Long listingId){
        Optional<Listing> listing = listingRepository.findById(listingId);
        if (!listing.isPresent()) {
            logger.debug("listingId=" + listingId + " was not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ListingResponse(listing.get()), HttpStatus.OK);
    }

    public ResponseEntity<ListingResponse> createListing(ListingRequest listingRequest, String token) {
        try {
            String username = jwtUtil.extractUsername(token.substring(7));
            Optional<Profile> profile = profileRepository.findProfileByUsername(username);
            if (!profile.isPresent()) {
                logger.debug("profile of token not found found.");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Listing newListing = new Listing(listingRequest.getName(), listingRequest.getDescription(),
                    listingRequest.getAddress(), listingRequest.isAvailable(),
                    listingRequest.isActive(), listingRequest.getPrice(), listingRequest.getPriceType(),
                    profile.get());
            Listing savedListing = listingRepository.save(newListing);
            return new ResponseEntity<>(new ListingResponse(savedListing), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ListingResponse> updateListing(UpdateListingRequest updateListingRequest, String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        Optional<Profile> profile = profileRepository.findProfileByUsername(username);

        if (!profile.isPresent()){
            logger.debug("profileId=" + profile.get().getId() + " was not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<Listing> listingData = listingRepository.findById(updateListingRequest.getListingId());

        if (!listingData.isPresent()) {
            logger.debug("listingId=" + updateListingRequest.getListingId() + " was not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (listingData.get().getProfile().getId() != profile.get().getId()){
            logger.debug("UserId is not the owner of listing.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }



        // Setting the new data
        Listing listing = listingData.get();
        // Update all values, even null from request?
        listing.setDescription(updateListingRequest.getDescription());
        listing.setAddress(updateListingRequest.getAddress());
        listing.setAvailable(updateListingRequest.isAvailable());
        listing.setActive(updateListingRequest.isActive());
        listing.setPrice(updateListingRequest.getPrice());
        listing.setPriceType(updateListingRequest.getPriceType());

        Listing savedListing = listingRepository.save(listing);
        logger.debug("listingId=" + updateListingRequest.getListingId() + " was updated to:\n" + savedListing);
        return new ResponseEntity<>(new ListingResponse(savedListing), HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> deleteListing(Long listingId, String token) {
        try {
            String username = jwtUtil.extractUsername(token.substring(7));
            Optional<Profile> profile = profileRepository.findProfileByUsername(username);

            if (!profile.isPresent()){
                logger.debug("profileId=" + profile.get().getId() + " was not found.");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Optional<Listing> listingData = listingRepository.findById(listingId);

            if (!listingData.isPresent()) {
                logger.debug("listingId=" + listingId + " was not found.");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            if (listingData.get().getProfile().getId() != profile.get().getId()){
                logger.debug("UserId is not the owner of listing.");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            listingRepository.deleteById(listingId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static List<ListingResponse> convertListings(List<Listing> listings){
        List<ListingResponse> listingResponses = new ArrayList<>();
        for (Listing listing :
                listings) {
            listingResponses.add(new ListingResponse(listing));
        }
        return listingResponses;
    }
}
