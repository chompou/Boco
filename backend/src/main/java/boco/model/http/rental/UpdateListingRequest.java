package boco.model.http.rental;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UpdateListingRequest {
    private String description;
    private Boolean isActive;
    private Double price;
    private String priceType;

    private Long id;
}
