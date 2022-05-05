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

@Service
public class LeaseService {
    private final LeaseRepository leaseRepository;
    private final ListingRepository listingRepository;
    private final ProfileRepository profileRepository;
    private final ReviewRepository reviewRepository;
    private final JwtUtil jwtUtil;

    Logger logger = LoggerFactory.getLogger(LeaseService.class);

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

    public ResponseEntity<List<LeaseResponse>> getMyLeases(String token, Boolean isOwner) {
        try {
            String username = jwtUtil.extractUsername(token.substring(7));
            Optional<Profile> profileData = profileRepository.findProfileByUsername(username);
            if (!profileData.isPresent()) {
                logger.debug("profile of token not found found.");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Profile profile = profileData.get();

            List<Lease> leases;
            if (isOwner) {
                leases = leaseRepository.getLeasesByOwner(profile);
            } else {
                leases = leaseRepository.getLeasesByProfile(profile);
            }
            return new ResponseEntity<>(convertLease(leases), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<LeaseResponse> createLease(LeaseRequest leaseRequest, String token) {
        try {
            // The person creating the lease is the person interested in the listing (not the listing owner)
            String username = jwtUtil.extractUsername(token.substring(7));
            Optional<Profile> profileData = profileRepository.findProfileByUsername(username);
            if (!profileData.isPresent()) {
                logger.debug("profile of token not found found.");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Optional<Listing> listingData = listingRepository.findById(leaseRequest.getId());
            if (!listingData.isPresent()) {
                logger.debug("listingId=" + leaseRequest.getId() + " was not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Listing listing = listingData.get();
            Profile profile = profileData.get();
            Profile owner = listing.getProfile();

            Lease newLease = new Lease(leaseRequest.getFromDatetime(), leaseRequest.getToDatetime(),
                    profile, listing, owner);
            Lease savedLease = leaseRepository.save(newLease);

            return new ResponseEntity<>(new LeaseResponse(savedLease), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    public ResponseEntity<HttpStatus> deleteLease(Long leaseId, String token) {
        try {
            String username = jwtUtil.extractUsername(token.substring(7));
            Optional<Profile> profileData = profileRepository.findProfileByUsername(username);

            if (!profileData.isPresent()){
                logger.debug("profile of token was not found.");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Profile profile = profileData.get();

            Optional<Lease> leaseData = leaseRepository.findById(leaseId);
            if (!leaseData.isPresent()) {
                logger.debug("leaseId=" + leaseId + " was not found.");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Lease lease = leaseData.get();

            if (lease.getIsCompleted()) {
                logger.debug("leaseId=" + leaseId + " is completed and cannot be deleted");
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            }

            if (profileIsOwnerOfLease(profile, lease)){
                leaseRepository.deleteById(leaseId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }


            if (isLessThanDayBeforeLeaseStart(lease)) {
                logger.debug("There is less than 24 hours before " + lease.getFromDatetime().toString()
                        + ", could not delete lease.");
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            }

            if (!isProfilePartOfLease(profile, lease)) {
                logger.debug("profileId=" + profile.getId() +
                        " could not delete lease with profileId=" +
                        lease.getProfile().getId() + " and ownerId=" + lease.getOwner().getId());
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            }

            leaseRepository.deleteById(leaseId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<LeaseResponse> updateLease(UpdateLeaseRequest updateLeaseRequest, String token) {
        try {
            String username = jwtUtil.extractUsername(token.substring(7));
            Optional<Profile> profileData = profileRepository.findProfileByUsername(username);

            if (!profileData.isPresent()) {
                logger.debug("profile of request was not found.");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Profile profile = profileData.get();

            Optional<Lease> leaseData = leaseRepository.findById(updateLeaseRequest.getLeaseId());

            if (!leaseData.isPresent()) {
                logger.debug("leaseId=" + updateLeaseRequest.getLeaseId() + " was not found.");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Lease lease = leaseData.get();

            if (lease.getOwner().getId() != profile.getId()) { // Owner of lease is not the profile trying to update
                logger.debug("profileId is not the owner of listing.");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }


            // Setting the new data
            lease.setIsApproved(updateLeaseRequest.getIsApproved());
            lease.setIsCompleted(updateLeaseRequest.getIsCompleted());

            Lease savedLease = leaseRepository.save(lease);
            logger.debug("leaseId=" + updateLeaseRequest.getLeaseId() + " was updated to:\n" + lease);
            return new ResponseEntity<>(new LeaseResponse(savedLease), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<LeaseResponse> createLeaseReview(ReviewRequest request, String reviewType, String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        Optional<Profile> profileData = profileRepository.findProfileByUsername(username);

        if (!profileData.isPresent()) {
            logger.debug("profile of request was not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Profile profile = profileData.get();

        Optional<Lease> leaseData = leaseRepository.findById(request.getLeaseId());
        if (!leaseData.isPresent()) {
            logger.debug("leaseId=" + request.getLeaseId() + " was not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Lease lease = leaseData.get();

        Review newReview = new Review(request.getRating(), request.getComment());
        newReview.setLease(lease);
        Lease savedLease;

        if (reviewType.equals("owner")) {
            if (profile.getId() != lease.getProfile().getId()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            reviewRepository.save(newReview);
            lease.setOwnerReview(newReview);
            savedLease = leaseRepository.save(lease);


        } else if (reviewType.equals("leasee")) {
            if (profile.getId() != lease.getOwner().getId()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            reviewRepository.save(newReview);
            lease.setLeaseeReview(newReview);
            savedLease = leaseRepository.save(lease);

        } else if (reviewType.equals("item")) {
            if (profile.getId() != lease.getProfile().getId()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            reviewRepository.save(newReview);
            lease.setItemReview(newReview);
            savedLease = leaseRepository.save(lease);

        } else {
            logger.debug("reviewType=" + reviewType + " does not match owner/leasee/item");
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(new LeaseResponse(savedLease), HttpStatus.OK);

    }

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
        return (lease.getProfile().getId() == profile.getId()) || (lease.getOwner().getId() == profile.getId());
    }

    private boolean profileIsOwnerOfLease(Profile profile, Lease lease){
        return (lease.getOwner().getId() == profile.getId());
    }

    public void removeDangling() {
        Date aWeekAgo = new Date(new Date().getTime() - (1000*60*60*24*7));
        List<Lease> leases = leaseRepository.findAll();
        for (Lease lease:leases) {
            if (!lease.getIsApproved() && new Date(lease.getToDatetime()).before(aWeekAgo)){
                try {
                    leaseRepository.delete(lease);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
