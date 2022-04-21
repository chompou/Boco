package boco.service.rental;

import boco.repository.rental.CategoryTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryTypeService {
    private final CategoryTypeRepository categoryTypeRepository;

    @Autowired
    public CategoryTypeService(CategoryTypeRepository categoryTypeRepository){
        this.categoryTypeRepository = categoryTypeRepository;
    }
}
