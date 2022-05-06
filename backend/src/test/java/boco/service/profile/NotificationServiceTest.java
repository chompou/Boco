package boco.service.profile;

import boco.component.BocoSocket;
import boco.model.http.notification.MyNotificationsResponse;
import boco.model.http.notification.NotificationResponse;
import boco.model.http.rental.LeaseResponse;
import boco.model.http.rental.UpdateLeaseRequest;
import boco.model.profile.Notification;
import boco.model.profile.Profile;
import boco.model.rental.Lease;
import boco.model.rental.Listing;
import boco.repository.profile.NotificationRepository;
import boco.repository.profile.ProfileRepository;
import boco.repository.rental.LeaseRepository;
import boco.service.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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
    @Spy
    List<Notification> notifications;
    @Spy
    List<Profile> users;
    @Spy
    List<Notification> other;
    Profile testProfile;
    Lease testLease;
    Listing testListing;


    @BeforeEach
    void setup(){
        notifications = new ArrayList<>();
        users = new ArrayList<>();
        other = new ArrayList<>();


        MockitoAnnotations.initMocks(this);

        testProfile = Profile.builder().username("testUser").id(1l).build();

        testListing = Listing.builder().description("test").profile(testProfile).isActive(true).name("testListing").build();
        testLease = Lease.builder().profile(testProfile).listing(testListing).owner(testProfile).id(1l).build();

        ReflectionTestUtils.setField(notificationService, "notificationRepository", notificationRepository);
        ReflectionTestUtils.setField(notificationService, "profileRepository", profileRepository);
        ReflectionTestUtils.setField(notificationService, "leaseRepository", leaseRepository);
        ReflectionTestUtils.setField(notificationService, "jwtUtil", jwtUtil);


        lenient().when(notificationRepository.countByProfileIdAndIsReadFalse(ArgumentMatchers.anyLong())).thenReturn(200);
        lenient().when(profileRepository.findProfileByUsername("anyOtherUser")).thenReturn(Optional.empty());
        lenient().when(profileRepository.findProfileByUsername("testUser")).thenReturn(Optional.of(testProfile));
        lenient().when(jwtUtil.extractUsername("testUserToken")).thenReturn("testUser");
        lenient().when(profileRepository.findProfileById(anyLong())).thenReturn(Optional.of(testProfile));
        lenient().when(leaseRepository.findById(anyLong())).thenReturn(Optional.of(testLease));
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
    void newLeaseNotification() {
        LeaseResponse leaseResponse = new LeaseResponse();
        leaseResponse.setId(1l);
        leaseResponse.setFromDatetime(10000l);
        leaseResponse.setIsApproved(true);
        leaseResponse.setOwnerId(1l);
        leaseResponse.setProfileId(2l);
        leaseResponse.setItemName("test item");

        ResponseEntity<LeaseResponse> leaseResponseResponseEntity = new ResponseEntity<>(leaseResponse, HttpStatus.OK);

        Notification notification = notificationService.newLeaseNotification(leaseResponseResponseEntity);
        assertEquals("Someone wants to lease your item: test item", notification.getMessage());
        assertEquals("undefined for item: test item", notification.getUrl());
        assertEquals(testProfile, notification.getProfile());
        assertFalse(notification.getIsRead());
        assertNull(notification.getId());
    }

    @Test
    void canceledLeaseForLease() {
        Notification notification = notificationService.canceledLeaseForLease(testLease.getId());
        assertEquals("The item you tried to lease was canceled: testListing", notification.getMessage());
        assertEquals("undefined url: testListing", notification.getUrl());
        assertEquals(testProfile, notification.getProfile());
        assertFalse(notification.getIsRead());
        assertNull(notification.getId());
    }

    @Test
    void canceledLeaseForOwner() {
        Notification notification = notificationService.canceledLeaseForLease(testLease.getId());
        assertEquals("The item you tried to lease was canceled: testListing", notification.getMessage());
        assertEquals("undefined url: testListing", notification.getUrl());
        assertEquals(testProfile, notification.getProfile());
        assertFalse(notification.getIsRead());
        assertNull(notification.getId());
    }

    @Test
    void approveLeaseNotification() {
        UpdateLeaseRequest updateLeaseRequest = new UpdateLeaseRequest();
        updateLeaseRequest.setLeaseId(1l);
        Notification notification = notificationService.approveLeaseNotification(updateLeaseRequest);
        assertEquals("Your Lease was approved for item: testListing", notification.getMessage());
        assertEquals("undefined url: testListing", notification.getUrl());
        assertEquals(testProfile, notification.getProfile());
        assertFalse(notification.getIsRead());
        assertNull(notification.getId());

    }

    @Test
    void completedLeaseNotification() {
        UpdateLeaseRequest updateLeaseRequest = new UpdateLeaseRequest();
        updateLeaseRequest.setLeaseId(1l);
        Notification notification = notificationService.completedLeaseNotification(updateLeaseRequest);
        assertEquals("Your lease was set to complete by the owner of the item: testListing", notification.getMessage());
        assertEquals("undefined url: testListing", notification.getUrl());
        assertEquals(testProfile, notification.getProfile());
        assertFalse(notification.getIsRead());
        assertNull(notification.getId());
    }

    @Test
    void convertNotifications() {
        Notification notification1 = Notification.builder().id(1l).message("1").url("url").build();
        Notification notification2 = Notification.builder().id(2l).message("2").url("url").build();
        List<Notification> notifications = List.of(notification1, notification2);
        List<NotificationResponse> notificationResponses = NotificationService.convertNotifications(notifications);
        assertEquals(notifications.size(), notificationResponses.size());
        assertEquals(notificationResponses.get(0).getId(), notification1.getId());
        assertEquals(notificationResponses.get(0).getMessage(), notification1.getMessage());
        assertEquals(notificationResponses.get(0).getIsRead(), notification1.getIsRead());
        assertEquals(notificationResponses.get(0).getUrl(), notification1.getUrl());

        assertEquals(notificationResponses.get(1).getId(), notification2.getId());
        assertEquals(notificationResponses.get(1).getMessage(), notification2.getMessage());
        assertEquals(notificationResponses.get(1).getIsRead(), notification2.getIsRead());
        assertEquals(notificationResponses.get(1).getUrl(), notification2.getUrl());
    }

    @Test
    void getMyNotifications() {
        List<Notification> unread = new ArrayList<>();
        List<Notification> read = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            unread.add(Notification.builder().profile(testProfile).isRead(true).message(i+"").url("url").build());
            read.add(Notification.builder().profile(testProfile).isRead(false).message(i+"").url("url").build());
        }
        lenient().when(notificationRepository.findByProfileUsernameAndIsReadFalse("testUser")).thenReturn(unread);
        lenient().when(notificationRepository.findByProfileUsernameAndIsReadTrue("testUser")).thenReturn(read);

        ResponseEntity response = notificationService.getMyNotifications("testUserToken");
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof MyNotificationsResponse);
    }

    @Test
    void upDateNotifications() {
        List<Integer> toBeUpdated = List.of(1, 2, 3);
        Notification n1 = new Notification(), n2 = new Notification(), n3 = new Notification(), n4 = new Notification();
        n1.setProfile(testProfile);
        n2.setProfile(testProfile);
        n3.setProfile(testProfile);
        n4.setProfile(new Profile());
        List<Notification> notifications = List.of(n1, n2, n3);
        lenient().when(notificationRepository.getOne(1l)).thenReturn(n1);
        lenient().when(notificationRepository.getOne(2l)).thenReturn(n2);
        lenient().when(notificationRepository.getOne(3l)).thenReturn(n3);


        ResponseEntity response = notificationService.upDateNotifications(toBeUpdated, "BEARER testUserToken");
        assertTrue(n1.getIsRead());
        assertTrue(n2.getIsRead());
        assertTrue(n3.getIsRead());
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof MyNotificationsResponse);


        toBeUpdated = List.of(1, 2, 3, 4);
        ResponseEntity response1 = notificationService.upDateNotifications(toBeUpdated, "BEARER testUserToken");
        assertEquals(500, response1.getStatusCodeValue());
    }

    @Test
    void dailyReminders() {
        int numberOfNewNotifications = 2; //initial 2
        List<Lease> leases = new ArrayList<>();
        Date now = new Date();
        Date inAnHour = new Date(now.getTime() + 1000*60*60);
        Date tomorrow = new Date(now.getTime() + 1000*60*60*23);
        Date thePast = new Date(now.getTime()-1000*60*60*24);
        Date theFuture = new Date(now.getTime() + 1000*60*60*24*365);
        for (int i = 0; i < 5; i++) {
            Lease startAndCompleteInADay = Lease.builder().isApproved(true).profile(testProfile).owner(testProfile).isCompleted(false)
                    .fromDatetime(inAnHour.getTime()).toDatetime(tomorrow.getTime()).build();
            Lease startAndCompleteInADay1 = Lease.builder().isApproved(false).profile(testProfile).owner(testProfile).isCompleted(false)
                    .fromDatetime(inAnHour.getTime()).toDatetime(tomorrow.getTime()).build();
            Lease startAndCompleteInADay2 = Lease.builder().isApproved(true).profile(testProfile).owner(testProfile).isCompleted(true)
                    .fromDatetime(inAnHour.getTime()).toDatetime(tomorrow.getTime()).build();
            Lease startAndCompleteInADay3 = Lease.builder().isApproved(false).profile(testProfile).owner(testProfile).isCompleted(true)
                    .fromDatetime(inAnHour.getTime()).toDatetime(tomorrow.getTime()).build();
            leases.add(startAndCompleteInADay);
            leases.add(startAndCompleteInADay1);
            leases.add(startAndCompleteInADay2);
            leases.add(startAndCompleteInADay3);
            numberOfNewNotifications += 2;

            Lease startWithinADay = Lease.builder().isApproved(true).profile(testProfile).owner(testProfile).isCompleted(false)
                    .fromDatetime(inAnHour.getTime()).toDatetime(theFuture.getTime()).build();
            Lease startWithinADay1 = Lease.builder().isApproved(false).profile(testProfile).owner(testProfile).isCompleted(false)
                    .fromDatetime(inAnHour.getTime()).toDatetime(theFuture.getTime()).build();
            Lease startWithinADay2 = Lease.builder().isApproved(true).profile(testProfile).owner(testProfile).isCompleted(true)
                    .fromDatetime(inAnHour.getTime()).toDatetime(theFuture.getTime()).build();
            Lease startWithinADay3 = Lease.builder().isApproved(false).profile(testProfile).owner(testProfile).isCompleted(true)
                    .fromDatetime(inAnHour.getTime()).toDatetime(theFuture.getTime()).build();
            leases.add(startWithinADay);
            leases.add(startWithinADay1);
            leases.add(startWithinADay2);
            leases.add(startWithinADay3);
            numberOfNewNotifications += 2;

            Lease endWithinADay = Lease.builder().isApproved(true).profile(testProfile).owner(testProfile).isCompleted(false)
                    .fromDatetime(thePast.getTime()).toDatetime(inAnHour.getTime()).build();
            Lease endWithinADay1 = Lease.builder().isApproved(false).profile(testProfile).owner(testProfile).isCompleted(false)
                    .fromDatetime(thePast.getTime()).toDatetime(inAnHour.getTime()).build();
            Lease endWithinADay2 = Lease.builder().isApproved(true).profile(testProfile).owner(testProfile).isCompleted(true)
                    .fromDatetime(thePast.getTime()).toDatetime(inAnHour.getTime()).build();
            Lease endWithinADay3 = Lease.builder().isApproved(false).profile(testProfile).owner(testProfile).isCompleted(true)
                    .fromDatetime(thePast.getTime()).toDatetime(inAnHour.getTime()).build();
            leases.add(endWithinADay);
            leases.add(endWithinADay1);
            leases.add(endWithinADay2);
            leases.add(endWithinADay3);
            numberOfNewNotifications += 2;
        }

        Lease lease = Lease.builder().isApproved(true).profile(testProfile).owner(testProfile).isCompleted(false)
                .fromDatetime(now.getTime()).toDatetime(now.getTime()+1000*60*60*12).build();
        leases.add(lease);
        lenient().when(leaseRepository.findAll()).thenReturn(leases);
        notificationService.dailyReminders();
        verify(notificationRepository, times(numberOfNewNotifications)).save(any());
    }
}