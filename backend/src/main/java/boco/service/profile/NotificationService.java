package boco.service.profile;

import boco.repository.profile.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final NotificationRepository notificatioRepository;

    @Autowired
    public NotificationService(NotificationRepository notificatioRepository) {
        this.notificatioRepository = notificatioRepository;
    }
}
