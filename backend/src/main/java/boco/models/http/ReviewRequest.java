package boco.models.http;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReviewRequest {
    private Double rating; // Rating 1-5
    private String comment;
    private Long leaseId; // ID of the lease where review should be added
}
