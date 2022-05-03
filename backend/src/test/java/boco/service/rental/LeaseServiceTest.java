package boco.service.rental;

import boco.models.http.LeaseResponse;
import boco.models.http.UpdateLeaseRequest;
import boco.models.profile.Personal;
import boco.models.profile.Profile;
import boco.models.rental.Lease;
import boco.models.rental.Listing;
import boco.repository.profile.ProfileRepository;
import boco.repository.rental.LeaseRepository;
import boco.repository.rental.ListingRepository;
import boco.service.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
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
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setup() {
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

        Listing li1 = new Listing("house", "house", "los", true, true, 100.0, "Month", p1);
        li1.setId(1L);

        Listing li2 = new Listing("crypto", "crypto", "bitcoin", true, true, 100.0, "Month", p2);
        li2.setId(2L);

        Listing li3 = new Listing("room", "room", "building", true, true, 1.0, "Week", p3);
        li3.setId(3L);

        Lease le1 = new Lease(
                Timestamp.valueOf("2030-07-01 10:00:00").getTime(),
                Timestamp.valueOf("2030-12-01 10:00:00").getTime(),
                p2, li1, p1);
        le1.setId(1L);
        Lease le2 = new Lease(
                Timestamp.valueOf("2031-07-01 10:00:00").getTime(),
                Timestamp.valueOf("2031-12-01 10:00:00").getTime(),
                p2, li1, p1);
        le2.setId(2L);
        Lease le3 = new Lease(
                Timestamp.valueOf("2032-07-01 10:00:00").getTime(),
                Timestamp.valueOf("2032-12-01 10:00:00").getTime(),
                p1, li2, p2);
        le3.setId(3L);
        Lease le4 = new Lease(
                Timestamp.valueOf("2033-07-01 10:00:00").getTime(),
                Timestamp.valueOf("2033-12-01 10:00:00").getTime(),
                p1, li2, p2);
        le4.setId(4L);
        Lease le5 = new Lease(
                Timestamp.valueOf("2034-07-01 10:00:00").getTime(),
                Timestamp.valueOf("2034-12-01 10:00:00").getTime(),
                p1, li2, p2);
        le5.setId(5L);

        // SPECIAL CASES:
        // Lease which is completed
        Lease le6 = new Lease(
                Timestamp.valueOf("2025-07-01 10:00:00").getTime(),
                Timestamp.valueOf("2025-12-01 10:00:00").getTime(),
                p4, li3, p3);
        le6.setId(6L);
        le6.setCompleted(true);
        // Lease with start date less than 24 hours before now
        Date date = new Date();
        long fromDate = date.getTime() + (3600*1000*3);
        long toDate = date.getTime() + (3600*1000*10);
        Lease le7 = new Lease(fromDate, toDate,
                p4, li3, p3);
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

        lenient().when(listingRepository.findById(1L)).thenReturn(Optional.of(li1));
        lenient().when(listingRepository.findById(2L)).thenReturn(Optional.of(li2));
        lenient().when(listingRepository.findById(3L)).thenReturn(Optional.of(li3));

        // Any lease can be returned to avoid NullPointerException, le1 was chosen randomly
        lenient().when(leaseRepository.save(any())).thenReturn(le1);
    }

    @Test
    public void getMyLeasesReturnsCorrectNumberOfLeases() {
        ResponseEntity<List<LeaseResponse>> res1 = service.getMyLeases("Bearer messi", true);
        List<LeaseResponse> leases1 = res1.getBody();
        ResponseEntity<List<LeaseResponse>> res2 = service.getMyLeases("Bearer usr", true);
        List<LeaseResponse> leases2 = res2.getBody();


        assertEquals(2, leases1.size());
        assertEquals(3, leases2.size());
    }

    @Test
    public void getMyLeasesReturnsStatusCode200() {
        ResponseEntity<List<LeaseResponse>> res = service.getMyLeases("Bearer messi", true);

        assertEquals(200, res.getStatusCodeValue());
    }

    @Test
    public void getMyLeasesReturnsCorrectLeases() {
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
    public void getMyLeasesReturnsStatusCode404() {
        var res = service.getMyLeases("Bearer notfound", true);
        assertEquals(404, res.getStatusCodeValue());
    }

    @Test
    public void deleteLeaseReturnsStatusCode204() {
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
    public void deleteLeaseReturnsStatusCode404() {
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
    public void deleteLeaseReturnsStatusCode422() {
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
    public void updateLeaseReturnsStatusCode200() {
        var req = new UpdateLeaseRequest(true,true,1L);
        var r1 = service.updateLease(req, "Bearer messi");
        assertEquals(200, r1.getStatusCodeValue(), 200);
    }

    @Test
    public void updateLeaseReturnsStatusCode400() {
        // updater of lease is not the lease owner
        var req1 = new UpdateLeaseRequest(true,true,1L); // Owned by messi
        var req2 = new UpdateLeaseRequest(true,true,3L); // Owned by usr

        var r1 = service.updateLease(req1, "Bearer usr");
        var r2 = service.updateLease(req1, "Bearer si");
        var r3 = service.updateLease(req2, "Bearer messi");
        var r4 = service.updateLease(req2, "Bearer ron");

        assertEquals(400, r1.getStatusCodeValue());
        assertEquals(400, r2.getStatusCodeValue());
        assertEquals(400, r3.getStatusCodeValue());
        assertEquals(400, r4.getStatusCodeValue());

    }


}