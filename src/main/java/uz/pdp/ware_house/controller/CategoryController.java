package uz.pdp.ware_house.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
;
import uz.pdp.ware_house.entity.Category;
import uz.pdp.ware_house.payload.CategoryDto;
import uz.pdp.ware_house.payload.Result;
import uz.pdp.ware_house.service.CategoryService;

import java.util.*;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;



    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Result getCategory(@PathVariable Integer id) {
        return categoryService.getOneCategory(id);
    }


    @PostMapping
    public Result addCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.addCategory(categoryDto);
    }

    @DeleteMapping("/{id}")
    public Result deleteCategory(@PathVariable Integer id){
        return categoryService.deleteCatogory(id);
    }

    @PutMapping("/{id}")
    public Result editCategory(@PathVariable Integer id, @RequestBody CategoryDto categoryDto){
        return categoryService.editCategory(id,categoryDto);
    }

}
