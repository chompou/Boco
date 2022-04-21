package boco.service.rental;

import boco.repository.rental.CategoryTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryTypeService {
    @Autowired
    private CategoryTypeRepository categoryTypeRepository;
}
