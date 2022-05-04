package boco.model.profile;

import boco.component.BocoHasher;
import boco.model.rental.Lease;
import boco.model.rental.Listing;
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
    private String address;
    private Boolean isVerified;
    private String tlf;
    private Double ratingListing;
    private Double ratingAsOwner;
    private Double ratingAsLeasee;
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
                   String passwordHash, String address, String tlf){
        this.username = username;
        this.email = email;
        this.description = description;
        this.displayName = displayName;
        this.passwordHash = BocoHasher.encode(passwordHash);
        this.address = address;
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
