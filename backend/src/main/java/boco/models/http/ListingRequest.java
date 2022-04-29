package boco.models.http;

import boco.models.rental.CategoryType;
import boco.models.rental.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ListingRequest {
    private String name;
    private String description;
    private String address;
    private boolean isAvailable;
    private boolean isActive;
    private double price;
    private String priceType;
    private List<String> categoryNames = new ArrayList<>();
    private Long profileId; // ID of the profile listing the request

    @Override
    public String toString() {
        return "ListingRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", isAvailable=" + isAvailable +
                ", isActive=" + isActive +
                ", price=" + price +
                ", priceType='" + priceType + '\'' +
                '}';
    }
}
