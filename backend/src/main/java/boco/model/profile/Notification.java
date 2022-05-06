package boco.model.profile;

import lombok.*;

import javax.persistence.*;

/**
 * JPA entity representing a notification
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique notification ID (primary key)
    private String message; // Notification message
    private String url; // Link to site related to the notification
    private Boolean isRead; // Boolean indicating if the notification is read

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile; // Receiver of the notification
}
