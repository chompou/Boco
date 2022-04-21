package boco.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Review {
    private long id; // Primary key
    private double rating;
    private String comment;
}
