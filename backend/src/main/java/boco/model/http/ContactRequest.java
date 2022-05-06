package boco.model.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a contact request sent from client.
 */
@Getter @Setter @AllArgsConstructor
public class ContactRequest {
    private String name;
    private String email;
    private String issue;
}
