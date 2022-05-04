package boco.repository.rental;

import boco.model.rental.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> getImageByListing_Id(Long listingId);

}
