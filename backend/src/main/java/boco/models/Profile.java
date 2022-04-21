package boco.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter @NoArgsConstructor
public class Profile {
    private long id;
    private String username;
    private String email;
    private String description;
    private String displayName;
    private String passwordHash;
    private String address;
    private boolean isVerified;
    private String tlf;
    private double ratingListing;
    private double profile;
    private double ratingGiven;
    private Timestamp deactivated;
}
