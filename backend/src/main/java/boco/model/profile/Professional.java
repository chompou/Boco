package boco.model.profile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * JPA entity representing a professional profile
 */
@Getter @Setter @NoArgsConstructor
@Entity
public class Professional extends Profile {

    /** Constructor calling the corresponding constructor of parent class Professional */
    public Professional(String username, String email, String description, String displayName,
                   String passwordHash, String address, String location, String tlf) {
        super(username, email, description, displayName, passwordHash, address, location, tlf);
    }
}
