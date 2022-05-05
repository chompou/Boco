package boco.model.profile;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor@Builder
@Entity
public class PasswordCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @OneToOne
    Profile profile;
    String generatedCode;
    Timestamp timestamp;

    public PasswordCode(Profile profile, String generatedCode){
        this.profile = profile;
        this.generatedCode = generatedCode;
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
    }
}
