package boco.model.http;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class MessageRequest {
    private Long receiverId;
    private String message;

    public MessageRequest(Long receiverId, String message) {
        this.receiverId = receiverId;
        this.message = message;
    }

    public MessageRequest(String message) {
        this.message = message;
    }

    public MessageRequest(int receiverId, String message) {
        this.receiverId = (long) receiverId;
        this.message = message;
    }

    public MessageRequest(String receiverId, String message) {
        this.receiverId = Long.parseLong(receiverId);
        this.message = message;
    }
}
