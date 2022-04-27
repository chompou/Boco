package boco.service.rental;

import boco.models.profile.Personal;
import boco.models.rental.Listing;
import boco.repository.profile.ProfileRepository;
import boco.repository.rental.ListingRepository;
import boco.service.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class ListingServiceTest {
    @InjectMocks
    private ListingService service;

    @Mock
    private ListingRepository listingRepository;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setup() {
        Personal p1 = new Personal("los", "la@la.com", "city", "LA", "pass","US", "12345678");
        Personal p2 = new Personal("miami", "mia@mi.fl", "city", "FL", "pass","US", "12345678");
        Personal p3 = new Personal("ny", "new@york.com", "city", "NY", "pass","US", "12345678");

        Listing l1 = new Listing("house", "house", "los", true, true, 100.0, "Month", p1);
        Listing l2 = new Listing("parking lot", "parking lot", "miami", true, true, 50.0, "Month", p2);
        Listing l3 = new Listing("penthouse", "penthouse", "ny", true, true, 150.0, "Month", p3);


        lenient()
                .when(listingRepository.findByDescriptionContainingOrNameContaining(any(), any(), any()))
                .thenReturn(null);
    }


    @Test
    public void getListingsTest() {

    }

}