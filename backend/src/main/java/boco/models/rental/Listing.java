package boco.models.rental;

import boco.models.rental.CategoryType;
import boco.models.rental.Image;
import boco.models.rental.Lease;
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
    private String category;
    private String address;
    private boolean isAvailable;
    private boolean isActive;
    private double price;
    private String priceType;
    private Timestamp lastChanged;
    private double rating;

    @OneToMany
    private List<Lease> leases;

    @OneToMany
    private List<Image> images;

    @ManyToMany
    private List<CategoryType> categoryTypes;




}
