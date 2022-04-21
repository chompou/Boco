package boco.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

@Getter @Setter @NoArgsConstructor
public class Listing {
    private long id; // Primary key
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



}
