package boco.service.rental;

import boco.models.http.ReviewResponse;
import boco.models.profile.Personal;
import boco.models.rental.Lease;
import boco.models.rental.Listing;
import boco.models.rental.Review;
import boco.repository.rental.ReviewRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.lenient;


@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    @InjectMocks
    private ReviewService service;

    @Mock
    private ReviewRepository repo;

    @BeforeEach
    public void setup() {
        Personal p1 = new Personal("emilgl", "gluckemil@gmail.com", "Test",
                "Emil", "letmepass","Baerum", "12345678");
        p1.setId(1L);
        Personal p2 = new Personal("olavdei", "olav@gmail.com", "Test1",
                "Olav", "letmepass","Baerum", "12345677");
        p2.setId(2L);

        Listing listing = new Listing("listing", "x", "Adr", true, true, 4.4, "Day", p1);


        Lease lease = new Lease(1L, true, null, null, true, null, null, null, p2, listing, p1);
        List<Review> reviews = new ArrayList<>();
        Review r1 = new Review(1L, 4.5, "good!", lease);
        Review r2 = new Review(2L, 4.2, "ok", lease);
        Review r3 = new Review(3L, 2.2, "...", lease);
        reviews.add(r1);
        reviews.add(r2);
        reviews.add(r3);

        lease.setLeaseeReview(r1);
        lease.setItemReview(r2);
        lease.setOwnerReview(r3);

        lenient().when(repo.findAll()).thenReturn(reviews);
    }

    @Test
    public void getAllReviewsReturnsCorrectNumberOfReviewsTest() {
        var res = service.getAllReviews();
        Assertions.assertEquals(res.getBody().size(), 3);
    }

    @Test
    public void getAllReviewsReturnsCorrectHttpStatusCode() {
        var res = service.getAllReviews();
        Assertions.assertEquals(res.getStatusCodeValue(), 200);
    }

    @Test
    public void getAlReviewsReturnsCorrectReviewData() {
        var res = service.getAllReviews();
        ReviewResponse r1 = res.getBody().get(0);
        ReviewResponse r2 = res.getBody().get(1);
        ReviewResponse r3 = res.getBody().get(2);

        Assertions.assertEquals(r1.getId(), 1L);
        Assertions.assertEquals(r1.getRating(), 4.5);
        Assertions.assertEquals(r1.getComment(), "good!");
        Assertions.assertEquals(r1.getProfile_id(), 2L);
        Assertions.assertEquals(r1.getDisplayName(), "Olav");

        Assertions.assertEquals(r2.getId(), 2L);
        Assertions.assertEquals(r2.getRating(), 4.2);
        Assertions.assertEquals(r2.getComment(), "ok");
        Assertions.assertEquals(r2.getProfile_id(), 2L);
        Assertions.assertEquals(r2.getDisplayName(), "Olav");

        Assertions.assertEquals(r3.getId(), 3L);
        Assertions.assertEquals(r3.getRating(), 2.2);
        Assertions.assertEquals(r3.getComment(), "...");
        Assertions.assertEquals(r3.getProfile_id(), 1L);
        Assertions.assertEquals(r3.getDisplayName(), "Emil");
    }
}