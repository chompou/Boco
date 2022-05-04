package boco.service.profile;

import boco.repository.profile.ProfessionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfessionalService {
    private final ProfessionalRepository professionalRepository;

    @Autowired

    public ProfessionalService(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }
}
