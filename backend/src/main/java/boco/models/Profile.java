package boco.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

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
}
