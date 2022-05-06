package boco.model.profile;

import boco.component.BocoHasher;
import boco.model.rental.Lease;
import boco.model.rental.Listing;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * JPA entity representing a profile. A profile is either personal or professional.
 * Personal and professional inherits this class.
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique ID of the profile
    @Column(unique = true)
    private String username; // Unique username
    @Column(unique = true)
    private String email; // Unique email
    private String description; // Description of profile, written by the profile
    private String displayName; // Name to display
    private String passwordHash; // Hashed password
    private String location; // Coordinates of profile on format latitude:longitude
    private String address; // Address of profile
    private Boolean isVerified; // Boolean indicating if the profile is verified
    private String tlf; // Phone number of profile
    private Double ratingListing; // Average rating of the items (leases) posted by the profile
    private Double ratingAsOwner; // Average rating given to the profile as owner role in a lease
    private Double ratingAsLeasee; // Average rating given to the profile as a leasee role in a lease
    private Timestamp deactivated; // Time profile was deactivated by user. Null: not deactivated

    @OneToMany(mappedBy = "profile")
    @JsonBackReference
    private List<Lease> rentals; // List of leases where the profile is a leasee

    @OneToMany(mappedBy = "profile")
    @JsonBackReference
    private List<Lease> owned; // List of leases where the profile is an owner

    @OneToMany(mappedBy = "profile")
    @JsonBackReference
    private List<Listing> listings; // List of listings posted by profile

    @OneToMany(mappedBy = "profile")
    @JsonBackReference
    private List<Notification> notifications; // Notifications for profile

    /**
     * Constructor of this class. isVerified is set to false and deactivated is set to null by default.
     */
    public Profile(String username, String email, String description, String displayName,
                   String passwordHash, String address, String location, String tlf){
        this.username = username;
        this.email = email;
        this.description = description;
        this.displayName = displayName;
        this.passwordHash = BocoHasher.encode(passwordHash);
        this.address = address;
        this.location = location;
        this.tlf = tlf;

        // Setting some default values
        this.isVerified = false;
        this.deactivated = null;
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
                ", location=" + location +
                ", address='" + address + '\'' +
                ", isVerified=" + isVerified +
                ", tlf='" + tlf + '\'' +
                ", ratingListing=" + ratingListing +
                ", ratingAsOwner=" + ratingAsOwner +
                ", ratingAsLeasee=" + ratingAsLeasee +
                ", deactivated=" + deactivated +
                '}';
    }
}
