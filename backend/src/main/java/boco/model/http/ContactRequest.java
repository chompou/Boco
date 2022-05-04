package boco.model.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class ContactRequest {
    private String name;
    private String email;
    private String issue;
}
