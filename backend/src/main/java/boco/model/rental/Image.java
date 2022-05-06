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
    @Column(length = 1048576)
    private byte[] image; // Bytes of the image

    @ManyToOne
    @JoinColumn(name = "listing_id")
    @JsonManagedReference
    private Listing listing; // Listing the image belongs to

    /**
     * Constructor with fields image and listing
     *
     * @param image Bytes of the image
     * @param listing Listing the image should be created for
     */
    public Image(byte[] image, Listing listing){
        this.image = image;
        this.listing = listing;
    }

    /**
     * Constructor with image field
     *
     * @param bytes Bytes of the image
     */
    public Image(byte[] bytes) {
        this.image = bytes;
    }
}
