package boco.models.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ImageRequest {
    private byte[] image;
    private String caption;
    private long listingId;
}
