package boco.controller.model;

import boco.model.rental.CategoryType;
import boco.service.rental.CategoryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    CategoryTypeService categoryTypeService;


    @Autowired
    public CategoryController(CategoryTypeService categoryTypeService){
        this.categoryTypeService = categoryTypeService;
    }

    @GetMapping("")
    public ResponseEntity<List<CategoryType>> getCategories(){
        return ResponseEntity.ok(categoryTypeService.getAll());
    }
}
