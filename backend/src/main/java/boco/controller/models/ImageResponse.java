package boco.controller.models;

import boco.models.rental.Image;
import boco.models.rental.Listing;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ImageResponse {
    private Long id;
    private byte[] image;
    private String caption;
    private Long listingId;

    public ImageResponse(Image image) {
        this.id = image.getId();
        this.image = image.getImage();
        this.caption = image.getCaption();
        this.listingId = image.getListing().getId();
    }
}
