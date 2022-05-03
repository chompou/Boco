package boco.service.rental;

import boco.models.http.ListingRequest;
import boco.models.http.ListingResponse;
import boco.models.http.ReviewResponse;
import boco.models.profile.Personal;
import boco.models.profile.Profile;
import boco.models.rental.CategoryType;
import boco.models.rental.Lease;
import boco.models.rental.Listing;
import boco.models.rental.Review;
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
import org.mockito.internal.util.collections.ListUtil;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.swing.plaf.ListUI;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        Listing l7 = new Listing("tree", "tree", "los", true, true, 200.0, "Month", p1);
        Listing l8 = new Listing("bottle", "bottle", "miami", true, true, 250.0, "Month", p2);
        Listing l9 = new Listing("phone", "phone", "ny", true, true, 300.0, "Month", p3);
        List<Listing> listings3 = new ArrayList<>(Arrays.asList(l7, l8, l9));

        Listing l10 = new Listing("cup", "cup", "los", true, true, 400.0, "Month", p1);
        Listing l11 = new Listing("city", "city", "miami", true, true, 500.0, "Month", p2);
        Listing l12 = new Listing("north", "north", "ny", true, true, 1500.0, "Month", p3);
        List<Listing> listings4 = new ArrayList<>(Arrays.asList(l10, l11, l12));

        List<Listing> listings5 = Stream.of(listings1, listings2, listings3, listings4)
                .flatMap(Collection::stream).collect(Collectors.toList());
        pageSize = 5;

        Page<Listing> listingPage1 = new PageImpl<Listing>(listings1, PageRequest.ofSize(pageSize), listings1.size());
        Page<Listing> listingPage2 = new PageImpl<Listing>(listings2, PageRequest.ofSize(pageSize), listings2.size());
        Page<Listing> listingPage3 = new PageImpl<Listing>(listings3, PageRequest.ofSize(pageSize), listings3.size());
        Page<Listing> listingPage4 = new PageImpl<Listing>(listings4, PageRequest.ofSize(pageSize), listings4.size());

        Lease le1 = new Lease(1651175214L, 651218414L, p1, l1, null);
        Lease le2 = new Lease(1650008914L, 1650959314L, p2, l1, null);
        Lease le3 = new Lease(1650008914L, 1650959314L, p2, l1, null);
        Lease le4 = new Lease(1650008914L, 1650959314L, p2, l1, null);
        Lease le5 = new Lease(1650008914L, 1650959314L, p2, l2, null);
        Lease le6 = new Lease(1650008914L, 1650959314L, p2, l2, null);
        l1.setLeases(new ArrayList<>(Arrays.asList(le1, le2, le3, le4)));

        Review r1 = new Review(4.0, "test");
        Review r2 = new Review(2.0, "Test2");
        Review r3 = new Review(3.5, "Litt sein i levering");

        r1.setLease(le1);
        r2.setLease(le2);
        r3.setLease(le3);
        le1.setItemReview(r1);
        le2.setItemReview(r2);
        le3.setItemReview(r3);







        lenient()
                .when(categoryTypeRepository.findCategoryTypeByNameEquals("Tool"))
                .thenReturn(Optional.of(new CategoryType(1L, "Tool")));
        lenient()
                .when(categoryTypeRepository.findCategoryTypeByNameEquals("Car"))
                .thenReturn(Optional.empty());
        lenient()
                .when(listingRepository.getListingByPriceRange(anyDouble(), anyDouble(), any()))
                .thenReturn(listings5);

        lenient()
                .when(listingRepository.findById(eq(1L)))
                .thenReturn(Optional.of(l1));
        lenient()
                .when(listingRepository.findById(eq(2L)))
                .thenReturn(Optional.of(l2));
        lenient()
                .when(listingRepository.findById(eq(3L)))
                .thenReturn(Optional.empty());
        lenient()
                .when(listingRepository.findById(eq(4L)))
                .thenReturn(Optional.of(l3));
        lenient()
                .when(jwtUtil.extractUsername(eq("1")))
                .thenReturn("los");
        lenient()
                .when(jwtUtil.extractUsername(eq("2")))
                .thenReturn(null);
        lenient()
                .when(profileRepository.findProfileByUsername("los"))
                .thenReturn(Optional.empty());
        lenient()
                .when(profileRepository.findProfileByUsername("miami"))
                .thenReturn(Optional.of(p2));








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
        ResponseEntity<List<ListingResponse>> response = service.getListings(1, pageSize, "", "id:ASC", -1, -1, "Car", "");
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }


    @Test
    public void testGetListings() {
        ResponseEntity<List<ListingResponse>> responseEntity = service.getListings(1, pageSize, "", "price:ASC", -1, -1, "", "");
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        List<ListingResponse> listingResponses = responseEntity.getBody();
        Assertions.assertEquals(listingResponses.size(), 5);
        Assertions.assertEquals( "pencil", listingResponses.get(0).getName());
    }

    @Test
    public void testGetListingsWithCategory() {
        //TODO add category
        ResponseEntity<List<ListingResponse>> responseEntity = service.getListings(1, pageSize, "", "id:ASC", -1, -1, "", "");
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        List<ListingResponse> listingResponses = responseEntity.getBody();
        Assertions.assertEquals(5, listingResponses.size());
        Assertions.assertEquals( "pencil", listingResponses.get(0).getName());
    }

    @Test
    public void testGetListingsWithPriceRange() {
        ResponseEntity<List<ListingResponse>> responseEntity = service.getListings(1, pageSize, "", "id:ASC", 175.0, 2000.0, "", "");
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        List<ListingResponse> listingResponses = responseEntity.getBody();
        Assertions.assertEquals(5, listingResponses.size());
        Assertions.assertEquals( "pencil", listingResponses.get(0).getName());
    }

    @Test
    public void testGetListingsWithDesc() {
        ResponseEntity<List<ListingResponse>> responseEntity = service.getListings(1, pageSize, "", "price:DESC", 50.0, 100.0, "", "");
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        List<ListingResponse> listingResponses = responseEntity.getBody();
        Assertions.assertEquals(5, listingResponses.size());
        Assertions.assertEquals( "tree", listingResponses.get(0).getName());
    }

    @Test
    public void testGetListingReviewsOfListingThatDoesNotExist(){
        ResponseEntity<List<ReviewResponse>> responseEntity = service.getListingReviews(3L, pageSize, 0);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testGetListingReviewsOfListingWithNoListings(){
        ResponseEntity<List<ReviewResponse>> responseEntity = service.getListingReviews(2L, pageSize, 0);
        Assertions.assertEquals(0, responseEntity.getBody().size());
    }

    @Test
    public void testGetListingReviewsOfListingWithNoReviews(){
        ResponseEntity<List<ReviewResponse>> responseEntity = service.getListingReviews(4L, pageSize, 0);
        Assertions.assertEquals(0, responseEntity.getBody().size());
    }

    @Test
    public void testGetListingReviewsOfListingWithReviews(){
        ResponseEntity<List<ReviewResponse>> responseEntity = service.getListingReviews(1L, pageSize, 0);
        Assertions.assertEquals(3, responseEntity.getBody().size());
    }

    @Test
    public void pagenationWorks(){
        ResponseEntity<List<ReviewResponse>> responseEntity = service.getListingReviews(1L, 2, 1);
        Assertions.assertEquals(1, responseEntity.getBody().size());
        Assertions.assertEquals(3.5, responseEntity.getBody().get(0).getRating());
    }

    @Test
    public void testGetListingByInvalidId(){
        ResponseEntity<ListingResponse> responseEntity = service.getListingById(3L);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void getListingByValidId(){
        ResponseEntity<ListingResponse> responseEntity = service.getListingById(1L);
        Assertions.assertEquals("house", responseEntity.getBody().getName());
    }

    @Test
    public void createListingInvalidProfile(){
        ListingRequest listingRequest = new ListingRequest("Motorbike", "Goes fast.", "On the hill", true, true, 50.0, "hour", null, 1L);
        ResponseEntity<ListingResponse> responseEntity = service.createListing(listingRequest, null, "Bearer 1");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void createListingInvalidToken(){
        ListingRequest listingRequest = new ListingRequest("Motorbike", "Goes fast.", "On the hill", true, true, 50.0, "hour", null, 2L);
        ResponseEntity<ListingResponse> responseEntity = service.createListing(listingRequest, null, "Bearer 2");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }






}