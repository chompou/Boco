package boco.models.profile;

import lombok.*;

import javax.persistence.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private String url;
    private Boolean isRead;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;
}
