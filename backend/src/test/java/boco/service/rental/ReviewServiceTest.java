package boco.service.rental;

import boco.model.http.rental.ReviewResponse;
import boco.model.profile.Personal;
import boco.model.rental.Lease;
import boco.model.rental.Listing;
import boco.model.rental.Review;
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
                "Emil", "letmepass","Baerum", "4:5", "12345678");
        p1.setId(1L);
        Personal p2 = new Personal("olavdei", "olav@gmail.com", "Test1",
                "Olav", "letmepass","Baerum", "3:3", "12345677");
        p2.setId(2L);

        Listing listing = new Listing("listing", "x", true, 4.4, "Day", p1);


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
        Assertions.assertEquals(3, res.getBody().size());
    }

    @Test
    public void getAllReviewsReturnsCorrectHttpStatusCode() {
        var res = service.getAllReviews();
        Assertions.assertEquals(200, res.getStatusCodeValue());
    }

    @Test
    public void getAllReviewsReturnsCorrectReviewData() {
        var res = service.getAllReviews();
        ReviewResponse r1 = res.getBody().get(0);
        ReviewResponse r2 = res.getBody().get(1);
        ReviewResponse r3 = res.getBody().get(2);

        Assertions.assertEquals(1L, r1.getId());
        Assertions.assertEquals(4.5, r1.getRating());
        Assertions.assertEquals("good!", r1.getComment());
        Assertions.assertEquals(1L, r1.getProfile_id());
        Assertions.assertEquals("Emil", r1.getDisplayName());

        Assertions.assertEquals(2L, r2.getId());
        Assertions.assertEquals(4.2, r2.getRating());
        Assertions.assertEquals("ok", r2.getComment());
        Assertions.assertEquals(2L, r2.getProfile_id());
        Assertions.assertEquals("Olav", r2.getDisplayName());

        Assertions.assertEquals(3L, r3.getId());
        Assertions.assertEquals(2.2, r3.getRating());
        Assertions.assertEquals("...", r3.getComment());
        Assertions.assertEquals(2L, r3.getProfile_id());
        Assertions.assertEquals("Olav", r3.getDisplayName());
    }
}