package boco.service.rental;

import boco.model.http.rental.LeaseRequest;
import boco.model.http.rental.LeaseResponse;
import boco.model.http.rental.ReviewRequest;
import boco.model.http.rental.UpdateLeaseRequest;
import boco.model.profile.Personal;
import boco.model.profile.Profile;
import boco.model.rental.Lease;
import boco.model.rental.Listing;
import boco.repository.profile.ProfileRepository;
import boco.repository.rental.LeaseRepository;
import boco.repository.rental.ListingRepository;
import boco.repository.rental.ReviewRepository;
import boco.service.security.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class LeaseServiceTest {
    @InjectMocks
    private LeaseService service;

    @Mock
    private LeaseRepository leaseRepository;
    @Mock
    private ListingRepository listingRepository;
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private JwtUtil jwtUtil;
    private List<Lease> leases;

    @BeforeEach
    public void setup() {
        leases = new ArrayList<>();
        Profile p1 = new Personal("messi", "leo@psg.fr", "x", "LEO", "x", "Argentina", "6:6", "12345678");
        p1.setId(1L);
        Optional<Profile> pd1 = Optional.of(p1);

        Profile p2 = new Personal("usr", "usr@mail.com", "x", "USR", "x", "World", "7:7", "12345678");
        p2.setId(2L);
        Optional<Profile> pd2 = Optional.of(p2);

        Profile p3 = new Personal("ron", "ron@gmail.com", "x", "RON", "x", "Wrld", "1:1", "12345678");
        p3.setId(3L);
        Optional<Profile> pd3 = Optional.of(p3);

        Profile p4 = new Personal("si", "si@mail.com", "x", "SI", "x", "si", "5:5", "12345678");
        p4.setId(4L);
        Optional<Profile> pd4 = Optional.of(p4);

        Listing li1 = new Listing("house", "house", true, 100.0, "Month", p1);
        li1.setId(1L);


        Listing li2 = new Listing("crypto", "crypto", true, 100.0, "Month", p2);
        li2.setId(2L);


        Listing li3 = new Listing("room", "room", true, 1.0, "Week", p3);
        li3.setId(3L);


        Lease le1 = new Lease(
                Timestamp.valueOf("2030-07-01 10:00:00").getTime(),
                Timestamp.valueOf("2030-12-01 10:00:00").getTime(),
                p2, li1, p1);
        le1.setId(1L);
        leases.add(le1);
        Lease le2 = new Lease(
                Timestamp.valueOf("2031-07-01 10:00:00").getTime(),
                Timestamp.valueOf("2031-12-01 10:00:00").getTime(),
                p2, li1, p1);
        le2.setId(2L);
        leases.add(le2);
        Lease le3 = new Lease(
                Timestamp.valueOf("2032-07-01 10:00:00").getTime(),
                Timestamp.valueOf("2032-12-01 10:00:00").getTime(),
                p1, li2, p2);
        le3.setId(3L);
        leases.add(le3);
        Lease le4 = new Lease(
                Timestamp.valueOf("2033-07-01 10:00:00").getTime(),
                Timestamp.valueOf("2033-12-01 10:00:00").getTime(),
                p1, li2, p2);
        le4.setId(4L);
        leases.add(le4);
        Lease le5 = new Lease(
                Timestamp.valueOf("2034-07-01 10:00:00").getTime(),
                Timestamp.valueOf("2034-12-01 10:00:00").getTime(),
                p1, li2, p2);
        le5.setId(5L);
        leases.add(le5);

        // SPECIAL CASES:
        // Lease which is completed
        Lease le6 = new Lease(
                Timestamp.valueOf("2025-07-01 10:00:00").getTime(),
                Timestamp.valueOf("2025-12-01 10:00:00").getTime(),
                p3, li3, p4);
        le6.setId(6L);
        le6.setIsCompleted(true);
        // Lease with start date less than 24 hours before now
        Date date = new Date();
        long fromDate = date.getTime() + (3600*1000*3);
        long toDate = date.getTime() + (3600*1000*10);
        Lease le7 = new Lease(fromDate, toDate,
                p3, li3, p4);
        le7.setId(7L);


        List<Lease> leases1 = List.of(le1, le2);
        List<Lease> leases2 = List.of(le3, le4, le5);
        List<Lease> leases3 = List.of(le6, le7);

        lenient().when(profileRepository.findProfileByUsername("messi")).thenReturn(pd1);
        lenient().when(profileRepository.findProfileByUsername("usr")).thenReturn(pd2);
        lenient().when(profileRepository.findProfileByUsername("ron")).thenReturn(pd3);
        lenient().when(profileRepository.findProfileByUsername("si")).thenReturn(pd4);
        lenient().when(profileRepository.findProfileByUsername("notfound")).thenReturn(Optional.empty());

        lenient().when(jwtUtil.extractUsername("messi")).thenReturn("messi");
        lenient().when(jwtUtil.extractUsername("usr")).thenReturn("usr");
        lenient().when(jwtUtil.extractUsername("ron")).thenReturn("ron");
        lenient().when(jwtUtil.extractUsername("si")).thenReturn("si");
        lenient().when(jwtUtil.extractUsername("notfound")).thenReturn("notfound");

        lenient().when(jwtUtil.extractProfileFromAuthHeader("Bearer messi")).thenReturn(p1);
        lenient().when(jwtUtil.extractProfileFromAuthHeader("Bearer usr")).thenReturn(p2);
        lenient().when(jwtUtil.extractProfileFromAuthHeader("Bearer ron")).thenReturn(p3);
        lenient().when(jwtUtil.extractProfileFromAuthHeader("Bearer si")).thenReturn(p4);
        lenient().when(jwtUtil.extractProfileFromAuthHeader("Bearer notfound")).thenReturn(null);

        lenient().when(leaseRepository.getLeasesByOwner(pd1.get())).thenReturn(leases1);
        lenient().when(leaseRepository.getLeasesByOwner(pd2.get())).thenReturn(leases2);
        lenient().when(leaseRepository.getLeasesByOwner(pd3.get())).thenReturn(leases3);

        lenient().when(leaseRepository.findById(1L)).thenReturn(Optional.of(le1));
        lenient().when(leaseRepository.findById(2L)).thenReturn(Optional.of(le2));
        lenient().when(leaseRepository.findById(3L)).thenReturn(Optional.of(le3));
        lenient().when(leaseRepository.findById(4L)).thenReturn(Optional.of(le4));
        lenient().when(leaseRepository.findById(5L)).thenReturn(Optional.of(le5));
        lenient().when(leaseRepository.findById(6L)).thenReturn(Optional.of(le6));
        lenient().when(leaseRepository.findById(7L)).thenReturn(Optional.of(le7));
        lenient().when(leaseRepository.findById(8L)).thenReturn(Optional.empty());

        lenient().when(listingRepository.findById(1L)).thenReturn(Optional.of(li1));
        lenient().when(listingRepository.findById(2L)).thenReturn(Optional.of(li2));
        lenient().when(listingRepository.findById(3L)).thenReturn(Optional.of(li3));

        // Any lease can be returned to avoid NullPointerException, le1 was chosen randomly
        lenient().when(leaseRepository.save(any())).thenReturn(le1);
        lenient().when(leaseRepository.getLeasesByListing_IdAndIsApprovedIsTrue(any())).thenReturn(leases);


    }

    @Test
    void getMyLeasesReturnsCorrectNumberOfLeases() {
        ResponseEntity<List<LeaseResponse>> res1 = service.getMyLeases("Bearer messi", true);
        List<LeaseResponse> leases1 = res1.getBody();
        ResponseEntity<List<LeaseResponse>> res2 = service.getMyLeases("Bearer usr", true);
        List<LeaseResponse> leases2 = res2.getBody();


        assertEquals(2, leases1.size());
        assertEquals(3, leases2.size());
    }

    @Test
    void getMyLeasesReturnsStatusCode200() {
        ResponseEntity<List<LeaseResponse>> res = service.getMyLeases("Bearer messi", true);

        assertEquals(200, res.getStatusCodeValue());
    }

    @Test
    void getMyLeasesReturnsCorrectLeases() {
        ResponseEntity<List<LeaseResponse>> res1 = service.getMyLeases("Bearer messi", true);
        ResponseEntity<List<LeaseResponse>> res2 = service.getMyLeases("Bearer usr", true);
        List<LeaseResponse> leases1 = res1.getBody();
        List<LeaseResponse> leases2 = res2.getBody();

        LeaseResponse l1 = leases1.get(0);
        LeaseResponse l2 = leases1.get(1);
        LeaseResponse l3 = leases2.get(0);
        LeaseResponse l4 = leases2.get(1);
        LeaseResponse l5 = leases2.get(2);

        assertEquals(1L, l1.getId());
        assertEquals(false, l1.getIsApproved());
        assertEquals(Timestamp.valueOf("2030-07-01 10:00:00").getTime(), l1.getFromDatetime());
        assertEquals(Timestamp.valueOf("2030-12-01 10:00:00").getTime(), l1.getToDatetime());
        assertEquals(false, l1.getIsCompleted());
        assertEquals(2L, l1.getProfileId(), 2L);
        assertEquals(1L, l1.getListingId(), 1L);

        assertEquals(2L, l2.getId());
        assertEquals(false, l2.getIsApproved());
        assertEquals(Timestamp.valueOf("2031-07-01 10:00:00").getTime(), l2.getFromDatetime());
        assertEquals(Timestamp.valueOf("2031-12-01 10:00:00").getTime(), l2.getToDatetime());
        assertEquals(false, l2.getIsCompleted());
        assertEquals(2L, l2.getProfileId());
        assertEquals(1L, l2.getListingId());

        assertEquals(3L, l3.getId());
        assertEquals(false, l3.getIsApproved());
        assertEquals(Timestamp.valueOf("2032-07-01 10:00:00").getTime(), l3.getFromDatetime());
        assertEquals(Timestamp.valueOf("2032-12-01 10:00:00").getTime(), l3.getToDatetime());
        assertEquals(false, l3.getIsCompleted());
        assertEquals(1L, l3.getProfileId());
        assertEquals(2L, l3.getListingId());

        assertEquals(4L, l4.getId());
        assertEquals(false, l4.getIsApproved());
        assertEquals(Timestamp.valueOf("2033-07-01 10:00:00").getTime(), l4.getFromDatetime());
        assertEquals(Timestamp.valueOf("2033-12-01 10:00:00").getTime(), l4.getToDatetime());
        assertEquals(false, l4.getIsCompleted());
        assertEquals(1L, l4.getProfileId(), 1L);
        assertEquals(2L, l4.getListingId(), 2L);

        assertEquals(5L, l5.getId());
        assertEquals(false, l5.getIsApproved());
        assertEquals(Timestamp.valueOf("2034-07-01 10:00:00").getTime(), l5.getFromDatetime());
        assertEquals(Timestamp.valueOf("2034-12-01 10:00:00").getTime(), l5.getToDatetime());
        assertEquals(false, l5.getIsCompleted());
        assertEquals(1L, l5.getProfileId());
        assertEquals(2L, l5.getListingId());
    }

    @Test
    void getMyLeasesReturnsStatusCode404() {
        var res = service.getMyLeases("Bearer notfound", true);
        assertEquals(404, res.getStatusCodeValue());
    }

    @Test
    void deleteLeaseReturnsStatusCode204() {
        // Owner deletes lease
        var r1 = service.deleteLease(1L, "Bearer messi");
        var r2 = service.deleteLease(2L, "Bearer messi");
        var r3 = service.deleteLease(3L, "Bearer usr");
        var r4 = service.deleteLease(4L, "Bearer usr");
        var r5 = service.deleteLease(5L, "Bearer usr");

        // Leasee deletes lease
        var r6 = service.deleteLease(1L, "Bearer usr");
        var r7 = service.deleteLease(2L, "Bearer usr");
        var r8 = service.deleteLease(3L, "Bearer messi");
        var r9 = service.deleteLease(4L, "Bearer messi");
        var r10 = service.deleteLease(5L, "Bearer messi");

        assertEquals(204, r1.getStatusCodeValue());
        assertEquals(204, r2.getStatusCodeValue());
        assertEquals(204, r3.getStatusCodeValue());
        assertEquals(204, r4.getStatusCodeValue());
        assertEquals(204, r5.getStatusCodeValue());
        assertEquals(204, r6.getStatusCodeValue());
        assertEquals(204, r7.getStatusCodeValue());
        assertEquals(204, r8.getStatusCodeValue());
        assertEquals(204, r9.getStatusCodeValue());
        assertEquals(204, r10.getStatusCodeValue());

    }

    @Test
    void deleteLeaseReturnsStatusCode404() {
        // Username not in database
        var r1 = service.deleteLease(1L, "Bearer notfound");

        // Lease not in database
        var r2 = service.deleteLease(8L, "Bearer usr");
        var r3 = service.deleteLease(9L, "Bearer messi");

        assertEquals(404, r1.getStatusCodeValue());
        assertEquals(404, r2.getStatusCodeValue());
        assertEquals(404, r3.getStatusCodeValue());
    }

    @Test
    void deleteLeaseReturnsStatusCode422() {
        // Completed lease
        var r1 = service.deleteLease(6L, "Bearer ron");
        // Trying to delete less than 24h before start
        var r2 = service.deleteLease(7L, "Bearer ron");

        // Profile not part of lease trying to delete the lease
        var r3 = service.deleteLease(1L, "Bearer ron");
        var r4 = service.deleteLease(2L, "Bearer si");

        assertEquals(422, r1.getStatusCodeValue());
        assertEquals(422, r2.getStatusCodeValue());
        assertEquals(422, r3.getStatusCodeValue());
        assertEquals(422, r4.getStatusCodeValue());
    }

    @Test
    void updateLeaseReturnsStatusCode200() {
        var req = new UpdateLeaseRequest(true,true,1L);
        var r1 = service.updateLease(req, "Bearer messi");
        assertEquals(200, r1.getStatusCodeValue(), 200);
    }



    @Test
    void checkLeaseIsLegalReturns400WhenOwnerIsNotOwner() {
        // updater of lease is not the lease owner
        var req1 = new UpdateLeaseRequest(true,true,1L); // Owned by messi
        var req2 = new UpdateLeaseRequest(true,true,3L); // Owned by usr

        var r1 = service.checkIfUpdatingLeaseIsLegal(req1, "Bearer usr");
        var r2 = service.checkIfUpdatingLeaseIsLegal(req1, "Bearer si");
        var r3 = service.checkIfUpdatingLeaseIsLegal(req2, "Bearer messi");
        var r4 = service.checkIfUpdatingLeaseIsLegal(req2, "Bearer ron");

        assertEquals(400, r1.getStatusCodeValue());
        assertEquals(400, r2.getStatusCodeValue());
        assertEquals(400, r3.getStatusCodeValue());
        assertEquals(400, r4.getStatusCodeValue());
    }

    @Test
    void testCheckForOverlappingLeases(){
        Listing l = new Listing("Elias", "En person", true, 20.0, "hour", null);

        Lease l1 = new Lease(Timestamp.valueOf("2030-04-02 10:00:00").getTime(), Timestamp.valueOf("2031-02-03 10:00:00").getTime(), null, new Listing(), null);
        List<Lease> leases = service.getOverlappingLeases(l1);
        Assertions.assertEquals(1, leases.size());

        Lease l2 = new Lease(Timestamp.valueOf("2030-04-02 10:00:00").getTime(), Timestamp.valueOf("2030-11-03 10:00:00").getTime(), null, new Listing(), null);
        List<Lease> leases2 = service.getOverlappingLeases(l2);
        Assertions.assertEquals(1, leases2.size());

        Lease l3 = new Lease(Timestamp.valueOf("2030-08-02 10:00:00").getTime(), Timestamp.valueOf("2031-02-03 10:00:00").getTime(), null, new Listing(), null);
        List<Lease> leases3 = service.getOverlappingLeases(l3);
        Assertions.assertEquals(1, leases3.size());

        Lease l4 = new Lease(Timestamp.valueOf("2030-08-02 10:00:00").getTime(), Timestamp.valueOf("2030-09-03 10:00:00").getTime(), null, new Listing(), null);
        List<Lease> leases4 = service.getOverlappingLeases(l4);
        Assertions.assertEquals(1, leases4.size());

        Lease l5 = new Lease(Timestamp.valueOf("2030-04-02 10:00:00").getTime(), Timestamp.valueOf("2030-05-03 10:00:00").getTime(), null, new Listing(), null);
        List<Lease> leases5 = service.getOverlappingLeases(l5);
        Assertions.assertEquals(0, leases5.size());

        Lease l6 = new Lease(Timestamp.valueOf("2030-04-02 10:00:00").getTime(), Timestamp.valueOf("2030-05-03 10:00:00").getTime(), null, new Listing(), null);
        List<Lease> leases6 = service.getOverlappingLeases(l6);
        Assertions.assertEquals(0, leases6.size());
    }



    @Test
    void createLease() {
        Date soon = new Date(new Date().getTime() + 1000*60*60*24);
        Date later = new Date(soon.getTime() + 1000*60*60*48);
        LeaseRequest toBeCreated = new LeaseRequest();
        toBeCreated.setId(1l);
        toBeCreated.setFromDatetime(soon.getTime());
        toBeCreated.setToDatetime(later.getTime());

        ResponseEntity response = service.createLease(toBeCreated, "Bearer messi");
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertTrue(response.getBody() instanceof LeaseResponse);
        LeaseResponse body = (LeaseResponse) response.getBody();

        assertEquals(2, body.getProfileId());
        assertEquals(1, body.getId());
        assertFalse(body.getIsApproved());
        assertFalse(body.getIsCompleted());
        assertFalse(body.getIsApproved());
        assertNotNull(body.getToDatetime());
        assertNotNull(body.getFromDatetime());
        assertEquals("house", body.getItemName());


        ResponseEntity errorResponse = service.createLease(toBeCreated, "Bearer not found");
        assertTrue(errorResponse.getStatusCode().isError());
        assertNull(errorResponse.getBody());


        toBeCreated.setId(8l);
        errorResponse = service.createLease(toBeCreated, "Bearer messi");
        assertTrue(errorResponse.getStatusCode().isError());
        assertNull(errorResponse.getBody());

        toBeCreated.setId(9l);
        errorResponse = service.createLease(toBeCreated, "Bearer something unexpected");
        assertTrue(errorResponse.getStatusCode().isError());
        assertNull(errorResponse.getBody());
    }

    @Test
    void createLeaseReview() {
        ReviewRequest request = new ReviewRequest();
        request.setLeaseId(1L);
        request.setRating(5.0);
        request.setComment("Very good");

        ReviewRequest request2 = new ReviewRequest();
        request2.setLeaseId(1L);
        request2.setRating(5.0);
        request2.setComment("Very good");

        ResponseEntity response1 = service.createLeaseReview(request, "owner", "Bearer usr");
        assertTrue(response1.getStatusCode().is2xxSuccessful());
        assertTrue(response1.getBody() instanceof LeaseResponse);
        assertEquals(1L, ((LeaseResponse) response1.getBody()).getId());
        assertNull(((LeaseResponse) response1.getBody()).getLeaseeReview());
        assertNull(((LeaseResponse) response1.getBody()).getItemReview());
        assertNotNull(((LeaseResponse) response1.getBody()).getOwnerReview());

        ResponseEntity response2 = service.createLeaseReview(request2, "leasee", "Bearer messi");
        assertTrue(response2.getStatusCode().is2xxSuccessful());
        assertTrue(response2.getBody() instanceof LeaseResponse);
        assertEquals(1L, ((LeaseResponse) response2.getBody()).getId());
        assertNotNull(((LeaseResponse) response2.getBody()).getLeaseeReview());
        assertNull(((LeaseResponse) response2.getBody()).getItemReview());
        assertNotNull(((LeaseResponse) response2.getBody()).getOwnerReview());

        ResponseEntity response3 = service.createLeaseReview(request, "item", "Bearer usr");
        assertTrue(response3.getStatusCode().is2xxSuccessful());
        assertTrue(response3.getBody() instanceof LeaseResponse);
        assertEquals(1L, ((LeaseResponse) response3.getBody()).getId());
        assertNotNull(((LeaseResponse) response3.getBody()).getLeaseeReview());
        assertNotNull(((LeaseResponse) response3.getBody()).getItemReview());
        assertNotNull(((LeaseResponse) response3.getBody()).getOwnerReview());
    }

    @Test
    void createLeaseReviewNegativeResponseOnUserNotCorrectForLease() {
        ReviewRequest request = new ReviewRequest();
        request.setLeaseId(1L);
        request.setRating(5.0);
        request.setComment("Very good");

        ReviewRequest request2 = new ReviewRequest();
        request2.setLeaseId(1L);
        request2.setRating(5.0);
        request2.setComment("Very good");

        ResponseEntity response1 = service.createLeaseReview(request, "owner", "Bearer messi");
        assertTrue(response1.getStatusCode().isError());
        assertFalse(response1.hasBody());

        ResponseEntity response2 = service.createLeaseReview(request2, "leasee", "Bearer usr");
        assertTrue(response2.getStatusCode().isError());
        assertFalse(response2.hasBody());

        ResponseEntity response3 = service.createLeaseReview(request, "item", "Bearer messi");
        assertTrue(response3.getStatusCode().isError());
        assertFalse(response3.hasBody());
    }

    @Test
    void removeDangling() {
        leases = new ArrayList<>();
        Date future = new Date(new Date().getTime() + 1000*60*60*24);
        Date aWeekAgo = new Date(new Date().getTime() - (1000*60*60*24*7));
        Date[] dates = new Date[2];
        dates[0] = aWeekAgo;
        dates[1] = future;

        for (int i = 0; i < 16; i++) {
            Lease lease = new Lease();
            lease.setId((long) i);
            lease.setFromDatetime(dates[i%2].getTime());
            lease.setToDatetime(lease.getFromDatetime() + 1000*60*60*24);
            lease.setIsApproved((int) i/2%2 == 0);
            lease.setIsCompleted((int) i/4%2 == 0);
            leases.add(lease);
        }
        lenient().when(leaseRepository.findAll()).thenReturn(leases);

        service.removeDangling();
    }
}
















