package boco.service.profile;

import boco.component.BocoSocket;
import boco.models.http.NotificationResponse;
import boco.models.profile.Notification;
import boco.repository.profile.NotificationRepository;
import boco.repository.profile.ProfileRepository;
import boco.service.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final ProfileRepository profileRepository;
    private final BocoSocket webSocket;
    private final JwtUtil jwtUtil;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, ProfileRepository profileRepository, JwtUtil jwtUtil, BocoSocket webSocket) {
        this.profileRepository = profileRepository;
        this.notificationRepository = notificationRepository;
        this.jwtUtil = jwtUtil;
        this.webSocket = webSocket;
    }

    public int unreadForUser(Long userId){
        return this.notificationRepository.countByProfileIdAndIsReadFalse(userId);
    }

    public int pushNotificationsFromJWT(String jwt){
        try {
            Long id = profileRepository.findProfileByUsername(jwtUtil.extractUsername(jwt)).get().getId();
            pushToProfile(id);
        }catch (Exception e){
            return 500;
        }
        return 200;
    }

    public void pushToProfile(Long userId){
        try {
            webSocket.sendOneMessage(userId +"", unreadForUser(userId) + "");
        }catch (Exception ignored){

        }
    }

    public void addNewNotification(Notification notification){
        try{
            this.notificationRepository.save(notification);
            pushToProfile(notification.getId());
        }catch (Exception ignored){

        }
    }

    public static List<NotificationResponse> convertNotifications(List<Notification> notifications){
        List<NotificationResponse> notificationResponses = new ArrayList<>();
        for (Notification notification :
                notifications) {
            notificationResponses.add(new NotificationResponse(notification));
        }
        return notificationResponses;
    }
}
