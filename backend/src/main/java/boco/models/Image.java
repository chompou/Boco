package boco.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Image {
    private long id; // Primary key
    private byte[] image;
    private String caption;
}
