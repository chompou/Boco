package boco.service.rental;

import boco.model.http.rental.LeaseRequest;
import boco.model.http.rental.LeaseResponse;
import boco.model.http.rental.ReviewRequest;
import boco.model.http.rental.UpdateLeaseRequest;
import boco.model.profile.Profile;
import boco.model.rental.Lease;
import boco.model.rental.Listing;
import boco.model.rental.Review;
import boco.repository.profile.ProfileRepository;
import boco.repository.rental.LeaseRepository;
import boco.repository.rental.ListingRepository;
import boco.repository.rental.ReviewRepository;
import boco.service.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type Lease service.
 */
@Service
public class LeaseService {
    private final LeaseRepository leaseRepository;
    private final ListingRepository listingRepository;
    private final ProfileRepository profileRepository;
    private final ReviewRepository reviewRepository;
    private final JwtUtil jwtUtil;

    /**
     * The Logger.
     */
    Logger logger = LoggerFactory.getLogger(LeaseService.class);

    /**
     * Instantiates a new Lease service.
     *
     * @param leaseRepository   the lease repository
     * @param listingRepository the listing repository
     * @param profileRepository the profile repository
     * @param reviewRepository  the review repository
     * @param jwtUtil           the jwt util
     */
    @Autowired
    public LeaseService(LeaseRepository leaseRepository, ListingRepository listingRepository,
                        ProfileRepository profileRepository, ReviewRepository reviewRepository,
                        JwtUtil jwtUtil) {
        this.leaseRepository = leaseRepository;
        this.listingRepository = listingRepository;
        this.profileRepository = profileRepository;
        this.reviewRepository = reviewRepository;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Gets the leases of the profile of the authHeader. Either leases on items (listings) owned
     * by profile or leases on items (listings) where the profile is the leasee
     *
     * @param authHeader Authorization header. JWT token with "Bearer " prefix.
     * @param isOwner    True: Get leases on items owned by profile.                False: Get leases on items (listings) where the profile is the leasee
     * @return List of lease responses
     */
    public ResponseEntity<List<LeaseResponse>> getMyLeases(String authHeader, Boolean isOwner) {
        try {
            Profile profile = jwtUtil.extractProfileFromAuthHeader(authHeader);
            if (profile == null){
                logger.warn("Profile of token not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<Lease> leases;
            if (Boolean.TRUE.equals(isOwner)) {
                leases = leaseRepository.getLeasesByOwner(profile);
            } else {
                leases = leaseRepository.getLeasesByProfile(profile);
            }

            return new ResponseEntity<>(convertLease(leases), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error when getting leases: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a lease based on leaseRequest fields and profile of authHeader
     *
     * @param leaseRequest Request containing fields for the lease to be created
     * @param authHeader   Authorization header. JWT token with "Bearer " prefix.
     * @return The created lease
     */
    public ResponseEntity<LeaseResponse> createLease(LeaseRequest leaseRequest, String authHeader) {
        try {
            Profile profile = jwtUtil.extractProfileFromAuthHeader(authHeader);
            if (profile == null){
                logger.warn("Profile of token not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Optional<Listing> listingData = listingRepository.findById(leaseRequest.getId());
            if (listingData.isEmpty()) {
                logger.warn("listingId={} was not found", leaseRequest.getId());
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Listing listing = listingData.get();

            Profile owner = listing.getProfile();

            Lease newLease = new Lease(leaseRequest.getFromDatetime(), leaseRequest.getToDatetime(),
                    profile, listing, owner);
            Lease savedLease = leaseRepository.save(newLease);

            return new ResponseEntity<>(new LeaseResponse(savedLease), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error when creating lease: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a lease
     *
     * @param leaseId    ID of the lease to be deleted
     * @param authHeader Authorization header. JWT token with "Bearer " prefix.
     * @return HTTP status of the deletion
     */
    public ResponseEntity<HttpStatus> deleteLease(Long leaseId, String authHeader) {
        try {
            Profile profile = jwtUtil.extractProfileFromAuthHeader(authHeader);
            if (profile == null){
                logger.warn("Profile of token not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Optional<Lease> leaseData = leaseRepository.findById(leaseId);
            if (leaseData.isEmpty()) {
                logger.warn("leaseId={} was not found.", leaseId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Lease lease = leaseData.get();

            if (Boolean.TRUE.equals(lease.getIsCompleted())) {
                logger.warn("leaseId={} is completed and cannot be deleted", leaseId);
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            }

            if (isLessThanDayBeforeLeaseStart(lease)) {
                logger.warn("There is less than 24 hours before the lease is set to start. " +
                        "Could not delete lease.");
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            }

            if (!isProfilePartOfLease(profile, lease)) {
                logger.warn("Profile of token is not part of lease.");
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            }

            leaseRepository.deleteById(leaseId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error when deleting lease: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates a lease
     *
     * @param updateLeaseRequest Request with new values of lease
     * @param authHeader         Authorization header. JWT token with "Bearer " prefix.
     * @return The updated leases
     */
    public ResponseEntity<LeaseResponse> updateLease(UpdateLeaseRequest updateLeaseRequest, String authHeader) {
        try {
            Optional<Lease> leaseData = leaseRepository.findById(updateLeaseRequest.getLeaseId());
            Lease lease = leaseData.get();
            // Setting the new data
            if (updateLeaseRequest.getIsApproved() != null){
                lease.setIsApproved(updateLeaseRequest.getIsApproved());
            }
            if (updateLeaseRequest.getIsCompleted() != null){
                lease.setIsCompleted(updateLeaseRequest.getIsCompleted());
            }

            Lease savedLease = leaseRepository.save(lease);
            logger.info("leaseId={} updated to: {}", updateLeaseRequest.getLeaseId(), lease);
            return new ResponseEntity<>(new LeaseResponse(savedLease), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error when updating lease: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Check if updating lease is legal response entity.
     *
     * @param updateLeaseRequest the update lease request
     * @param authHeader         the auth header
     * @return the response entity
     */
    public ResponseEntity<Boolean> checkIfUpdatingLeaseIsLegal(UpdateLeaseRequest updateLeaseRequest, String authHeader){
        Profile profile = jwtUtil.extractProfileFromAuthHeader(authHeader);
        if (profile == null){
            logger.warn("Profile of token not found");
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.NOT_FOUND);
        }

        Optional<Lease> leaseData = leaseRepository.findById(updateLeaseRequest.getLeaseId());
        if (leaseData.isEmpty()) {
            logger.warn("leaseId={} was not found.", updateLeaseRequest.getLeaseId());
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.NOT_FOUND);
        }
        Lease lease = leaseData.get();

        if (!isProfileOwnerOfLease(profile, lease)) {
            logger.warn("Profile of token not owner of lease");
            return new ResponseEntity<>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
        }

        if (updateLeaseRequest.getIsApproved() != null && updateLeaseRequest.getIsApproved().equals(true) && !updateLeaseRequest.getIsCompleted()) {
            List<Lease> leases = getOverlappingLeases(lease);
            if (leases.size() != 0) {
                logger.warn("Lease overlaps with other leases");
                return new ResponseEntity<>(Boolean.FALSE, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    /**
     * Get overlapping leases list.
     *
     * @param lease the lease
     * @return the list
     */
    public List<Lease> getOverlappingLeases(Lease lease){
        List<Lease> leases = leaseRepository.getLeasesByListing_IdAndIsApprovedIsTrue(lease.getListing().getId());
        return leases.stream()
                .filter(lease1 -> (lease1.getToDatetime() >= lease.getFromDatetime() && lease1.getToDatetime() <= lease.getToDatetime()) || (lease1.getFromDatetime() <= lease.getToDatetime() && lease1.getToDatetime() >= lease.getToDatetime()))
                .collect(Collectors.toList());
    }

    /**
     * Add a review to a lease relationship between an item owner and leasee. Either
     * the item, owner of item or leasee is reviewed.
     *
     * @param reviewRequest Review to be added along. Also includes the leaseId to add review to
     * @param reviewType    Type of review: owner/item/leasee
     * @param authHeader    Authorization header. JWT token with "Bearer " prefix.
     * @return The updated lease
     */
    public ResponseEntity<LeaseResponse> createLeaseReview(ReviewRequest reviewRequest, String reviewType, String authHeader) {
        Profile profile = jwtUtil.extractProfileFromAuthHeader(authHeader);
        if (profile == null){
            logger.warn("Profile of token not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<Lease> leaseData = leaseRepository.findById(reviewRequest.getLeaseId());
        if (leaseData.isEmpty()) {
            logger.warn("leaseId={} was not found.", reviewRequest.getLeaseId());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Lease lease = leaseData.get();

        Review newReview = new Review(reviewRequest.getRating(), reviewRequest.getComment());
        newReview.setLease(lease);
        Lease savedLease;

        if (reviewType.equals("owner")) {
            if (!isProfileLeaseeOfLease(profile, lease)) {
                logger.warn("Profile writing a owner-review is not the leasee");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            reviewRepository.save(newReview);
            lease.setOwnerReview(newReview);
            savedLease = leaseRepository.save(lease);
            Profile owner = lease.getOwner();
            owner.setRatingAsOwner(reviewRepository.getOwnerRating(owner.getId()));
            profileRepository.save(owner);

        } else if (reviewType.equals("leasee")) {
            if (!isProfileOwnerOfLease(profile, lease)) {
                logger.warn("Profile writing a leasee-review is not the lease owner");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            reviewRepository.save(newReview);
            lease.setLeaseeReview(newReview);
            savedLease = leaseRepository.save(lease);
            Profile leasee = lease.getProfile();
            leasee.setRatingAsLeasee(reviewRepository.getLeaseeRating(leasee.getId()));
            profileRepository.save(leasee);

        } else if (reviewType.equals("item")) {
            if (!isProfileLeaseeOfLease(profile, lease)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            reviewRepository.save(newReview);
            lease.setItemReview(newReview);
            savedLease = leaseRepository.save(lease);
            Listing listing = lease.getListing();
            listing.setRating(reviewRepository.getItemRating(listing.getId()));
            listingRepository.save(listing);
            Profile owner = lease.getOwner();
            owner.setRatingListing(reviewRepository.getAverageItemRatingForProfile(owner.getId()));
            profileRepository.save(owner);

        } else {
            logger.warn("reviewType={} does not match owner/leasee/item", reviewType);
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(new LeaseResponse(savedLease), HttpStatus.OK);
    }

    /**
     * Convert lease list.
     *
     * @param leases the leases
     * @return the list
     */
    public static List<LeaseResponse> convertLease(List<Lease> leases){
        List<LeaseResponse> leaseResponses = new ArrayList<>();
        for (Lease lease :
                leases) {
            leaseResponses.add(new LeaseResponse(lease));
        }
        return leaseResponses;
    }

    private boolean isLessThanDayBeforeLeaseStart(Lease lease) {
        return (lease.getFromDatetime() + (3600*1000*24)) > lease.getToDatetime();
    }

    private boolean isProfilePartOfLease(Profile profile, Lease lease) {
        return (lease.getProfile().getId().longValue() == profile.getId().longValue()) ||
                (lease.getOwner().getId().longValue() == profile.getId().longValue());
    }

    private boolean isProfileOwnerOfLease(Profile profile, Lease lease){
        return (lease.getOwner().getId().longValue() == profile.getId().longValue());
    }
    private boolean isProfileLeaseeOfLease(Profile profile, Lease lease) {
        return profile.getId().longValue() == lease.getProfile().getId().longValue();
    }

    /**
     * Removes overdue leases that have not been approved, waits one week.
     */
    public void removeDangling() {
        Date aWeekAgo = new Date(new Date().getTime() - (1000*60*60*24*7));
        List<Lease> leases = leaseRepository.findAll();
        for (Lease lease:leases) {
            if (!Boolean.TRUE.equals(lease.getIsApproved()) && new Date(lease.getToDatetime()).before(aWeekAgo)){
                try {
                    leaseRepository.delete(lease);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
