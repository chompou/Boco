package boco.service.rental;

import boco.repository.rental.LeaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaseService {
    private final LeaseRepository leaseRepository;

    @Autowired
    public LeaseService(LeaseRepository leaseRepository) {
        this.leaseRepository = leaseRepository;
    }
}
