package boco.model.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MyNotificationsResponse {
    List<NotificationResponse> unread;
    List<NotificationResponse> read;
}
