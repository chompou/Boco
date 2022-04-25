package boco.service.profile;

import boco.models.http.ListingResponse;
import boco.models.http.NotificationResponse;
import boco.models.profile.Notification;
import boco.models.rental.Listing;
import boco.repository.profile.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificatioRepository;

    @Autowired
    public NotificationService(NotificationRepository notificatioRepository) {
        this.notificatioRepository = notificatioRepository;
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
