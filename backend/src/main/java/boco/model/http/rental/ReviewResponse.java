package boco.model.http.rental;

import boco.model.rental.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents data of a review. The class is used to send a response from a REST controller when
 * receiving review related requests.
 *
 * See boco.model.rental.Review class for documentation of the fields of this class.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ReviewResponse {
    private Long id;
    private Double rating;
    private String comment;
    private Long profile_id;
    private String displayName;

    /**
     * Creates a reviewResponse from an Review.
     * @param review The review who's data is being returned.
     */
    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.rating = review.getRating();
        this.comment = review.getComment();

        try {
            //Checks if the owner of the review is the owner of the item or the leasee
            if (review.getLease().getLeaseeReview() != null && this.id == review.getLease().getLeaseeReview().getId()) {
                //The leasee is the owner of the review
                this.profile_id = review.getLease().getOwner().getId();
                this.displayName = review.getLease().getOwner().getDisplayName();
            } else {
                //The owner of the item is the owner of the review
                this.profile_id = review.getLease().getProfile().getId();
                this.displayName = review.getLease().getProfile().getDisplayName();
            }
        } catch (NullPointerException e) {
            // Should never reach here
            e.printStackTrace();
        }
    }
}
