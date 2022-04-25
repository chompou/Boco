package boco.repository.profile;

import boco.models.profile.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository <Profile, Long> {
    Optional<Profile> findProfileByUsernameOrEmail(String username, String email);
    Optional<Profile> findProfileByUsername(String username);
    Optional<Profile> findProfileByEmail(String email);

    @Query("SELECT p FROM Profile p WHERE EXISTS " +
            "(SELECT li FROM Listing li WHERE li.profile.id = ?1 AND EXISTS" +
            "(SELECT le FROM Lease le WHERE le.listing.id = li.id AND le.profile.id = p.id))")
    List<Profile> getAllByContactToOwner(long ownerId);

    @Query("SELECT p FROM Profile p WHERE EXISTS " +
            "(SELECT li FROM Listing li WHERE li.profile.id = p.id AND EXISTS" +
            "(SELECT le FROM Lease le WHERE le.listing.id = li.id AND le.profile.id = ?1))")
    List<Profile> getAllByContactToRenter(long renterId);

    @Query("SELECT p FROM Profile p WHERE p IN" +
            "(SELECT p1 FROM Profile p1 WHERE EXISTS " +
            "(SELECT li FROM Listing li WHERE li.profile.id = p1.id AND EXISTS" +
            "(SELECT le FROM Lease le WHERE le.listing.id = li.id AND le.profile.id = ?1)))" +
            "OR p IN " +
            "(SELECT p1 FROM Profile p1 WHERE EXISTS " +
            "(SELECT li FROM Listing li WHERE li.profile.id = ?1 AND EXISTS" +
            "(SELECT le FROM Lease le WHERE le.listing.id = li.id AND le.profile.id = p1.id)))")
    List<Profile> getAllByContact(long id);

    @Query("SELECT p FROM Profile p WHERE p.id = ?2 AND p IN" +
            "(SELECT p1 FROM Profile p1 WHERE EXISTS " +
            "(SELECT li FROM Listing li WHERE li.profile.id = p1.id AND EXISTS" +
            "(SELECT le FROM Lease le WHERE le.listing.id = li.id AND le.profile.id = ?1)))" +
            "OR p IN " +
            "(SELECT p1 FROM Profile p1 WHERE EXISTS " +
            "(SELECT li FROM Listing li WHERE li.profile.id = ?1 AND EXISTS" +
            "(SELECT le FROM Lease le WHERE le.listing.id = li.id AND le.profile.id = p1.id)))")
    Optional<Profile> getIfContact(long id1, long id2);

    @Query("SELECT p FROM Profile p WHERE EXISTS " +
            "(SELECT li FROM Listing li WHERE li.profile.id = ?1 AND EXISTS" +
            "(SELECT le FROM Lease le WHERE le.listing.id = li.id AND le.profile.id = p.id))")
    Page<Profile> getAllByContactToOwner(long ownerId, Pageable page);

    @Query("SELECT p FROM Profile p WHERE EXISTS " +
            "(SELECT li FROM Listing li WHERE li.profile.id = p.id AND EXISTS" +
            "(SELECT le FROM Lease le WHERE le.listing.id = li.id AND le.profile.id = ?1))")
    Page<Profile> getAllByContactToRenter(long renterId, Pageable page);

    @Query("SELECT p FROM Profile p WHERE p IN" +
            "(SELECT p1 FROM Profile p1 WHERE EXISTS " +
            "(SELECT li FROM Listing li WHERE li.profile.id = p1.id AND EXISTS" +
            "(SELECT le FROM Lease le WHERE le.listing.id = li.id AND le.profile.id = ?1)))" +
            "OR p IN " +
            "(SELECT p1 FROM Profile p1 WHERE EXISTS " +
            "(SELECT li FROM Listing li WHERE li.profile.id = ?1 AND EXISTS" +
            "(SELECT le FROM Lease le WHERE le.listing.id = li.id AND le.profile.id = p1.id)))")
    Page<Profile> getAllByContact(long id, Pageable page);


}