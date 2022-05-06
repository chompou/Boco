package boco.service.profile;

import boco.component.BocoSocket;
import boco.model.http.notification.MyNotificationsResponse;
import boco.model.http.notification.NotificationResponse;
import boco.model.http.rental.LeaseResponse;
import boco.model.http.rental.UpdateLeaseRequest;
import boco.model.profile.Notification;
import boco.model.profile.Profile;
import boco.model.rental.Lease;
import boco.repository.profile.NotificationRepository;
import boco.repository.profile.ProfileRepository;
import boco.repository.rental.LeaseRepository;
import boco.service.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Notification service
 */
@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final ProfileRepository profileRepository;
    private final LeaseRepository leaseRepository;
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
    public NotificationService(NotificationRepository notificationRepository, ProfileRepository profileRepository,
                               LeaseRepository leaseRepository, JwtUtil jwtUtil, BocoSocket webSocket) {
        this.profileRepository = profileRepository;
        this.notificationRepository = notificationRepository;
        this.leaseRepository = leaseRepository;
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
        webSocket.sendOneMessage(userId +"", unreadForUser(userId) + "");
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

    public Notification newLeaseNotification(ResponseEntity<LeaseResponse> responseEntity){
        if ((responseEntity.getBody()!=null) || responseEntity.getStatusCode().is2xxSuccessful()){
            try {
                Profile profile = profileRepository.findProfileById(responseEntity.getBody().getProfileId()).get();
                String message = "Your lease request i created for item: " + responseEntity.getBody().getItemName();
                String url = "undefined for item: " + responseEntity.getBody().getItemName();
                return getNotification(profile,message, url);

            }catch(Exception ignored){

            }
        }
        return null;
    }

    public Notification canceledLeaseForLease(Long leaseId){
        Lease lease = leaseRepository.findById(leaseId).get();
        String message = "The item you tried to lease was canceled: " + lease.getListing().getName();
        String url = "undefined url: " + lease.getListing().getName();
        return getNotification(lease.getProfile(), message, url);
    }

    public Notification canceledLeaseForOwner(Long leaseId){
        Lease lease = leaseRepository.findById(leaseId).get();
        String message = "The item you tried to rent out was canceled: " + lease.getListing().getName();
        String url = "undefined url: " + lease.getListing().getName();
        return getNotification(lease.getOwner(), message, url);
    }

    private Notification getNotification(Profile profile, String message, String url){
        Notification notification = new Notification();
        notification.setProfile(profile);
        notification.setMessage(message);
        notification.setIsRead(false);
        notification.setUrl(url);
        return notification;
    }
    public Notification approveLeaseNotification(UpdateLeaseRequest updateLeaseRequest){
        Lease lease = leaseRepository.findById(updateLeaseRequest.getLeaseId()).get();
        Profile profile = profileRepository.findProfileById(lease.getProfile().getId()).get();
        String message = "Your Lease was approved for item: " + lease.getListing().getName();
        String url = "undefined url: " + lease.getListing().getName();
        return getNotification(profile, message, url);
    }

    public Notification completedLeaseNotification(UpdateLeaseRequest updateLeaseRequest){
        Lease lease = leaseRepository.findById(updateLeaseRequest.getLeaseId()).get();
        Profile profile = profileRepository.findProfileById(lease.getProfile().getId()).get();
        String message = "Your lease was set to complete by the owner of the item: " + lease.getListing().getName();
        String url = "undefined url: " + lease.getListing().getName();
        return getNotification(profile, message, url);
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
            List<Notification> unreadNull = notificationRepository.findByProfileUsernameAndIsReadNull(username);
            for (Notification n: unreadNull) {
                n.setIsRead(false);
                notificationRepository.save(n);
            }
            unread.addAll(unreadNull);
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
                    currentNotification.setIsRead(true);
                    notificationRepository.save(currentNotification);
                }
            }
            pushNotificationsFromJWT(token.substring(7));
            return getMyNotifications(token);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void dailyReminders(){
        Date now = new Date();
        Date tomorrow = new Date(now.getTime() + (1000*60*60*24));
        List<Lease> leases = leaseRepository.findAll();
        for (Lease lease:leases) {
            boolean approved = lease.getIsApproved();
            boolean completed = lease.getIsCompleted();
            if (approved && !completed){
                Date from = new Date(lease.getFromDatetime());
                Date to = new Date(lease.getToDatetime());
                if (from.before(tomorrow) && from.after(now) && to.before(tomorrow) && to.after(now)){
                    try{
                        Notification ownerNotification = new Notification();
                        ownerNotification.setMessage("You have a lease tomorrow, remember to deliver and receive it");
                        ownerNotification.setProfile(lease.getOwner());
                        ownerNotification.setUrl("not yet defined url: ref lease:" + lease.getId());
                        ownerNotification.setIsRead(false);
                        addNewNotification(ownerNotification);
                    }catch (Exception ignored){}
                    try {
                        Notification customerNotification = new Notification();
                        customerNotification.setMessage("You have a lease tomorrow, remember to pick it up and give it back");
                        customerNotification.setProfile(lease.getProfile());
                        customerNotification.setUrl("not yet defined url: ref lease:" + lease.getId());
                        customerNotification.setIsRead(false);
                        addNewNotification(customerNotification);
                    }catch (Exception ignored){}
                }else if (from.before(tomorrow) && from.after(now)){
                    try{
                        Notification ownerNotification = new Notification();
                        ownerNotification.setMessage("You have a lease starting tomorrow, remember to deliver it");
                        ownerNotification.setProfile(lease.getOwner());
                        ownerNotification.setUrl("not yet defined url: ref lease:" + lease.getId());
                        ownerNotification.setIsRead(false);
                        addNewNotification(ownerNotification);
                    }catch (Exception ignored){}
                    try {
                        Notification customerNotification = new Notification();
                        customerNotification.setMessage("You have a lease starting tomorrow, remember to pick it up");
                        customerNotification.setProfile(lease.getProfile());
                        customerNotification.setUrl("not yet defined url: ref lease:" + lease.getId());
                        customerNotification.setIsRead(false);
                        addNewNotification(customerNotification);
                    }catch (Exception ignored){}
                }else if (to.before(tomorrow) && to.after(now)){
                    try{
                        Notification ownerNotification = new Notification();
                        ownerNotification.setMessage("You have a lease finishing tomorrow, remember to receive it");
                        ownerNotification.setProfile(lease.getOwner());
                        ownerNotification.setUrl("not yet defined url: ref lease:" + lease.getId());
                        ownerNotification.setIsRead(false);
                        addNewNotification(ownerNotification);
                    }catch (Exception ignored){}
                    try {
                        Notification customerNotification = new Notification();
                        customerNotification.setMessage("You have a lease finishing tomorrow, remember to give it back");
                        customerNotification.setProfile(lease.getProfile());
                        customerNotification.setUrl("not yet defined url: ref lease:" + lease.getId());
                        customerNotification.setIsRead(false);
                        addNewNotification(customerNotification);
                    }catch (Exception ignored){}
                }

            }

        }
    }
}
