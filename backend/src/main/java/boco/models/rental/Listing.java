package boco.models.rental;

import boco.models.profile.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Getter @Setter @NoArgsConstructor
@Entity
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String category;
    private String address;
    private boolean isAvailable;
    private boolean isActive;
    private double price;
    private String priceType;
    private Timestamp lastChanged;
    private double rating;

    public Listing(String name, String description, String category,
                   String address, boolean isAvailable, boolean isActive,
                   double price, String priceType, Profile profile) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.address = address;
        this.isAvailable = isAvailable;
        this.isActive = isActive;
        this.price = price;
        this.priceType = priceType;
        this.profile = profile;

        // Setting lastChanged to creation time
        Date date = new Date();
        Timestamp createdTime = new Timestamp(date.getTime());
        this.lastChanged = createdTime;
    }

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(mappedBy = "listing")
    private List<Lease> leases;

    @OneToMany(mappedBy = "listing")
    private List<Image> images;

    @ManyToMany
    private List<CategoryType> categoryTypes;

    @Override
    public String toString() {
        return "Listing{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", address='" + address + '\'' +
                ", isAvailable=" + isAvailable +
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
