package boco.model.profile;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * JPA entity representing a password code.
 */
@Getter@Setter@NoArgsConstructor@AllArgsConstructor@Builder
@Entity
public class PasswordCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id; //The id of the password code
    @OneToOne
    Profile profile; // The profile the passwordcode is attached to
    String generatedCode; // The code itself
    Timestamp timestamp; //

    public PasswordCode(Profile profile, String generatedCode){
        this.profile = profile;
        this.generatedCode = generatedCode;
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
    }
}
