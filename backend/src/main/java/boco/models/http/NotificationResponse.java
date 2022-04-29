package boco.models.http;

import boco.models.profile.Notification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class NotificationResponse {
    private Long id;
    private String message;
    private String url;
    private Boolean isRead;

    public NotificationResponse(Notification notification) {
        this.id = notification.getId();
        this.message = notification.getMessage();
        this.url = notification.getUrl();
        this.isRead = notification.getIsRead();
    }
}
