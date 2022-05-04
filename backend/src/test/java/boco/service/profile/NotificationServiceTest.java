package boco.service.profile;

import boco.component.BocoSocket;
import boco.model.profile.Profile;
import boco.repository.profile.NotificationRepository;
import boco.repository.profile.ProfileRepository;
import boco.repository.rental.LeaseRepository;
import boco.service.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {
    @InjectMocks
    NotificationService notificationService;

    @Mock
    NotificationRepository notificationRepository;
    @Mock
    ProfileRepository profileRepository;
    @Mock
    LeaseRepository leaseRepository;
    @Mock
    BocoSocket webSocket;
    @Mock
    JwtUtil jwtUtil;


    @BeforeEach
    void setup(){
        Profile testProfile = Profile.builder().username("testUser").id(1l).build();


        lenient().when(notificationRepository.countByProfileIdAndIsReadFalse(ArgumentMatchers.anyLong())).thenReturn(200);
        lenient().when(profileRepository.findProfileByUsername("anyOtherUser")).thenReturn(Optional.empty());
        lenient().when(profileRepository.findProfileByUsername("testUser")).thenReturn(Optional.of(testProfile));
        lenient().when(jwtUtil.extractUsername("testUserToken")).thenReturn("testUser");
    }

    @Test
    void unreadForUser() {
        assertEquals(200, notificationService.unreadForUser(1l));
    }

    @Test
    void pushNotificationsFromJWT() {
        assertEquals(200, notificationService.pushNotificationsFromJWT("testUserToken"));
        assertEquals(500, notificationService.pushNotificationsFromJWT("anyOtherUser"));
    }


    @Test
    void addNewNotification() {
        /*
        ArgumentCaptor<Notification> notificationArgumentCaptor = ArgumentCaptor.forClass(Notification.class);
        Notification notification = new Notification();
        notification.setId(1l);
        lenient().doNothing().when(notificationRepository).save(notificationArgumentCaptor.capture());

        assertEquals(notification, notificationArgumentCaptor.getValue());
        
         */
    }

    @Test
    void newLeaseNotification() {
    }

    @Test
    void canceledLeaseForLease() {
    }

    @Test
    void canceledLeaseForOwner() {
    }

    @Test
    void approveLeaseNotification() {
    }

    @Test
    void completedLeaseNotification() {
    }

    @Test
    void convertNotifications() {
    }

    @Test
    void getMyNotifications() {
    }

    @Test
    void upDateNotifications() {
    }

    @Test
    void dailyReminders() {
    }
}