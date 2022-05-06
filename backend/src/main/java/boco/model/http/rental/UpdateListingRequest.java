package boco.model.http.rental;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents updated fields in a listing when sending an HTTP PUT request to a REST controller.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UpdateListingRequest {
    private String description;
    private Boolean isActive;
    private Double price;
    private String priceType;

    private Long id;
}
