package boco.repository.profile;

import boco.model.profile.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    int countByProfileIdAndIsReadFalse(Long profileId);
    int countByProfileId(Long profileId);
    List<Notification> findByProfileIdAndIsReadTrue(Long profileId);
    List<Notification> findByProfileIdAndIsReadFalse(Long profileId);
    List<Notification> findByProfileUsernameAndIsReadTrue(String username);
    List<Notification> findByProfileUsernameAndIsReadFalse(String username);
    List<Notification> findByProfileUsernameAndIsReadNull(String username);
}
