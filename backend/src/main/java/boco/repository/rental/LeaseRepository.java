package boco.repository.rental;

import boco.models.profile.Profile;
import boco.models.rental.Lease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaseRepository extends JpaRepository<Lease, Long> {

    List<Lease> getLeasesByProfile_Id(Long profileId);
    List<Lease> getLeasesByOwner(Profile owner);
    List<Lease> getLeasesByProfile(Profile profile);
    List<Lease> getLeasesByListing_Id(Long listingId);
    List<Lease> getLeasesByIsApprovedIsTrueAndIsCompletedIsTrue();
}
