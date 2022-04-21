package boco.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Notification {
    private long id;
    private String message;
    private String ur;
    private boolean isRead;

}
