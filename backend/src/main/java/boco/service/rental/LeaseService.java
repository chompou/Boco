package boco.service.rental;

import boco.repository.rental.LeaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaseService {
    @Autowired
    private LeaseRepository leaseRepository;
}
