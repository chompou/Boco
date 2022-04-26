package boco.repository.rental;

import boco.models.rental.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT l.ownerReview FROM Lease l WHERE l.profile.id = ?1")
    Optional<Review> getIfWrittenAboutOwnerAndWrittenBy(long id);

    @Query("SELECT l.itemReview FROM Lease l WHERE l.profile.id = ?1")
    Optional<Review> getIfWrittenAboutItemAndWrittenBy(long id);

    @Query("SELECT l.leaseeReview FROM Lease l WHERE l.owner.id = ?1")
    Optional<Review> getIfWrittenAboutLeaseeAndWrittenBy(long id);

    @Query("SELECT r FROM Review r WHERE r IN " +
            "(SELECT l.leaseeReview FROM Lease l WHERE l.owner.id = ?1)" +
            "OR r IN  " +
            "(SELECT l.itemReview FROM Lease l WHERE l.profile.id = ?1)" +
            "OR r IN " +
            "(SELECT l.ownerReview FROM Lease l WHERE l.profile.id = ?1)")
    Page<Review> getWhereWrittenByAuthor(long id, Pageable pageable);
}