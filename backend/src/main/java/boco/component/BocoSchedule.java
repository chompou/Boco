package boco.component;

import boco.controller.security.LoginController;
import boco.service.profile.NotificationService;
import boco.service.profile.ProfileService;
import boco.service.rental.LeaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class BocoSchedule {

    private final Logger logger;
    private final ProfileService profileService;
    private final NotificationService notificationService;
    private final LeaseService leaseService;
    private final static Long DAILY = 1000L *60*60*24;

    @Autowired
    public BocoSchedule(ProfileService profileService, NotificationService notificationService,
                        LeaseService leaseService){
        this.logger = LoggerFactory.getLogger(LoginController.class);
        this.profileService = profileService;
        this.notificationService = notificationService;
        this.leaseService = leaseService;
    }

    @Scheduled(fixedDelay = DAILY, initialDelay = 360000)
    public void deleteUsers(){
        logger.info("Deleting users");
        try {
            profileService.deleteDeactivatedProfiles();
        }catch (Exception ignored){}

        logger.info("users deleted");
    }

    @Scheduled(fixedDelay = DAILY, initialDelay = 240000)
    public void remindUsers(){
        logger.info("Daily reminders");
        notificationService.dailyReminders();
    }

    @Scheduled(fixedDelay = DAILY, initialDelay = 120000)
    public void deleteDanglingLeases(){
        logger.info("Deleting dangling leases");
        leaseService.removeDangling();

    }
}
