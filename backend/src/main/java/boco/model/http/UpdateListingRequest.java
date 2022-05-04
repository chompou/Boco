package boco.model.http;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UpdateListingRequest {
    private String description;
    private String address;
    private boolean isAvailable;
    private boolean isActive;
    private double price;
    private String priceType;

    private Long listingId;
}
