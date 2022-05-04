package boco.service.profile;

import boco.component.BocoSocket;
import boco.model.http.MyNotificationsResponse;
import boco.model.http.NotificationResponse;
import boco.model.profile.Notification;
import boco.repository.profile.NotificationRepository;
import boco.repository.profile.ProfileRepository;
import boco.service.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Notification service
 */
@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final ProfileRepository profileRepository;
    private final BocoSocket webSocket;
    private final JwtUtil jwtUtil;

    /**
     * Instantiates a new Notification service.
     *
     * @param notificationRepository the notification repository
     * @param profileRepository      the profile repository
     * @param jwtUtil                the jwt util
     * @param webSocket              the web socket
     */
    @Autowired
    public NotificationService(NotificationRepository notificationRepository, ProfileRepository profileRepository, JwtUtil jwtUtil, BocoSocket webSocket) {
        this.profileRepository = profileRepository;
        this.notificationRepository = notificationRepository;
        this.jwtUtil = jwtUtil;
        this.webSocket = webSocket;
    }

    /**
     * Unread for user int.
     * Counts unread notifications
     *
     * @param userId the user id
     * @return the int
     */
    public int unreadForUser(Long userId){
        return this.notificationRepository.countByProfileIdAndIsReadFalse(userId);
    }

    /**
     * Push notifications from jwt.
     * Pushes number of unread notifications to logged in users websocket connection
     *
     * @param jwt the jwt
     * @return the int
     */
    public int pushNotificationsFromJWT(String jwt){
        try {
            Long id = profileRepository.findProfileByUsername(jwtUtil.extractUsername(jwt)).get().getId();
            pushToProfile(id);
        }catch (Exception e){
            return 500;
        }
        return 200;
    }

    /**
     * Push to profile.
     * Pushes notifications to specified users websocket
     *
     * @param userId the user id
     */
    public void pushToProfile(Long userId){
        try {
            webSocket.sendOneMessage(userId +"", unreadForUser(userId) + "");
        }catch (Exception ignored){

        }
    }

    /**
     * Add new notification.
     * Puts notification in database, pushes notifications to receiver
     *
     * @param notification the notification
     */
    public void addNewNotification(Notification notification){
        try{
            this.notificationRepository.save(notification);
            pushToProfile(notification.getId());
        }catch (Exception ignored){

        }
    }

    /**
     * Convert notifications from notification to notification response
     *
     * @param notifications the notifications
     * @return the list
     */
    public static List<NotificationResponse> convertNotifications(List<Notification> notifications){
        List<NotificationResponse> notificationResponses = new ArrayList<>();
        for (Notification notification :
                notifications) {
            notificationResponses.add(new NotificationResponse(notification));
        }
        return notificationResponses;
    }

    public ResponseEntity<?> getMyNotifications(String token) {
        try {
            String username = jwtUtil.extractUsername(token.substring(7));
            List<Notification> read = notificationRepository.findByProfileUsernameAndIsReadFalse(username);
            List<Notification> unread = notificationRepository.findByProfileUsernameAndIsReadTrue(username);
            MyNotificationsResponse response = new MyNotificationsResponse(convertNotifications(read), convertNotifications(unread));
            return ResponseEntity.ok(response);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> upDateNotifications(List<Integer> toBeUpdated, String token) {
        try {
            String username = jwtUtil.extractUsername(token.substring(7));
            for (Integer i:toBeUpdated) {
                Notification currentNotification = notificationRepository.getOne((long) i);
                if (currentNotification.getProfile().getUsername().equals(username)){
                    System.out.println("ok username for notification: " + i);
                    currentNotification.setIsRead(true);
                    notificationRepository.save(currentNotification);
                }else {
                    System.out.println("not ok for noti: " +i);
                }
            }
            pushNotificationsFromJWT(token.substring(7));
            return getMyNotifications(token);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
