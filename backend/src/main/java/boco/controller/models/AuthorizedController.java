package boco.controller.models;

import boco.models.http.*;
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
            @RequestPart("file") @Validated MultipartFile multipartFile,
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

    @PostMapping("/lease")
    public ResponseEntity<LeaseResponse> createLease(@RequestBody LeaseRequest leaseRequest,
                                             @RequestHeader(name="Authorization") String token) {
        return leaseService.createLease(leaseRequest, token);
    }

    @PutMapping("/lease")
    public ResponseEntity<LeaseResponse> updateLease(@RequestBody UpdateLeaseRequest updateLeaseRequest,
                                                     @RequestHeader(name="Authorization") String token) {
        return leaseService.updateLease(updateLeaseRequest, token);
    }

    @DeleteMapping("/lease/{lease_id}")
    public ResponseEntity<HttpStatus> deleteLease(@PathVariable(value = "lease_id") Long leaseId,
                                                  @RequestHeader(name="Authorization") String token) {
        return leaseService.deleteLease(leaseId, token);
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
