package boco.models.http;

import boco.models.rental.CategoryType;
import boco.models.rental.Image;
import boco.models.rental.Listing;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor
public class ListingResponse {
    private Long id;
    private String name;
    private String description;
    private String address;
    private boolean isAvailable;
    private boolean isActive;
    private double price;
    private String priceType;
    private Timestamp lastChanged;
    private double rating;
    private List<CategoryType> categoryTypes;
    private Long profileId;
    private List<ImageResponse> images;

    public ListingResponse(Listing listing) {
        this.id = listing.getId();
        this.name = listing.getName();
        this.description = listing.getDescription();
        this.address = listing.getAddress();
        this.isAvailable = listing.isAvailable();
        this.isActive = listing.isActive();
        this.price = listing.getPrice();
        this.priceType = listing.getPriceType();
        this.lastChanged = listing.getLastChanged();
        this.rating = listing.getRating();
        this.categoryTypes = listing.getCategoryTypes();
        if (listing.getProfile() != null) this.profileId = listing.getProfile().getId();

        // Turn images into ImageResponses
        images = new ArrayList<>();
        for (Image img : listing.getImages()) {
            images.add(new ImageResponse(img));
        }
    }
}
