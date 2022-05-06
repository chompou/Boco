package boco.model.profile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * JPA entity representing a personal profile
 */
@Getter @Setter @NoArgsConstructor
@Entity
public class  Personal extends Profile {
    /** Constructor calling the corresponding constructor of parent class Profile */
    public Personal(String username, String email, String description, String displayName,
                        String passwordHash, String address, String location, String tlf) {
        super(username, email, description, displayName, passwordHash, address, location, tlf);
    }

}
