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

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Boolean isActive;
    private Double price;
    private String priceType;
    private Timestamp lastChanged;
    private Double rating;
    @ManyToOne
    @JoinColumn(name = "profile_id")
    @JsonManagedReference
    private Profile profile;

    @OneToMany(mappedBy = "listing")
    @JsonBackReference
    private List<Lease> leases;

    @OneToMany(mappedBy = "listing")
    @JsonBackReference
    private List<Image> images;

    @ManyToMany
    private List<CategoryType> categoryTypes;

    public Listing(String name, String description,
                   Boolean isActive,
                   Double price, String priceType, Profile profile) {
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.price = price;
        this.priceType = priceType;
        this.profile = profile;

        // Setting lastChanged to creation time
        Date date = new Date();
        Timestamp createdTime = new Timestamp(date.getTime());
        this.lastChanged = createdTime;
        images = new ArrayList<>();
        categoryTypes = new ArrayList<>();
    }

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
