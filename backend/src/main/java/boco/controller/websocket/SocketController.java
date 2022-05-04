package boco.controller.websocket;

import boco.component.BocoSocket;
import boco.model.http.notification.MessageRequest;
import boco.service.profile.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/my/websocket")
public class SocketController {
    @Autowired
    private BocoSocket websocket;

    @Autowired
    private NotificationService notificationService;

    @RequestMapping("/sendAllMessage")
    public String testMulti(@RequestBody MessageRequest messageRequest){
        websocket.sendAllMessage(messageRequest.getMessage());
        return "message sent";
    }

    @RequestMapping("/sendOneMessage")
    public String testOne(@RequestBody MessageRequest messageRequest){
        websocket.sendOneMessage(messageRequest.getReceiverId().toString(), messageRequest.getMessage());
        return "message sent";
    }

    @RequestMapping("/updateNotifications")
    public ResponseEntity<?> updateNotifications(@RequestHeader("Authorization") String authHeader){
        if (notificationService.pushNotificationsFromJWT(authHeader.substring(7)) == 500){
            return new  ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok("notifications updated");
    }
}
