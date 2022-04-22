package boco.models.profile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter @Setter @NoArgsConstructor
@Entity
public class Professional extends Profile {

    public Professional(String username, String email, String description, String displayName,
                   String passwordHash, String address, String tlf) {
        super(username, email, description, displayName, passwordHash, address, tlf);
    }
}
