package boco.service.rental;

import boco.component.Haversine;
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
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<List<ListingResponse>> getListings(int page, int perPage, String search, String sort, double priceFrom, double priceTo, String category, String location){
        boolean distanceSort = false;
        CategoryType catType = null;
        if (!category.equals("")){
            Optional<CategoryType> catTypeData = categoryTypeRepository.findCategoryTypeByNameEquals(category);
            if (!catTypeData.isPresent()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            catType = catTypeData.get();
        }

        if (priceTo == -1){
            priceTo = Double.MAX_VALUE;
        }
        if (sort.split(":").length != 2){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //TODO Validate sort name
        String sortBy = sort.split(":")[0];
        String sortDir = sort.split(":")[1];


        if (sortBy.equals("distance")){
            distanceSort = true;
            sortBy = "id";
        }


        List<Listing> listings = listingRepository.getListingByPriceRange(priceFrom, priceTo, Sort.by(sortBy).ascending());

        if (!category.equals("")){
            CategoryType finalCatType = catType;
            listings = listings.stream().filter(l -> (l.getCategoryTypes().contains(finalCatType))).collect(Collectors.toList());
        }

        if (!search.equals("")){
            //TODO add more advanced searching
            listings = listings.stream().filter(l -> (l.getName().contains(search) || l.getDescription().contains(search))).collect(Collectors.toList());
        }


        if (distanceSort){
            if (location == null || location.split(":").length != 2){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
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
            for (int i = 0; i < responses.size(); i++) {
                System.out.println(i + ". Distance: " + responses.get(i).getDistance());
            }
            return new ResponseEntity<>(responses, HttpStatus.OK);
        }


        if (!sortDir.equals("ASC")){
            Collections.reverse(listings);
        }

        List<Listing> listingsSublist = listings.subList(page*perPage, Math.min((page+1)*perPage, listings.size()));
        return new ResponseEntity<>(convertListings(listingsSublist), HttpStatus.OK);
    }


    /**
     * gets the reviews of a listing given by Id.
     * @param listingId The id of the listing.
     * @param perPage The number of reviews to be returned.
     * @param page The page number to be returned
     * @return A list of reviewResponses
     */
    public ResponseEntity<List<ReviewResponse>> getListingReviews(Long listingId, int perPage, int page) {
        Optional<Listing> listingData = listingRepository.findById(listingId);

        if (!listingData.isPresent()) {
            logger.debug("listingId=" + listingId + " was not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (listingData.get().getLeases() == null) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }

        List<Lease> listingLeases = listingData.get().getLeases();

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
     * @param listingId The id of the listing we are looking for.
     * @return The listing response we are looking for.
     */
    public ResponseEntity<ListingResponse> getListingById(Long listingId){
        Optional<Listing> listing = listingRepository.findById(listingId);
        if (!listing.isPresent()) {
            logger.debug("listingId=" + listingId + " was not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ListingResponse(listing.get()), HttpStatus.OK);
    }

    public ResponseEntity<ListingResponse> createListing(ListingRequest listingRequest, MultipartFile multipartFile, String token) {
        try {
            String username = jwtUtil.extractUsername(token.substring(7));
            Optional<Profile> profile = profileRepository.findProfileByUsername(username);
            if (!profile.isPresent()) {
                logger.debug("profile of token not found found.");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Listing newListing = new Listing(listingRequest.getName(), listingRequest.getDescription(),
                    listingRequest.getIsActive(), listingRequest.getPrice(), listingRequest.getPriceType(),
                    profile.get());
            listingRepository.save(newListing);
            System.out.println(listingRequest.getCategoryNames().size());
            for (int i = 0; i<listingRequest.getCategoryNames().size(); i++){
                Optional<CategoryType> categoryType = categoryTypeRepository.findCategoryTypeByNameEquals(listingRequest.getCategoryNames().get(i));
                if (categoryType.isPresent()){
                    newListing.getCategoryTypes().add(categoryType.get());
                }
            }
            listingRepository.save(newListing);
            Image image = new Image(multipartFile.getBytes(), newListing);
            Image savedImage = imageRepository.save(image);
            newListing.getImages().add(savedImage);
            Listing savedListing = listingRepository.save(newListing);
            return new ResponseEntity<>(new ListingResponse(savedListing), HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
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

        Optional<Listing> listingData = listingRepository.findById(updateListingRequest.getId());

        if (!listingData.isPresent()) {
            logger.debug("listingId=" + updateListingRequest.getId() + " was not found.");
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
        listing.setIsActive(updateListingRequest.getIsActive());
        listing.setPrice(updateListingRequest.getPrice());
        listing.setPriceType(updateListingRequest.getPriceType());

        Listing savedListing = listingRepository.save(listing);
        logger.debug("listingId=" + updateListingRequest.getId() + " was updated to:\n" + savedListing);
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


            System.out.println("Your id: " + profile.get().getId() + ", database id: " + listingData.get().getProfile().getId());

            Optional<Listing> emptyListing = listingRepository.findById(1L);
            if (!emptyListing.isPresent()){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            setListingWhenDeleted(listingId, emptyListing.get());
            listingRepository.deleteById(listingId);
            System.out.println("Updated stuff" );
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void deleteListing(Listing listing) {
        try {
            Optional<Listing> listingData = listingRepository.findById(listing.getId());
            Optional<Listing> emptyListing = listingRepository.findById(1L);
            if (!emptyListing.isPresent()){
                return;
            }
            setListingWhenDeleted(listing.getId(), emptyListing.get());
            listingRepository.deleteById(listing.getId());
        } catch (Exception ignored) {
        }
    }

    public void setListingWhenDeleted(Long listingId, Listing emptyListing){
        List<Lease> leases = leaseRepository.getLeasesByListing_Id(listingId);

        for (Lease lease :
                leases) {
            lease.setListing(emptyListing);
        }
        leaseRepository.saveAll(leases);

        List<Image> images = imageRepository.getImageByListing_Id(listingId);

        for (Image image :
                images) {
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

}
