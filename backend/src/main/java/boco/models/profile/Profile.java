package boco.models.profile;

import boco.component.BocoHasher;
import boco.models.rental.Lease;
import boco.models.rental.Listing;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
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
    private String location;
    private String address;
    private Boolean isVerified;
    private String tlf;
    private Double ratingListing;
    private Double ratingProfile;
    private Double ratingGiven;
    //Time profile was deactivated by user
    private Timestamp deactivated;

    @OneToMany(mappedBy = "profile")
    @JsonBackReference
    private List<Lease> rentals;

    @OneToMany(mappedBy = "profile")
    @JsonBackReference
    private List<Lease> owned;

    @OneToMany(mappedBy = "profile")
    @JsonBackReference
    private List<Listing> listings;

    @OneToMany(mappedBy = "profile")
    @JsonBackReference
    private List<Notification> notifications;

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
                ", ratingProfile=" + ratingProfile +
                ", ratingGiven=" + ratingGiven +
                ", deactivated=" + deactivated +
                '}';
    }
}
