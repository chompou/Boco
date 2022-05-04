package boco.service.rental;

import boco.model.rental.CategoryType;
import boco.repository.rental.CategoryTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class CategoryTypeTest {

    @InjectMocks
    private CategoryTypeService categoryTypeService;

    @Mock
    private CategoryTypeRepository categoryTypeRepository;

    @BeforeEach
    public void setUp(){
        CategoryType categoryType = new CategoryType(1L, "Car");
        CategoryType categoryType1 = new CategoryType(2L, "Sport");
        CategoryType categoryType2 = new CategoryType(3L, "Tool");
        List<CategoryType> categoryTypes = new ArrayList<>(Arrays.asList(categoryType, categoryType1, categoryType2));
        lenient().when(categoryTypeRepository.findAll()).thenReturn(categoryTypes);
    }

    @Test
    public void testGetAllCategories(){
        List<CategoryType> categoryTypes = categoryTypeService.getAll();
        Assertions.assertEquals(categoryTypes.size(), 3);
        Assertions.assertEquals(categoryTypes.get(0).getId(), 1L);
        Assertions.assertEquals(categoryTypes.get(0).getName(), "Car");
        Assertions.assertEquals(categoryTypes.get(1).getId(), 2L);
        Assertions.assertEquals(categoryTypes.get(1).getName(), "Sport");
        Assertions.assertEquals(categoryTypes.get(2).getId(), 3L);
        Assertions.assertEquals(categoryTypes.get(2).getName(), "Tool");
    }

}
