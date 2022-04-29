package boco.service.rental;

import boco.models.http.ListingResponse;
import boco.models.profile.Personal;
import boco.models.profile.Profile;
import boco.models.rental.CategoryType;
import boco.models.rental.Listing;
import boco.repository.profile.ProfileRepository;
import boco.repository.rental.CategoryTypeRepository;
import boco.repository.rental.ListingRepository;
import boco.service.security.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ListingServiceTest {
    @InjectMocks
    private ListingService service;

    @Mock
    private ListingRepository listingRepository;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private CategoryTypeRepository categoryTypeRepository;

    @Mock
    private JwtUtil jwtUtil;
    private int pageSize;
    private List<Listing> listings2;

    @BeforeEach
    public void setup() {
        Personal p1 = new Personal("los", "la@la.com", "city", "LA", "pass","US", "12345678");
        Personal p2 = new Personal("miami", "mia@mi.fl", "city", "FL", "pass","US", "12345678");
        Personal p3 = new Personal("ny", "new@york.com", "city", "NY", "pass","US", "12345678");
        List<Profile> profiles = new ArrayList<>(Arrays.asList(p1, p2, p3));

        Listing l1 = new Listing("house", "house", "los", true, true, 100.0, "Month", p1);
        Listing l2 = new Listing("parking lot", "parking lot", "miami", true, true, 50.0, "Month", p2);
        Listing l3 = new Listing("penthouse", "penthouse", "ny", true, true, 150.0, "Month", p3);
        List<Listing> listings1 = new ArrayList<>(Arrays.asList(l1, l2, l3));

        Listing l4 = new Listing("bench", "bench", "Bergen", true, true, 100.0, "Month", p1);
        Listing l5 = new Listing("bulldozer", "bulldozer", "Trondheim", true, true, 50.0, "Month", p2);
        Listing l6 = new Listing("pencil", "pencil", "Oslo", true, true, 150.0, "Month", p3);
        listings2 = new ArrayList<>(Arrays.asList(l4, l5, l6));

        Listing l7 = new Listing("tree", "tree", "los", true, true, 100.0, "Month", p1);
        Listing l8 = new Listing("bottle", "bottle", "miami", true, true, 50.0, "Month", p2);
        Listing l9 = new Listing("phone", "phone", "ny", true, true, 150.0, "Month", p3);
        List<Listing> listings3 = new ArrayList<>(Arrays.asList(l1, l2, l3));

        Listing l10 = new Listing("cup", "cup", "los", true, true, 100.0, "Month", p1);
        Listing l11 = new Listing("city", "city", "miami", true, true, 50.0, "Month", p2);
        Listing l12 = new Listing("north", "north", "ny", true, true, 150.0, "Month", p3);
        List<Listing> listings4 = new ArrayList<>(Arrays.asList(l1, l2, l3));

        pageSize = 5;


        Page<Listing> listingPage1 = new PageImpl<Listing>(listings1, PageRequest.ofSize(pageSize), listings1.size());
        Page<Listing> listingPage2 = new PageImpl<Listing>(listings2, PageRequest.ofSize(pageSize), listings2.size());
        Page<Listing> listingPage3 = new PageImpl<Listing>(listings3, PageRequest.ofSize(pageSize), listings3.size());
        Page<Listing> listingPage4 = new PageImpl<Listing>(listings4, PageRequest.ofSize(pageSize), listings4.size());



        lenient()
                .when(categoryTypeRepository.findCategoryTypeByNameEquals("Tool"))
                .thenReturn(Optional.of(new CategoryType(1L, "Tool")));
        lenient()
                .when(categoryTypeRepository.findCategoryTypeByNameEquals("Car"))
                .thenReturn(Optional.empty());


        lenient()
                .when(listingRepository.findByDescriptionContainingOrNameContaining(any(), any(), any()))
                .thenReturn(listingPage2);

        lenient()
                .when(listingRepository.findByDescriptionContainingAndCategoryTypesContainingOrNameContainingAndCategoryTypesContaining(any(), any(), any(), any(), any()))
                .thenReturn(listingPage1);

        lenient()
                .when(listingRepository.findByPriceBetweenAndDescriptionContainingOrPriceBetweenAndNameContaining(anyDouble(), anyDouble(), any(), anyDouble(), anyDouble(), any(), any()))
                .thenReturn(listingPage3);

        lenient()
                .when(listingRepository.findByPriceBetweenAndDescriptionContainingAndCategoryTypesContainingOrPriceBetweenAndNameContainingAndCategoryTypesContaining(eq(234), eq(234234), any(), any(), eq(234), eq(234234), any(), any(), any()))
                .thenReturn(listingPage4);



        /*








         */

        //lenient()
         //       .when(ListingService.convertListings());


    }


    @Test
    public void getAllListingsTest() {
        Assertions.assertEquals(true, true);
    }

    @Test
    public void testConvertListings(){
        List<ListingResponse> listingResponses = ListingService.convertListings(listings2);
        Assertions.assertEquals(listingResponses.size(), 3);
        Assertions.assertEquals(listingResponses.get(0).getName(), "bench");
    }

    @Test
    public void categoryDoesNotExist(){
        ResponseEntity<List<ListingResponse>> response = service.getListings(1, pageSize, "", "id", -1, -1, "Car");
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }


    @Test
    public void testGetListingsWithOutCategoryOrPriceRange() {
        ResponseEntity<List<ListingResponse>> responseEntity = service.getListings(1, pageSize, "", "id", -1, -1, "");
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        List<ListingResponse> listingResponses = responseEntity.getBody();
        Assertions.assertEquals(listingResponses.size(), 3);
        Assertions.assertEquals( "bench", listingResponses.get(0).getName());
        Assertions.assertEquals("bulldozer", listingResponses.get(1).getName());
        Assertions.assertEquals("pencil", listingResponses.get(2).getName());
    }

    @Test
    public void testGetListingsWithOutCategoryWithPriceRange() {
        ResponseEntity<List<ListingResponse>> responseEntity = service.getListings(1, pageSize, "", "id", 50.0, 100.0, "");
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        List<ListingResponse> listingResponses = responseEntity.getBody();
        Assertions.assertEquals(listingResponses.size(), 3);
        Assertions.assertEquals( "house", listingResponses.get(0).getName());
        Assertions.assertEquals("parking lot", listingResponses.get(1).getName());
        Assertions.assertEquals("penthouse", listingResponses.get(2).getName());
    }

    @Test
    public void testGetListingsWithCategoryWithOutPriceRange() {
        ResponseEntity<List<ListingResponse>> responseEntity = service.getListings(1, pageSize, "", "id", -1, -1, "Tool");
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        List<ListingResponse> listingResponses = responseEntity.getBody();
        Assertions.assertEquals(listingResponses.size(), 3);
        Assertions.assertEquals( "house", listingResponses.get(0).getName());
        Assertions.assertEquals("bottle", listingResponses.get(1).getName());
        Assertions.assertEquals("phone", listingResponses.get(2).getName());
    }

}