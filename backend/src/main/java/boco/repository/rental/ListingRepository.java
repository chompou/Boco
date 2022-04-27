package boco.repository.rental;

import boco.models.rental.CategoryType;
import boco.models.rental.Listing;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    Page<Listing> findByDescriptionContainingOrNameContaining(String description, String name, Pageable pageable);

    Page<Listing> findByDescriptionContainingAndCategoryTypesContainingOrNameContainingAndCategoryTypesContaining(String description, CategoryType category, String name, CategoryType category1, Pageable pageable);

    Page<Listing> findByPriceBetweenAndDescriptionContainingOrPriceBetweenAndNameContaining(double minPrice, double maxPrice, String description, double minPrice1, double maxPrice1, String name,  Pageable pageable);

    Page<Listing> findByPriceBetweenAndDescriptionContainingAndCategoryTypesContainingOrPriceBetweenAndNameContainingAndCategoryTypesContaining(double minPrice, double maxPrice, String description, CategoryType category, double minPrice1, double maxPrice1, String name, CategoryType category1,  Pageable pageable);

    @Query(value = "UPDATE Lease lease SET lease.listing = " +
            "(SELECT l FROM Listing l WHERE l.id = 1)" +
            "WHERE lease.listing.id = ?1")
    @Modifying
    int updateLeaseWhenListingDeleted(Long listingId);
}
