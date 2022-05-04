package boco.model.http.rental;

import boco.model.rental.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ReviewResponse {
    private Long id;
    private Double rating;
    private String comment;
    private Long profile_id;
    private String displayName;

    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.rating = review.getRating();
        this.comment = review.getComment();

        try {
            if (this.id == review.getLease().getLeaseeReview().getId()) {
                this.profile_id = review.getLease().getOwner().getId();
                this.displayName = review.getLease().getOwner().getDisplayName();
            } else {
                this.profile_id = review.getLease().getProfile().getId();
                this.displayName = review.getLease().getProfile().getDisplayName();
            }
        } catch (NullPointerException e) {
            // handle
            e.printStackTrace();
        }
    }
}
