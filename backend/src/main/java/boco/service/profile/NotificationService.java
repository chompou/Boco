package boco.service.profile;

import boco.component.BocoSocket;
import boco.repository.profile.NotificationRepository;
import boco.repository.profile.ProfileRepository;
import boco.service.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final NotificationRepository notificatioRepository;
    private final ProfileRepository profileRepository;
    private final BocoSocket webSocket;
    private final JwtUtil jwtUtil;

    @Autowired
    public NotificationService(NotificationRepository notificatioRepository, ProfileRepository profileRepository, JwtUtil jwtUtil, BocoSocket webSocket) {
        this.profileRepository = profileRepository;
        this.notificatioRepository = notificatioRepository;
        this.jwtUtil = jwtUtil;
        this.webSocket = webSocket;
    }

    public int unreadForUser(Long userId){
        return notificatioRepository.countByProfileIdAndIsReadFalse(userId);
    }

    public int pushNotificationsFromJWT(String jwt){
        try {
            Long id = profileRepository.findProfileByUsername(jwtUtil.extractUsername(jwt)).get().getId();
            webSocket.sendOneMessage(id +"", notificatioRepository.countByProfileIdAndIsReadFalse(id) + "");
        }catch (Exception e){
            return 500;
        }
        return 200;
    }
}
