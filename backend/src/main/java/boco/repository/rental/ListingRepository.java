package boco.repository.rental;

import boco.models.rental.Listing;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    Page<Listing> findByDescriptionContainingOrNameContaining(String description, String name, Pageable pageable);

    Page<Listing> findByDescriptionContainingOrNameContainingAndPriceBetween(String description, String name, double minPrice, double maxPrice, Pageable pageable);
}
