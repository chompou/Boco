package boco.controller.model;

import boco.model.http.notification.NotificationReadRequest;
import boco.model.http.profile.PrivateProfileResponse;
import boco.model.http.profile.UpdateProfileRequest;
import boco.model.http.rental.*;
import boco.model.profile.Notification;
import boco.service.profile.NotificationService;
import boco.service.profile.ProfileService;
import boco.service.rental.LeaseService;
import boco.service.rental.ListingService;
import boco.service.rental.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * This class defines endpoints for authorized operations (operations needing a JWT token)
 */
@RestController
@RequestMapping("/api/my")
public class AuthorizedController {
    private final ListingService listingService;
    private final ProfileService profileService;
    private final ReviewService reviewService;
    private final LeaseService leaseService;
    private final NotificationService notificationService;

    @Autowired
    public AuthorizedController(ListingService listingService, ProfileService profileService,
                                ReviewService reviewService, LeaseService leaseService,
                                NotificationService notificationService) {
        this.listingService = listingService;
        this.profileService = profileService;
        this.reviewService = reviewService;
        this.leaseService = leaseService;
        this.notificationService = notificationService;
    }

    /**
     * Creates a new listing
     *
     * @param listingRequest The new listing
     * @param multipartFile Image of listing
     * @param token Authorization header
     * @return The created listing
     */
    @PostMapping(value = "/listing", consumes = {"multipart/form-data"})
    public ResponseEntity<ListingResponse> createListing(
            @RequestPart("properties") @Validated ListingRequest listingRequest,
            @RequestPart("file") MultipartFile multipartFile,
            @RequestHeader(name="Authorization") String token) {
        return listingService.createListing(listingRequest,multipartFile, token);
    }

    /**
     * Updates a listing
     *
     * @param updateListingRequest The new listing values
     * @param token Authorization header
     * @return The updated listing
     */
    @PutMapping("/listing")
    public ResponseEntity<ListingResponse> updateListing(@RequestBody UpdateListingRequest updateListingRequest,
                                                         @RequestHeader(name="Authorization") String token) {
        return listingService.updateListing(updateListingRequest, token);
    }

    /**
     * Deletes a listing
     *
     * @param listingId ID of listing to be deleted
     * @param token Authorization header
     * @return Status indicating if the listing was deleted
     */
    @DeleteMapping("/listing/{listing_id}")
    public ResponseEntity<HttpStatus> deleteListing(@PathVariable("listing_id") Long listingId,
                                                    @RequestHeader(name="Authorization") String token) {
        return listingService.deleteListing(listingId, token);
    }

    /**
     * Gets private version of profile, only supposed to be returned to the profile itself.
     *
     * @param token Authorization header
     * @return Private version of profile
     */
    @GetMapping("/profile")
    public ResponseEntity<PrivateProfileResponse> getMyProfile(@RequestHeader(name="Authorization") String token){
        return profileService.getPrivateProfile(token);
    }

    /**
     * Updates a profile
     *
     * @param updateProfileRequest New values of profile
     * @param token Authorization header
     * @return The updated profile
     */
    @PutMapping("/profile")
    public ResponseEntity<PrivateProfileResponse> updateProfile(@RequestBody UpdateProfileRequest updateProfileRequest,
                                                         @RequestHeader(name="Authorization") String token) {
        return profileService.updateProfile(updateProfileRequest, token);
    }

    /**
     * Deactivates a profile
     * @param token Authorization header
     * @return The deleted profile
     */
    @PutMapping("/profile/deactivate")
    public ResponseEntity<PrivateProfileResponse> deactivateMyProfile(@RequestHeader(name="Authorization") String token){
        return profileService.deactivateProfile(token);
    }

    /**
     * Gets reviews written by profile of token
     *
     * @param perPage Number of reviews per page
     * @param page Page number
     * @param token Authorization header
     * @return List of reviews
     */
    @GetMapping("/review")
    public ResponseEntity<List<ReviewResponse>> getGivenReviews(@RequestParam(name = "perPage") int perPage,
                                                                @RequestParam(name = "page") int page,
                                                                @RequestHeader(name="Authorization") String token){
        return reviewService.getMyWrittenReviews(token, perPage, page);
    }

    /**
     * Gets leases profile is part of, which is indicated by isOwner parameter
     *
     * @param token Authorization header
     * @param isOwner True: gets leases where profile is the owner of item of lease. Else false
     * @return List of leases
     */
    @GetMapping("/lease")
    public ResponseEntity<List<LeaseResponse>> getMyLeases(@RequestHeader(name="Authorization") String token,
                                                           @RequestParam(name = "is_owner") Boolean isOwner) {
        return leaseService.getMyLeases(token, isOwner);
    }

    /**
     * Adds a review to a lease
     *
     * @param reviewRequest The review
     * @param reviewType Type of review either of owner of item in lease, leasee of lease or the item itself
     * @param token Authorization header
     * @return The lease review was added to
     */
    @PostMapping("/lease/review")
    public ResponseEntity<LeaseResponse> createLeaseReview(@RequestBody ReviewRequest reviewRequest,
                                                           @RequestParam(name = "review_type") String reviewType,
                                                           @RequestHeader(name="Authorization") String token) {
        return leaseService.createLeaseReview(reviewRequest, reviewType, token);
    }

    /**
     * Creates a new lease
     *
     * @param leaseRequest Lease to be created
     * @param token Authorization header
     * @return The created lease
     */
    @PostMapping("/lease")
    public ResponseEntity<LeaseResponse> createLease(@RequestBody LeaseRequest leaseRequest,
                                             @RequestHeader(name="Authorization") String token) {
        ResponseEntity<LeaseResponse> leaseResponseResponseEntity = leaseService.createLease(leaseRequest, token);
        Notification notification = notificationService.newLeaseNotification(leaseResponseResponseEntity);
        if (notification != null){
            notificationService.addNewNotification(notification);
        }
        return leaseResponseResponseEntity;
    }

    /**
     * Updates a lease
     *
     * @param updateLeaseRequest New values of lease
     * @param token Authorization header
     * @return The updated lease
     */
    @PutMapping("/lease")
    public ResponseEntity<LeaseResponse> updateLease(@RequestBody UpdateLeaseRequest updateLeaseRequest,
                                                     @RequestHeader(name="Authorization") String token) {
        ResponseEntity<Boolean> leaseIsLegal = leaseService.checkIfUpdatingLeaseIsLegal(updateLeaseRequest, token);
        if (!leaseIsLegal.getBody()){
            return new ResponseEntity<>(leaseIsLegal.getStatusCode());
        }

        if (updateLeaseRequest.getIsApproved()!= null){
            Notification notification = notificationService.approveLeaseNotification(updateLeaseRequest);
            notificationService.addNewNotification(notification);
        }
        if (updateLeaseRequest.getIsCompleted()!= null){
            Notification notification = notificationService.completedLeaseNotification(updateLeaseRequest);
            notificationService.addNewNotification(notification);
        }
        return leaseService.updateLease(updateLeaseRequest, token);
    }

    /**
     * Deletes a lease
     *
     * @param leaseId ID of the lease
     * @param token Authorization header
     * @return Status indicating if the lease was deleted
     */
    @DeleteMapping("/lease/{lease_id}")
    public ResponseEntity<HttpStatus> deleteLease(@PathVariable(value = "lease_id") Long leaseId,
                                                  @RequestHeader(name="Authorization") String token) {
        Notification notificationLeasee = notificationService.canceledLeaseForLease(leaseId);
        Notification notificationOwner = notificationService.canceledLeaseForOwner(leaseId);
        ResponseEntity<HttpStatus> response = leaseService.deleteLease(leaseId, token);
        if (response.getStatusCode().is2xxSuccessful()){
            notificationService.addNewNotification(notificationLeasee);
            notificationService.addNewNotification(notificationOwner);
        }
        return response;
    }

    /**
     * Gets notifications of profile of header token
     *
     * @param token Authorization header
     * @return  List of notifications
     */
    @GetMapping("/notifications")
    public ResponseEntity<?> getMyNotifications(@RequestHeader(name="Authorization") String token){
        return notificationService.getMyNotifications(token);
    }

    /**
     * Updates a notification
     *
     * @param toBeUpdated New notification values
     * @param token Authorization header
     * @return Updated list of notifications
     */
    @PutMapping("/notifications")
    public ResponseEntity<?> updateNotification(@RequestBody NotificationReadRequest toBeUpdated,
                                                @RequestHeader(name="Authorization") String token){
        return notificationService.upDateNotifications(toBeUpdated.getToBeRead(), token);
    }
}
