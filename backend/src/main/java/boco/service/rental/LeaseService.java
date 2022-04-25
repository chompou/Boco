package boco.service.rental;

import boco.models.http.LeaseResponse;
import boco.models.http.NotificationResponse;
import boco.models.profile.Notification;
import boco.models.rental.Lease;
import boco.repository.rental.LeaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeaseService {
    private final LeaseRepository leaseRepository;

    @Autowired
    public LeaseService(LeaseRepository leaseRepository) {
        this.leaseRepository = leaseRepository;
    }

    public static List<LeaseResponse> convertLease(List<Lease> leases){
        List<LeaseResponse> leaseResponses = new ArrayList<>();
        for (Lease lease :
                leases) {
            leaseResponses.add(new LeaseResponse(lease));
        }
        return leaseResponses;
    }
}
