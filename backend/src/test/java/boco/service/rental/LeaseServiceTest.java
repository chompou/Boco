package boco.service.rental;

import boco.models.http.LeaseResponse;
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
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
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
        Profile p1 = new Personal("messi", "leo@psg.fr", "x", "LEO", "x", "Argentina", "12345678");
        p1.setId(1L);
        Optional<Profile> pd1 = Optional.of(p1);

        Profile p2 = new Personal("usr", "usr@mail.com", "x", "USR", "x", "World", "12345678");
        p2.setId(2L);
        Optional<Profile> pd2 = Optional.of(p2);

        Listing li1 = new Listing("house", "house", "los", true, true, 100.0, "Month", p1);
        li1.setId(1L);

        Listing li2 = new Listing("crypto", "crypto", "bitcoin", true, true, 100.0, "Month", p2);
        li2.setId(2L);

        Lease le1 = new Lease(Timestamp.valueOf("2030-07-01 10:00:00"), Timestamp.valueOf("2030-12-01 10:00:00"),
                p2, li1, p1);
        Lease le2 = new Lease(Timestamp.valueOf("2031-07-01 10:00:00"), Timestamp.valueOf("2031-12-01 10:00:00"),
                p2, li1, p1);
        Lease le3 = new Lease(Timestamp.valueOf("2032-07-01 10:00:00"), Timestamp.valueOf("2032-12-01 10:00:00"),
                p1, li2, p2);
        Lease le4 = new Lease(Timestamp.valueOf("2033-07-01 10:00:00"), Timestamp.valueOf("2033-12-01 10:00:00"),
                p1, li2, p2);
        Lease le5 = new Lease(Timestamp.valueOf("2034-07-01 10:00:00"), Timestamp.valueOf("2034-12-01 10:00:00"),
                p1, li2, p2);
        le1.setId(1L);
        le2.setId(2L);
        le3.setId(3L);
        le4.setId(4L);
        le5.setId(5L);

        List<Lease> leases1 = List.of(le1, le2);
        List<Lease> leases2 = List.of(le3, le4, le5);

        lenient().when(profileRepository.findProfileByUsername("messi")).thenReturn(pd1);
        lenient().when(profileRepository.findProfileByUsername("usr")).thenReturn(pd2);
        lenient().when(profileRepository.findProfileByUsername("notfound")).thenReturn(Optional.empty());

        lenient().when(jwtUtil.extractUsername(ArgumentMatchers.matches("messi"))).thenReturn("messi");
        lenient().when(jwtUtil.extractUsername(ArgumentMatchers.matches("usr"))).thenReturn("usr");
        lenient().when(jwtUtil.extractUsername(ArgumentMatchers.matches("notfound"))).thenReturn("notfound");

        lenient().when(leaseRepository.getLeasesByOwner(pd1.get())).thenReturn(leases1);
        lenient().when(leaseRepository.getLeasesByOwner(pd2.get())).thenReturn(leases2);

        lenient().when(leaseRepository.findById(1L)).thenReturn(Optional.of(le1));
        lenient().when(leaseRepository.findById(2L)).thenReturn(Optional.of(le2));
        lenient().when(leaseRepository.findById(3L)).thenReturn(Optional.of(le3));
        lenient().when(leaseRepository.findById(4L)).thenReturn(Optional.of(le4));
        lenient().when(leaseRepository.findById(5L)).thenReturn(Optional.of(le5));

        lenient().when(listingRepository.findById(1L)).thenReturn(Optional.of(li1));
        lenient().when(listingRepository.findById(2L)).thenReturn(Optional.of(li2));
    }

    @Test
    public void getMyLeasesReturnsCorrectNumberOfLeases() {
        ResponseEntity<List<LeaseResponse>> res1 = service.getMyLeases("Bearer messi");
        List<LeaseResponse> leases1 = res1.getBody();
        ResponseEntity<List<LeaseResponse>> res2 = service.getMyLeases("Bearer usr");
        List<LeaseResponse> leases2 = res2.getBody();


        assertEquals(leases1.size(), 2);
        assertEquals(leases2.size(), 3);
    }

    @Test
    public void getMyLeasesReturnsStatusCode200() {
        ResponseEntity<List<LeaseResponse>> res = service.getMyLeases("Bearer messi");

        assertEquals(res.getStatusCodeValue(), 200);
    }

    @Test
    public void getMyLeasesReturnsCorrectLeases() {
        ResponseEntity<List<LeaseResponse>> res1 = service.getMyLeases("Bearer messi");
        ResponseEntity<List<LeaseResponse>> res2 = service.getMyLeases("Bearer usr");
        List<LeaseResponse> leases1 = res1.getBody();
        List<LeaseResponse> leases2 = res2.getBody();

        LeaseResponse l1 = leases1.get(0);
        LeaseResponse l2 = leases1.get(1);
        LeaseResponse l3 = leases2.get(0);
        LeaseResponse l4 = leases2.get(1);
        LeaseResponse l5 = leases2.get(2);

        assertEquals(l1.getId(), 1L);
        assertEquals(l1.getIsApproved(), false);
        assertEquals(l1.getFromDatetime(), Timestamp.valueOf("2030-07-01 10:00:00"));
        assertEquals(l1.getToDatetime(), Timestamp.valueOf("2030-12-01 10:00:00"));
        assertEquals(l1.getIsCompleted(), false);
        assertEquals(l1.getProfileId(), 2L);
        assertEquals(l1.getListingId(), 1L);

        assertEquals(l2.getId(), 2L);
        assertEquals(l2.getIsApproved(), false);
        assertEquals(l2.getFromDatetime(), Timestamp.valueOf("2031-07-01 10:00:00"));
        assertEquals(l2.getToDatetime(), Timestamp.valueOf("2031-12-01 10:00:00"));
        assertEquals(l2.getIsCompleted(), false);
        assertEquals(l2.getProfileId(), 2L);
        assertEquals(l2.getListingId(), 1L);

        assertEquals(l3.getId(), 3);
        assertEquals(l3.getIsApproved(), false);
        assertEquals(l3.getFromDatetime(), Timestamp.valueOf("2032-07-01 10:00:00"));
        assertEquals(l3.getToDatetime(), Timestamp.valueOf("2032-12-01 10:00:00"));
        assertEquals(l3.getIsCompleted(), false);
        assertEquals(l3.getProfileId(), 1L);
        assertEquals(l3.getListingId(), 2L);

        assertEquals(l4.getId(), 4L);
        assertEquals(l4.getIsApproved(), false);
        assertEquals(l4.getFromDatetime(), Timestamp.valueOf("2033-07-01 10:00:00"));
        assertEquals(l4.getToDatetime(), Timestamp.valueOf("2033-12-01 10:00:00"));
        assertEquals(l4.getIsCompleted(), false);
        assertEquals(l4.getProfileId(), 1L);
        assertEquals(l4.getListingId(), 2L);

        assertEquals(l5.getId(), 5L);
        assertEquals(l5.getIsApproved(), false);
        assertEquals(l5.getFromDatetime(), Timestamp.valueOf("2034-07-01 10:00:00"));
        assertEquals(l5.getToDatetime(), Timestamp.valueOf("2034-12-01 10:00:00"));
        assertEquals(l5.getIsCompleted(), false);
        assertEquals(l5.getProfileId(), 1L);
        assertEquals(l5.getListingId(), 2L);
    }

    @Test
    public void getMyLeasesReturnsStatusCode404() {
        var res = service.getMyLeases("Bearer notfound");
        assertEquals(res.getStatusCodeValue(), 404);
    }

    @Test
    public void deleteLeaseReturnsStatusCode204() {
        var r1 = service.deleteLease(1L, "Bearer messi");
        var r2 = service.deleteLease(2L, "Bearer messi");
        var r3 = service.deleteLease(3L, "Bearer usr");
        var r4 = service.deleteLease(4L, "Bearer usr");
        var r5 = service.deleteLease(5L, "Bearer usr");

        assertEquals(r1.getStatusCodeValue(), 204);
        assertEquals(r2.getStatusCodeValue(), 204);
        assertEquals(r3.getStatusCodeValue(), 204);
        assertEquals(r4.getStatusCodeValue(), 204);
        assertEquals(r5.getStatusCodeValue(), 204);
    }

    @Test
    public void deleteLeaseReturnsStatusCode404() {
        // Username not in database
        var r1 = service.deleteLease(1L, "Bearer notfound");

        // Lease not in database
        var r2 = service.deleteLease(6L, "Bearer usr");
        var r3 = service.deleteLease(7L, "Bearer messi");

        assertEquals(r1.getStatusCodeValue(), 404);
        assertEquals(r2.getStatusCodeValue(), 404);
        assertEquals(r3.getStatusCodeValue(), 404);
    }
}