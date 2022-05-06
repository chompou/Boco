package boco.repository.rental;

import boco.model.rental.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Image entity
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    /**
     * Gets images by listing id
     * @param listingId listing id of image
     * @return the image found
     */
    List<Image> getImageByListing_Id(Long listingId);

}
