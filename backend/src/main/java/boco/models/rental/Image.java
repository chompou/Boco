package boco.models.rental;

import boco.models.profile.Profile;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private byte[] image;
    public Image(byte[] image, Listing listing){
        this.image = image;
        this.listing = listing;
    }
    @ManyToOne
    @JoinColumn(name = "listing_id")
    @JsonManagedReference
    private Listing listing;


    public Image(byte[] bytes) {
        this.image = bytes;
    }

    public Image(long size) {
        this.id = size;
    }
}
