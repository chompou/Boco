package boco.model.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class UpdatePasswordRequest {
    String passwordHash1;
    String passwordHash2;
}
