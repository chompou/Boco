package boco.service.rental;

import boco.component.Haversine;
import boco.component.SimilarStringSort;
import boco.model.http.rental.ListingRequest;
import boco.model.http.rental.ListingResponse;
import boco.model.http.rental.ReviewResponse;
import boco.model.http.rental.UpdateListingRequest;
import boco.model.profile.Profile;
import boco.model.rental.*;
import boco.repository.profile.ProfileRepository;
import boco.repository.rental.CategoryTypeRepository;
import boco.repository.rental.ImageRepository;
import boco.repository.rental.LeaseRepository;
import boco.repository.rental.ListingRepository;
import boco.service.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ListingService {
    private final ListingRepository listingRepository;
    private final ProfileRepository profileRepository;
    private final CategoryTypeRepository categoryTypeRepository;
    private final LeaseRepository leaseRepository;
    private final ImageRepository imageRepository;

    Logger logger = LoggerFactory.getLogger(ListingService.class);

    private JwtUtil jwtUtil;

    @Autowired
    public ListingService(ListingRepository listingRepository, ProfileRepository profileRepository, CategoryTypeRepository categoryTypeRepository, LeaseRepository leaseRepository, ImageRepository imageRepository, JwtUtil jwtUtil) {
        this.listingRepository = listingRepository;
        this.profileRepository = profileRepository;
        this.categoryTypeRepository = categoryTypeRepository;
        this.leaseRepository = leaseRepository;
        this.imageRepository = imageRepository;
        this.jwtUtil = jwtUtil;
    }


    public List<ListingResponse> getAllListings(){
        return convertListings(listingRepository.findAll());
    }

    /**
     * Gets a page of listingResponses that fulfills the requirements given.
     * @param page The page number of the search
     * @param perPage The number of listingResponses to be returned
     * @param search Requires the Listings to contain the search value in their name or description.
     *               Empty string if not used
     * @param sort The column we are sorting by.
     *             "id" if not used.
     * @param priceFrom The minimum price of item we are looking for.
     *                  -1 if not used.
     * @param priceTo The maximum price of item we are looking for.
     *                -1 if not used. priceTo and priceFrom must be used together.
     * @param category The category of items we are looking for
     *                 Empty string if not used.
     * @return A responseEntity with a list of listingresponses.
     */
    public ResponseEntity<List<ListingResponse>> getListings(int page, int perPage, String search, String sort, double priceFrom, double priceTo, String category, String location) {
        List<Listing> allListings = listingRepository.findAllByIsActiveTrue();

        // Filtering by Category
        if (!category.equals("")) {
            Optional<CategoryType> catTypeData = categoryTypeRepository.findCategoryTypeByNameEquals(category);
            if (!catTypeData.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            CategoryType finalCatType = catTypeData.get();
            allListings = allListings.stream()
                    .filter(l -> (l.getCategoryTypes().contains(finalCatType))).collect(Collectors.toList());
        }

        // Filtering by price
        double finalPriceTo = (priceTo == -1) ? Double.MAX_VALUE : priceTo;
        allListings = allListings.stream()
                .filter(l -> (l.getPrice() <= finalPriceTo && l.getPrice() >= priceFrom)).collect(Collectors.toList());

        // Search
        if (!search.equals(""))
            allListings = SimilarStringSort.searchListings(allListings, search, 40);

        // Sort
        if (sort.split(":").length != 2) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String sortBy = sort.split(":")[0];
        String sortDir = sort.split(":")[1];

        switch (sortBy) {
            case "distance":
                if (location == null || location.split(":").length != 2) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                // sort by distance
                return new ResponseEntity<>(sortListingsByDistance(page, perPage, location, allListings), HttpStatus.OK);
            case "rating":
                sortListingsByRating(allListings);
                break;
            case "lastChanged":
                sortListingsByLastChanged(allListings);
                break;
            case "id":
                sortListingsById(allListings);
                break;
            case "price":
                sortListingsByPrice(allListings);
                break;
            default:
                break;
        }

        if (!sortDir.equals("ASC")) Collections.reverse(allListings);

        List<Listing> listingsSublist = allListings.subList(page*perPage,
                Math.min((page+1)*perPage, allListings.size()));
        return new ResponseEntity<>(convertListings(listingsSublist), HttpStatus.OK);
    }

    /**
     * gets the reviews of a listing given by id.
     * @param listingId The id of the listing.
     * @param perPage The number of reviews to be returned.
     * @param page The page number to be returned
     * @return A list of reviewResponses
     */
    public ResponseEntity<List<ReviewResponse>> getListingReviews(Long listingId, int perPage, int page) {
        Optional<Listing> listingData = listingRepository.findById(listingId);

        if (listingData.isEmpty()) {
            logger.warn("listingId={} was not found.", listingId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Listing listing = listingData.get();
        List<Lease> listingLeases = listing.getLeases();

        if (listingLeases == null) return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);

        // Getting the reviews of the listings leases
        List<Review> reviews = new ArrayList<>();
        for (int i = 0; i < listingLeases.size(); i++) {
            Review  newReview = listingLeases.get(i).getItemReview();
            if (newReview != null)  reviews.add(newReview);
        }

        List<Review> reviewsSublist = reviews.subList(page*perPage, Math.min((page+1)*perPage, reviews.size()));
        return new ResponseEntity<>(ReviewService.convertReviews(reviewsSublist), HttpStatus.OK);
    }


    /**
     * Gets the listing response of a listing given its id.
     *
     * @param listingId The id of the listing we are looking for.
     * @return The listing response we are looking for.
     */
    public ResponseEntity<ListingResponse> getListingById(Long listingId){
        Optional<Listing> listingData = listingRepository.findById(listingId);
        if (listingData.isEmpty()) {
            logger.warn("listingId={} was not found.", listingId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Listing listing = listingData.get();
        return new ResponseEntity<>(new ListingResponse(listing), HttpStatus.OK);
    }

    /**
     * Creates a listing in the database based on the data from listingRequest.
     *
     * @param listingRequest Request containing listing data
     * @param multipartFile Image of listing
     * @param authHeader Authorization header. JWT token with "Bearer " prefix.
     * @return The created listing
     */
    public ResponseEntity<ListingResponse> createListing(ListingRequest listingRequest, MultipartFile multipartFile, String authHeader) {
        try {
            Profile profile = jwtUtil.extractProfileFromAuthHeader(authHeader);
            if (profile == null){
                logger.warn("Profile of token not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            // Saving the listing without images or categories
            Listing newListing = new Listing(listingRequest.getName(), listingRequest.getDescription(),
                    listingRequest.getIsActive(), listingRequest.getPrice(), listingRequest.getPriceType(),
                    profile);
            listingRepository.save(newListing);

            // Adding categories to new listing
            for (int i = 0; i<listingRequest.getCategoryNames().size(); i++){
                Optional<CategoryType> categoryType = categoryTypeRepository.findCategoryTypeByNameEquals(listingRequest.getCategoryNames().get(i));
                if (categoryType.isPresent()) newListing.getCategoryTypes().add(categoryType.get());
            }
            listingRepository.save(newListing);

            // Adding images to new listing
            Image image = new Image(multipartFile.getBytes(), newListing);
            Image savedImage = imageRepository.save(image);
            newListing.getImages().add(savedImage);
            Listing savedListing = listingRepository.save(newListing);

            return new ResponseEntity<>(new ListingResponse(savedListing), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating listing: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates a listing with the values in updateListingRequest. Values are updated even if
     * the new value is null.
     * Only the owner of the listing can update it
     *
     * @param updateListingRequest New values of listing
     * @param authHeader Authorization header. JWT token with "Bearer " prefix.
     * @return The saved listing
     */
    public ResponseEntity<ListingResponse> updateListing(UpdateListingRequest updateListingRequest, String authHeader) {
        Profile profile = jwtUtil.extractProfileFromAuthHeader(authHeader);
        if (profile == null){
            logger.warn("Profile of token not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<Listing> listingData = listingRepository.findById(updateListingRequest.getId());
        if (listingData.isEmpty()) {
            logger.warn("listingId={} was not found.", updateListingRequest.getId());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Listing listing = listingData.get();

        if (!isProfileListingOwner(listing, profile)){
            logger.debug("profileId is not the owner of listing.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Setting the new data
        // Update all values, even null from request?
        listing.setDescription(updateListingRequest.getDescription());
        listing.setIsActive(updateListingRequest.getIsActive());
        listing.setPrice(updateListingRequest.getPrice());
        listing.setPriceType(updateListingRequest.getPriceType());

        Listing savedListing = listingRepository.save(listing);
        logger.info("listingId={} was updated to: {}", updateListingRequest.getId(), savedListing);
        return new ResponseEntity<>(new ListingResponse(savedListing), HttpStatus.OK);
    }

    /**
     * Deletes a listing with listingId. Only the owner of the listing can delete it.
     *
     * @param listingId ID of the listing
     * @param authHeader Authorization header. JWT token with "Bearer " prefix.
     * @return Status indicating if the listing was successfully deleted
     */
    public ResponseEntity<HttpStatus> deleteListing(Long listingId, String authHeader) {
        try {
            Profile profile = jwtUtil.extractProfileFromAuthHeader(authHeader);
            if (profile == null){
                logger.warn("Profile of token not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Optional<Listing> listingData = listingRepository.findById(listingId);
            if (listingData.isEmpty()) {
                logger.debug("listingId={} was not found.", listingId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Listing listing = listingData.get();

            if (!isProfileListingOwner(listing, profile)){
                logger.debug("profileId is not the owner of listing.");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Optional<Listing> emptyListingData = listingRepository.findById(1L);
            if (emptyListingData.isEmpty()){
                logger.error("Error retrieving empty listing (listingId=1)");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Listing emptyListing = emptyListingData.get();

            setListingWhenDeleted(listingId, emptyListing);
            listingRepository.deleteById(listingId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error deleting listing: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void deleteListing(Listing listing) {
            Optional<Listing> emptyListing = listingRepository.findById(1L);
            if (emptyListing.isEmpty()) return;

        try {
            setListingWhenDeleted(listing.getId(), emptyListing.get());
            listingRepository.deleteById(listing.getId());
        } catch (Exception e) {
            logger.error("Error in ListingService.deleteListing: {}", e.getMessage());
        }
    }

    public void setListingWhenDeleted(Long listingId, Listing emptyListing){
        List<Lease> leases = leaseRepository.getLeasesByListing_Id(listingId);

        for (Lease lease : leases) {
            lease.setListing(emptyListing);
        }
        leaseRepository.saveAll(leases);

        List<Image> images = imageRepository.getImageByListing_Id(listingId);

        for (Image image : images) {
            image.setListing(emptyListing);
        }
        imageRepository.saveAll(images);
    }

    public static List<ListingResponse> convertListings(List<Listing> listings){
        List<ListingResponse> listingResponses = new ArrayList<>();
        for (Listing listing :
                listings) {
            listingResponses.add(new ListingResponse(listing));
        }
        return listingResponses;
    }

    private boolean isProfileListingOwner(Listing listing, Profile profile) {
        return listing.getProfile().getId().intValue() == profile.getId().intValue();
    }

    private List<ListingResponse> sortListingsByDistance(int page, int perPage, String location, List<Listing> listings) {
        double lat1 = Double.valueOf(location.split(":")[0]);
        double long1 = Double.valueOf(location.split(":")[1]);

        double lat2;
        double long2;
        List<ListingResponse> responses = new ArrayList<>();
        for (Listing listing: listings) {

            lat2 = Double.valueOf(listing.getProfile().getLocation().split(":")[0]);
            long2 = Double.valueOf(listing.getProfile().getLocation().split(":")[1]);
            double distance = Haversine.distance(lat1, long1, lat2, long2);
            responses.add(new ListingResponse(listing, distance));
        }

        Comparator<ListingResponse> distanceComp = Comparator.comparingDouble(ListingResponse::getDistance);
        Collections.sort(responses, distanceComp);
        return responses.subList(page*perPage, Math.min((page+1)*perPage, responses.size()));
    }

    private List<Listing> sortListingsByRating(List<Listing> listings) {
        var ratingComp = new Comparator<Listing>() {
            @Override
            public int compare(Listing l1, Listing l2) {
                if (l1.getRating() > l2.getRating()) return 1;
                if (l1.getRating() < l2.getRating()) return -1;
                return 0;
            }
        };
        listings.sort(ratingComp);
        return listings;
    }

    private List<Listing> sortListingsByLastChanged(List<Listing> listings) {
        var lastChangedComp = new Comparator<Listing>() {
            @Override
            public int compare(Listing l1, Listing l2) {
                if (l1.getLastChanged().after(l2.getLastChanged())) return 1;
                if (l1.getLastChanged().before(l2.getLastChanged())) return -1;
                return 0;
            }
        };
        listings.sort(lastChangedComp);
        return listings;
    }

    private List<Listing> sortListingsById(List<Listing> listings) {
        var idComp = new Comparator<Listing>() {
            @Override
            public int compare(Listing l1, Listing l2) {
                if (l1.getId() > l2.getId()) return 1;
                if (l1.getId() < l2.getId()) return -1;
                return 0;
            }
        };
        listings.sort(idComp);
        return listings;
    }

    private List<Listing> sortListingsByPrice(List<Listing> listings) {
        var priceComp = new Comparator<Listing>() {
            @Override
            public int compare(Listing l1, Listing l2) {
                if (l1.getPrice() > l2.getPrice()) return 1;
                if (l1.getPrice() < l2.getPrice()) return -1;
                return 0;
            }
        };
        listings.sort(priceComp);
        return listings;
    }

}
