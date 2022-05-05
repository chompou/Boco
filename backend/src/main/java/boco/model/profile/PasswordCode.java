package boco.model.profile;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor@Builder
public class PasswordCode {
    @Id
    @GeneratedValue
    Long id;
    @OneToOne
    Profile profile;
    String code;
    Timestamp timestamp;

    public PasswordCode(Profile profile, String code){
        this.profile = profile;
        this.code = code;
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
    }
}
