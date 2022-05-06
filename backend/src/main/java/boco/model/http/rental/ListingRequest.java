package boco.model.http.rental;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a listing when sending an HTTP POST request to a REST controller.
 *
 * See boco.model.rental.Listing class for documentation of the fields of this class.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ListingRequest {
    private String name;
    private String description;
    private Boolean isActive;
    private Double price;
    private String priceType;
    private List<String> categoryNames = new ArrayList<>();
    private Long profileId; // ID of the profile listing the request

    /** @return String representation of this object */
    @Override
    public String toString() {
        return "ListingRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                ", price=" + price +
                ", priceType='" + priceType + '\'' +
                '}';
    }
}
