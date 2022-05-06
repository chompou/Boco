package boco.model.rental;

import boco.model.profile.Profile;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * JPA entity representing a listing (item)
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique ID of listing (primary key)
    private String name; // Name of the listing
    private String description; // Description of the item being listed
    private Boolean isActive; // Boolean indicating if the listing is active
    private Double price; // Price to lease the item of the listing for 1 hour
    private String priceType; // Price type: Hour/Day/Week
    private Timestamp lastChanged; // Datetime the listing was last changed
    private Double rating; // Average rating of the item of the listing

    @ManyToOne
    @JoinColumn(name = "profile_id")
    @JsonManagedReference
    private Profile profile; // Profile that published the listing

    @OneToMany(mappedBy = "listing")
    @JsonBackReference
    private List<Lease> leases; // Leases of listing

    @OneToMany(mappedBy = "listing")
    @JsonBackReference
    private List<Image> images; // Images of listing

    @ManyToMany
    private List<CategoryType> categoryTypes; // Categories of the listing

    /**
     * Constructor of Listing.
     * lastChanged is set to the time this object is constructed.
     * Images and categories are initialized to empty lists.
     * */
    public Listing(String name, String description, Boolean isActive,
                   Double price, String priceType, Profile profile) {
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.price = price;
        this.priceType = priceType;
        this.profile = profile;
        this.rating = -1.0;

        // Setting lastChanged to creation time
        Date date = new Date();
        this.lastChanged = new Timestamp(date.getTime());

        // Initializing images and categories to empty lists
        images = new ArrayList<>();
        categoryTypes = new ArrayList<>();
    }

    /** @return String representation of a listing */
    @Override
    public String toString() {
        return "Listing{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                ", price=" + price +
                ", priceType='" + priceType + '\'' +
                ", lastChanged=" + lastChanged +
                ", rating=" + rating +
                ", profile=" + profile +
                ", leases=" + leases +
                ", images=" + images +
                ", categoryTypes=" + categoryTypes +
                '}';
    }
}
