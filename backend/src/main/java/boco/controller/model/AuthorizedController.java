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


    @PostMapping(value = "/listing", consumes = {"multipart/form-data"})
    public ResponseEntity<ListingResponse> createListing(
            @RequestPart("properties") @Validated ListingRequest listingRequest,
            @RequestPart("file") MultipartFile multipartFile,
            @RequestHeader(name="Authorization") String token) {
        return listingService.createListing(listingRequest,multipartFile, token);
    }


    @PutMapping("/listing")
    public ResponseEntity<ListingResponse> updateListing(@RequestBody UpdateListingRequest updateListingRequest,
                                                         @RequestHeader(name="Authorization") String token) {
        return listingService.updateListing(updateListingRequest, token);
    }

    @DeleteMapping("/listing/{listing_id}")
    public ResponseEntity<HttpStatus> deleteListing(@PathVariable("listing_id") Long listingId,
                                                    @RequestHeader(name="Authorization") String token) {
        return listingService.deleteListing(listingId, token);
    }


    @GetMapping("/profile")
    public ResponseEntity<PrivateProfileResponse> getMyProfile(@RequestHeader(name="Authorization") String token){
        return profileService.getPrivateProfile(token);
    }
    @PutMapping("/profile")
    public ResponseEntity<PrivateProfileResponse> updateProfile(@RequestBody UpdateProfileRequest updateProfileRequest,
                                                         @RequestHeader(name="Authorization") String token) {
        return profileService.updateProfile(updateProfileRequest, token);
    }
    @PutMapping("/profile/deactivate")
    public ResponseEntity<PrivateProfileResponse> deactivateMyProfile(@RequestHeader(name="Authorization") String token){
        return profileService.deactivateProfile(token);
    }

    @GetMapping("/review")
    public ResponseEntity<List<ReviewResponse>> getGivenReviews(@RequestParam(name = "perPage") int perPage,
                                                                @RequestParam(name = "page") int page,
                                                                @RequestHeader(name="Authorization") String token){
        return reviewService.getMyWrittenReviews(token, perPage, page);
    }

    @GetMapping("/lease")
    public ResponseEntity<List<LeaseResponse>> getMyLeases(@RequestHeader(name="Authorization") String token,
                                                           @RequestParam(name = "is_owner") Boolean isOwner) {
        return leaseService.getMyLeases(token, isOwner);
    }

    @PostMapping("/lease/review")
    public ResponseEntity<LeaseResponse> createLeaseReview(@RequestBody ReviewRequest reviewRequest,
                                                           @RequestParam(name = "review_type") String reviewType,
                                                           @RequestHeader(name="Authorization") String token) {
        return leaseService.createLeaseReview(reviewRequest, reviewType, token);
    }

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

    @PutMapping("/lease")
    public ResponseEntity<LeaseResponse> updateLease(@RequestBody UpdateLeaseRequest updateLeaseRequest,
                                                     @RequestHeader(name="Authorization") String token) {
        ResponseEntity<Boolean> leaseIsLegal = leaseService.checkIfUpdatingLeaseIsLegal(updateLeaseRequest, token);
        if (!leaseIsLegal.getBody()) {
            return new ResponseEntity<>(leaseIsLegal.getStatusCode());
        }

        if (updateLeaseRequest.getIsApproved() != null && updateLeaseRequest.getIsApproved()) {
            System.out.println("1");
            Notification notification = notificationService.approveLeaseNotification(updateLeaseRequest);
            notificationService.addNewNotification(notification);
        }
        if (updateLeaseRequest.getIsCompleted() != null && updateLeaseRequest.getIsCompleted()) {
            System.out.println("2");
            Notification notification = notificationService.completedLeaseNotification(updateLeaseRequest);
            notificationService.addNewNotification(notification);
        }
        return leaseService.updateLease(updateLeaseRequest, token);
    }

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

    @GetMapping("/notifications")
    public ResponseEntity<?> getMyNotifications(@RequestHeader(name="Authorization") String token){
        return notificationService.getMyNotifications(token);
    }

    @PutMapping("/notifications")
    public ResponseEntity<?> updateNotification(@RequestBody NotificationReadRequest toBeUpdated,
                                                @RequestHeader(name="Authorization") String token){
        return notificationService.upDateNotifications(toBeUpdated.getToBeRead(), token);
    }
}
