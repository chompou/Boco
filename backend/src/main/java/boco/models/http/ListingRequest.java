package boco.models.http;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ListingRequest {
    private String name;
    private String description;
    private String category;
    private String address;
    private boolean isAvailable;
    private boolean isActive;
    private double price;
    private String priceType;

    private Long profileId; // ID of the profile listing the request

    @Override
    public String toString() {
        return "ListingRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", address='" + address + '\'' +
                ", isAvailable=" + isAvailable +
                ", isActive=" + isActive +
                ", price=" + price +
                ", priceType='" + priceType + '\'' +
                '}';
    }
}
