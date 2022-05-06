package boco.repository.profile;

import boco.model.profile.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for notification entity
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    /**
     * @param profileId ID of the profile
     * @return Number of unread notifications for a profile with profileId
     */
    int countByProfileIdAndIsReadFalse(Long profileId);

    /**
     * @param username unique username of the profile
     * @return List of read notifications of a profile defined by username
     */
    List<Notification> findByProfileUsernameAndIsReadTrue(String username);

    /**
     * @param username unique username of the profile
     * @return List of unread notifications of a profile defined by username
     */
    List<Notification> findByProfileUsernameAndIsReadFalse(String username);
    List<Notification> findByProfileUsernameAndIsReadNull(String username);
}
