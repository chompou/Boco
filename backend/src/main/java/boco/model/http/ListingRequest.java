package boco.model.http;

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
    private Boolean isActive;
    private Double price;
    private String priceType;
    private List<String> categoryNames = new ArrayList<>();
    private Long profileId; // ID of the profile listing the request

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
