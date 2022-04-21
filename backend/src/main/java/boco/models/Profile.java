package boco.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter @Setter @NoArgsConstructor
@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String description;
    private String displayName;
    private String passwordHash;
    private String address;
    private Boolean isVerified;
    private String tlf;
    private Double ratingListing;
    private Double profile;
    private Double ratingGiven;
    private Timestamp deactivated;

    @OneToMany
    private List<Lease> rentals;

    @OneToMany
    private List<Listing> listings;

    @OneToMany
    private List<Notification> notifications;
}
