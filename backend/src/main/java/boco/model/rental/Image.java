package boco.model.rental;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * JPA entity representing an image
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique ID of the image (primary key)
    @Column(length = 4000000)
    private byte[] image; // The image, stored as array of bytes

    @ManyToOne
    @JoinColumn(name = "listing_id")
    @JsonManagedReference
    private Listing listing;

    /**
     * Constructor of image with fields image and listing
     *
     * @param image Byte representation of an image
     * @param listing The listing the image belongs to
     */
    public Image(byte[] image, Listing listing){
        this.image = image;
        this.listing = listing;
    }

    /**
     * Constructor of image with field bytes
     *
     * @param bytes Bytes of image
     */
    public Image(byte[] bytes) {
        this.image = bytes;
    }
}
