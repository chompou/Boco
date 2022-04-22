package boco.models.rental;

import boco.models.profile.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter @Setter @NoArgsConstructor
@Entity
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
