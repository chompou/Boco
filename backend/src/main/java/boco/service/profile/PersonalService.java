package boco.service.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalService {
    private final PersonalService personalService;

    @Autowired

    public PersonalService(PersonalService personalService) {
        this.personalService = personalService;
    }
}
