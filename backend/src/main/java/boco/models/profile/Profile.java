package boco.models.profile;

import boco.models.rental.Lease;
import boco.models.rental.Listing;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter @Setter @NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String description;
    private String displayName;
    private String passwordHash;
    private String address;
    private Boolean isVerified;
    private String tlf;
    private Double ratingListing;
    private Double ratingProfile;
    private Double ratingGiven;
    private Timestamp deactivated;

    @OneToMany
    private List<Lease> rentals;

    @OneToMany
    private List<Listing> listings;

    @OneToMany
    private List<Notification> notifications;

    public Profile(String username, String email, String description, String displayName,
                   String passwordHash, String address, String tlf) {
        this.username = username;
        this.email = email;
        this.description = description;
        this.displayName = displayName;
        this.passwordHash = passwordHash;
        this.address = address;
        this.tlf = tlf;

        // Setting some default values
        this.isVerified = false;

    }

    /**
     * toString method excluding the all properties of type list
     *
     * @return String representation of a profile instance
     */
    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", displayName='" + displayName + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", address='" + address + '\'' +
                ", isVerified=" + isVerified +
                ", tlf='" + tlf + '\'' +
                ", ratingListing=" + ratingListing +
                ", ratingProfile=" + ratingProfile +
                ", ratingGiven=" + ratingGiven +
                ", deactivated=" + deactivated +
                '}';
    }
}
