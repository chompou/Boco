package boco.controller.websocket;

import boco.component.BocoSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/my/websocket")
public class testController {
    @Autowired
    private BocoSocket websocket;

    @RequestMapping("/sendAllMessage")
    public String testMulti(String message){
        websocket.sendAllMessage(message);
        return "message sent";
    }

    @RequestMapping("/sendOneMessage")
    public String testOne(String message, String userId){
        websocket.sendOneMessage(userId, message);
        return "message sent";
    }
}
