package boco.models.http;

import boco.models.rental.Image;
import boco.models.rental.Listing;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ImageResponse {
    private Long id;
    private byte[] image;
    private Long listingId;

    public ImageResponse(Image image) {
        this.id = image.getId();
        this.image = image.getImage();
        this.listingId = image.getListing().getId();
    }
}
