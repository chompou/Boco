package boco.service.rental;

import boco.models.rental.CategoryType;
import boco.repository.rental.CategoryTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryTypeService {
    private final CategoryTypeRepository categoryTypeRepository;

    @Autowired
    public CategoryTypeService(CategoryTypeRepository categoryTypeRepository){
        this.categoryTypeRepository = categoryTypeRepository;
    }

    public List<CategoryType> getAll(){
        return categoryTypeRepository.findAll();
    }
}
