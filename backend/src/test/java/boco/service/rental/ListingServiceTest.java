package boco.service.rental;

import boco.model.http.rental.*;
import boco.model.profile.Personal;
import boco.model.profile.Profile;
import boco.model.rental.*;
import boco.repository.profile.ProfileRepository;
import boco.repository.rental.CategoryTypeRepository;
import boco.repository.rental.ImageRepository;
import boco.repository.rental.LeaseRepository;
import boco.repository.rental.ListingRepository;
import boco.service.security.JwtUtil;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ListingServiceTest {
    @InjectMocks
    private ListingService service;

    @Mock
    private ListingRepository listingRepository;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private CategoryTypeRepository categoryTypeRepository;

    @Mock
    private LeaseRepository leaseRepository;

    @Mock
    private JwtUtil jwtUtil;
    private int pageSize;
    private List<Listing> listings2;

    @BeforeEach
    public void setup() {
        Personal p1 = new Personal("los", "la@la.com", "city", "LA", "pass","US", "4:2", "12345678");
        Personal p2 = new Personal("miami", "mia@mi.fl", "city", "FL", "pass","US", "4:5", "12345678");
        Personal p3 = new Personal("ny", "new@york.com", "city", "NY", "pass","US", "6:3", "12345678");
        p1.setId(1L);
        p2.setId(2L);
        p3.setId(3L);
        List<Profile> profiles = new ArrayList<>(Arrays.asList(p1, p2, p3));

        Listing l1 = new Listing("house", "house", true, 100.0, "Month", p1); l1.setId(1L); l1.setRating(1.0);
        Listing l2 = new Listing("parking lot", "parking lot", true, 50.0, "Month", p2); l2.setId(2L);l2.setRating(4.0);
        Listing l3 = new Listing("penthouse", "penthouse", true, 150.0, "Month", p3); l3.setId(3L);
        List<Listing> listings1 = new ArrayList<>(Arrays.asList(l1, l2, l3));

        Listing l4 = new Listing("bench", "bench",  true, 99.0, "Month", p1); l4.setId(4L);l4.setRating(2.0);
        Listing l5 = new Listing("bulldozer", "bulldozer",  true, 51.0, "Month", p2); l5.setId(5L);l5.setRating(3.0);
        Listing l6 = new Listing("pencil", "pencil",  true, 150.0, "Month", p3); l6.setId(6L);
        listings2 = new ArrayList<>(Arrays.asList(l4, l5, l6));

        Listing l7 = new Listing("tree", "tree",  true, 200.0, "Month", p1); l7.setId(7L);
        Listing l8 = new Listing("bottle", "bottle",  true, 250.0, "Month", p2); l8.setId(8L);
        Listing l9 = new Listing("phone", "phone",  true, 300.0, "Month", p3); l9.setId(9L);
        List<Listing> listings3 = new ArrayList<>(Arrays.asList(l7, l8, l9));

        Listing l10 = new Listing("cup", "cup",  true, 400.0, "Month", p1); l10.setId(10L);
        Listing l11 = new Listing("city", "city", true, 500.0, "Month", p2); l11.setId(11L);
        Listing l12 = new Listing("north", "north",  true, 1500.0, "Month", p3); l12.setId(12L);
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
        le1.setId(1L);
        le2.setId(2L);
        le3.setId(3L);
        le4.setId(4L);
        le5.setId(5L);
        le6.setId(6L);

        List<Lease> leases1 =new ArrayList<>(Arrays.asList(le1, le2, le3, le4));
        l1.setLeases(leases1);

        Review r1 = new Review(4.0, "test");
        Review r2 = new Review(2.0, "Test2");
        Review r3 = new Review(3.5, "Litt sein i levering");
        r1.setId(1L);
        r2.setId(2L);
        r3.setId(3L);

        r1.setLease(le1);
        r2.setLease(le2);
        r3.setLease(le3);
        le1.setItemReview(r1);
        le2.setItemReview(r2);
        le3.setItemReview(r3);

        File file = new File("src/main/resources/testbilde2.png");
        byte[] imageBytes = new byte[0];
        try {
            imageBytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image image = new Image();
        image.setImage(imageBytes);
        image.setId(1L);
        List<Image> images1 = new ArrayList<>(Arrays.asList(image));





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
                .when(listingRepository.findById(eq(7L)))
                .thenReturn(Optional.of(l7));
        lenient()
                .when(jwtUtil.extractUsername(eq("1")))
                .thenReturn("los");
        lenient()
                .when(jwtUtil.extractUsername(eq("2")))
                .thenReturn(null);
        lenient()
                .when(jwtUtil.extractUsername(eq("3")))
                .thenReturn("miami");
        lenient()
                .when(jwtUtil.extractProfileFromAuthHeader(eq("Bearer 1")))
                .thenReturn(p1);
        lenient()
                .when(jwtUtil.extractProfileFromAuthHeader(eq("Bearer 2")))
                .thenReturn(null);
        lenient()
                .when(jwtUtil.extractProfileFromAuthHeader(eq("Bearer 3")))
                .thenReturn(p3);
        lenient()
                .when(profileRepository.findProfileByUsername("los"))
                .thenReturn(Optional.empty());
        lenient()
                .when(profileRepository.findProfileByUsername("miami"))
                .thenReturn(Optional.of(p2));
        lenient()
                .when(listingRepository.save(any()))
                .thenReturn(l1);
        lenient()
                .when(imageRepository.save(any()))
                .thenReturn(image);
        lenient()
                .when(listingRepository.findAllByIsActiveTrue())
                .thenReturn(List.of(l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12));
        lenient()
                .when(leaseRepository.getLeasesByListing_Id(eq(1L)))
                .thenReturn(leases1);
        lenient()
                .when(leaseRepository.saveAll(any()))
                .thenReturn(null);
        lenient()
                .when(imageRepository.getImageByListing_Id(any()))
                .thenReturn(images1);
        lenient()
                .when(imageRepository.saveAll(any()))
                .thenReturn(null);
        lenient()
                .doNothing()
                .when(listingRepository).deleteById(eq(1L));








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
        ResponseEntity<ListingResultsResponse> response = service.getListings(1, pageSize, "", "id:ASC", -1, -1, "Car", "");
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }


    @Test
    public void testGetListings() {
        ResponseEntity<ListingResultsResponse> responseEntity = service.getListings(1, pageSize, "", "price:ASC", -1, -1, "", "");
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        List<ListingResponse> listingResponses = responseEntity.getBody().getListingResponses();
        Assertions.assertEquals(listingResponses.size(), 5);
        Assertions.assertEquals( "pencil", listingResponses.get(0).getName());
    }

    @Test
    public void testGetListingsWithCategory() {
        //TODO add category
        ResponseEntity<ListingResultsResponse> responseEntity = service.getListings(1, pageSize, "", "id:ASC", -1, -1, "", "");
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        List<ListingResponse> listingResponses = responseEntity.getBody().getListingResponses();
        Assertions.assertEquals(5, listingResponses.size());
        Assertions.assertEquals( "pencil", listingResponses.get(0).getName());
    }

    @Test
    public void testGetListingsWithPriceRange() {
        ResponseEntity<ListingResultsResponse> responseEntity = service.getListings(0, 100, "", "id:ASC", 175.0, 2000.0, "", "");
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        List<ListingResponse> listingResponses = responseEntity.getBody().getListingResponses();
        Assertions.assertEquals(6, listingResponses.size());
        Assertions.assertEquals( "tree", listingResponses.get(0).getName());
        Assertions.assertEquals( "bottle", listingResponses.get(1).getName());
        Assertions.assertEquals( "phone", listingResponses.get(2).getName());
        Assertions.assertEquals( "cup", listingResponses.get(3).getName());
        Assertions.assertEquals( "city", listingResponses.get(4).getName());
        Assertions.assertEquals( "north", listingResponses.get(5).getName());
    }

    @Test
    public void testGetListingsWithPricesDesc() {
        ResponseEntity<ListingResultsResponse> responseEntity = service.getListings(0, 100, "", "price:DESC", 50.0, 100.0, "", "");
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        List<ListingResponse> listingResponses = responseEntity.getBody().getListingResponses();
        Assertions.assertEquals(4, listingResponses.size());
        Assertions.assertEquals( "house", listingResponses.get(0).getName());
        Assertions.assertEquals( "bench", listingResponses.get(1).getName());
        Assertions.assertEquals( "bulldozer", listingResponses.get(2).getName());
        Assertions.assertEquals( "parking lot", listingResponses.get(3).getName());
    }

    @Test
    public void testGetListingsByDistance() {
        ResponseEntity<ListingResultsResponse> responseEntity = service.getListings(0, 100, "", "distance:DESC", 50.0, 100.0, "", "40:40");
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        List<ListingResponse> listingResponses = responseEntity.getBody().getListingResponses();
        Assertions.assertEquals(4, listingResponses.size());
        Assertions.assertEquals( "parking lot", listingResponses.get(0).getName());
        Assertions.assertEquals( "bulldozer", listingResponses.get(1).getName());
        Assertions.assertEquals( "house", listingResponses.get(2).getName());
        Assertions.assertEquals( "bench", listingResponses.get(3).getName());
        Assertions.assertTrue(listingResponses.get(0).getDistance() <= listingResponses.get(1).getDistance());
        Assertions.assertTrue(listingResponses.get(1).getDistance() <= listingResponses.get(2).getDistance());
        Assertions.assertTrue(listingResponses.get(2).getDistance() <= listingResponses.get(3).getDistance());

    }

    @Test
    public void testGetListingsByRating() {
        ResponseEntity<ListingResultsResponse> responseEntity = service.getListings(0, 100, "", "rating:DESC", 50.0, 100.0, "", "40:40");
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        List<ListingResponse> listingResponses = responseEntity.getBody().getListingResponses();
        Assertions.assertEquals(4, listingResponses.size());
        Assertions.assertEquals( "parking lot", listingResponses.get(0).getName());
        Assertions.assertEquals( "bulldozer", listingResponses.get(1).getName());
        Assertions.assertEquals( "bench", listingResponses.get(2).getName());
        Assertions.assertEquals( "house", listingResponses.get(3).getName());
        Assertions.assertTrue(listingResponses.get(0).getRating() > listingResponses.get(1).getRating());
        Assertions.assertTrue(listingResponses.get(1).getRating() > listingResponses.get(2).getRating());
        Assertions.assertTrue(listingResponses.get(2).getRating() > listingResponses.get(3).getRating());
    }

    @Test
    public void testGetListingsByLastChagned() {
        ResponseEntity<ListingResultsResponse> responseEntity = service.getListings(0, 100, "", "lastChanged:DESC", 50.0, 100.0, "", "40:40");
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        List<ListingResponse> listingResponses = responseEntity.getBody().getListingResponses();
        Assertions.assertEquals(4, listingResponses.size());
        Assertions.assertEquals( "bulldozer", listingResponses.get(0).getName());
        Assertions.assertEquals( "bench", listingResponses.get(1).getName());
        Assertions.assertEquals( "parking lot", listingResponses.get(2).getName());
        Assertions.assertEquals( "house", listingResponses.get(3).getName());


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
        ListingRequest listingRequest = new ListingRequest("Motorbike", "Goes fast.", true, 50.0, "hour", null, 1L);
        ResponseEntity<ListingResponse> responseEntity = service.createListing(listingRequest, null, "Bearer 1");
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void createListingInvalidToken(){
        ListingRequest listingRequest = new ListingRequest("Motorbike", "Goes fast.", true, 50.0, "hour", null, 2L);
        ResponseEntity<ListingResponse> responseEntity = service.createListing(listingRequest, null, "Bearer 2");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void createListingSuccessfully(){
        ListingRequest listingRequest = new ListingRequest("Motorbike", "Goes fast.", true, 50.0, "hour", new ArrayList<>(), 3L);
        Image image = new Image("iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAA1BMVEWsyPIniFeoAAAASElEQVR4nO3BgQAAAADDoPlTX+AIVQEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADwDcaiAAFXD1ujAAAAAElFTkSuQmCC".getBytes(StandardCharsets.UTF_8));
        MultipartFile multipartFile = new MockMultipartFile("ImageFile", image.getImage());
        ResponseEntity<ListingResponse> responseEntity = service.createListing(listingRequest, multipartFile, "Bearer 3");
        Mockito.verify(listingRepository, Mockito.times(3)).save(any());
        Mockito.verify(imageRepository, Mockito.times(1)).save(any());
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals(1L, responseEntity.getBody().getId());
    }

    @Test
    public void updateListingSuccessfully(){
        UpdateListingRequest updateListingRequest = new UpdateListingRequest("house", true, 200.0, "day", 1L);
        ResponseEntity<ListingResponse> responseEntity = service.updateListing(updateListingRequest, "Bearer 1");
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Mockito.verify(listingRepository, Mockito.times(1)).save(any());
        Assertions.assertEquals(1L, responseEntity.getBody().getId());
    }

    @Test
    public void updateListingInvalidToken(){
        UpdateListingRequest updateListingRequest = new UpdateListingRequest("house", true, 200.0, "day", 1L);
        ResponseEntity<ListingResponse> responseEntity = service.updateListing(updateListingRequest, "Bearer 2");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void updateListingInvalidProfile(){
        UpdateListingRequest updateListingRequest = new UpdateListingRequest("house", true, 200.0, "day", 1L);
        ResponseEntity<ListingResponse> responseEntity = service.updateListing(updateListingRequest, "Bearer 3");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void deleteListingSuccessfully(){
        ResponseEntity<HttpStatus> responseEntity = service.deleteListing(7L, "Bearer 1");
        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        Mockito.verify(leaseRepository, Mockito.times(1)).saveAll(any());
        Mockito.verify(listingRepository, Mockito.times(1)).deleteById(any());
        Mockito.verify(imageRepository, Mockito.times(1)).saveAll(any());
    }

    @Test
    public void deleteListingInvalidToken(){
        ResponseEntity<HttpStatus> responseEntity = service.deleteListing(1L, "Bearer 2");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Mockito.verify(leaseRepository, Mockito.times(0)).saveAll(any());
        Mockito.verify(listingRepository, Mockito.times(0)).deleteById(any());
        Mockito.verify(imageRepository, Mockito.times(0)).saveAll(any());
    }

    @Test
    public void deleteListingInvalidProfile(){
        ResponseEntity<HttpStatus> responseEntity = service.deleteListing(2L, "Bearer 1");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Mockito.verify(leaseRepository, Mockito.times(0)).saveAll(any());
        Mockito.verify(listingRepository, Mockito.times(0)).deleteById(any());
        Mockito.verify(imageRepository, Mockito.times(0)).saveAll(any());
    }


    @Test
    public void deleteListingByListingSuccessfully(){
        File file = new File("src/main/resources/testbilde2.png");
        byte[] imageBytes = new byte[0];
        try {
            imageBytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image image = new Image();
        image.setImage(imageBytes);

        Profile profile = new Profile("test", "test@gmail.com", "JEg er en person", "pers123", "pass123", "klokkegata 99", "50:40", "31415926");
        Listing listing = new Listing("test", "test1", true, 50.0, "hour", profile);
        profile.setListings(Arrays.asList(listing));
        image.setListing(listing);
        listing.setImages(Arrays.asList(image));

        service.deleteListing(listing);
        Mockito.verify(leaseRepository, Mockito.times(1)).saveAll(any());
        Mockito.verify(listingRepository, Mockito.times(1)).deleteById(any());
        Mockito.verify(imageRepository, Mockito.times(1)).saveAll(any());
    }








}