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
    private Double ratingProfile;
    private Double ratingGiven;
    private Timestamp deactivated;

    @OneToMany
    private List<Lease> rentals;

    @OneToMany
    private List<Listing> listings;

    @OneToMany
    private List<Notification> notifications;

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
