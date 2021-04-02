package uz.pdp.ware_house.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.ware_house.entity.Category;
import uz.pdp.ware_house.payload.CategoryDto;
import uz.pdp.ware_house.payload.Result;
import uz.pdp.ware_house.repository.CategoryRepository;

import java.util.*;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    //CREATE
    public Result addCategory(CategoryDto categoryDto){
        Category category=new Category();
        category.setName(categoryDto.getName());
        if (categoryDto.getParentCategoryId()!=null){
            Optional<Category> optionalCategory = categoryRepository.findById(categoryDto.getParentCategoryId());
            if (!optionalCategory.isPresent())
                return new Result("Bunday ota kategoriya topilmadi",false);

            Category parentCategory = optionalCategory.get();

            //CHECK Parent category IS_ACTIVE
            if (!parentCategory.isActive())
                return new Result("Bu ota kategoriya avval uchirilgan",false);

            //CHECK UNIQUE category_id and category_name
            boolean existsByNameAndCategoryId = categoryRepository.existsByNameAndCategoryId(categoryDto.getName(),
                    categoryDto.getParentCategoryId());
            if (existsByNameAndCategoryId)
                return new Result("Ushbu ota kategoriyada joriy kategoriya allaqachon yaratilgan",false);

            category.setCategory(parentCategory);
        }
        categoryRepository.save(category);
        return new Result("Muvaffaqiyatli saqlandi",true);
    }


    //READ
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }


    //READ ONE
    public Result getOneCategory(Integer id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        return optionalCategory.isPresent()? new Result("Bunday kategoriya topilmadi",false,null):
                new Result("Muvaffaqiyatli topildi",true,optionalCategory.get());
    }


    //DELETE
    public Result deleteCatogory(Integer id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent())
            return new Result("Ushbu kategoriya topilmadi",false);
        Category category = optionalCategory.get();
        category.setActive(false);
        categoryRepository.save(category);
        return new Result("Kategoriya muvaffaqiyatli uchirildi",true);
    }


    //PUT
    public Result editCategory(Integer id, CategoryDto categoryDto){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent())
            return new Result("Kategoriya topilmadi",false);

        Category editingCategory = optionalCategory.get();
        editingCategory.setName(categoryDto.getName());
        editingCategory.setActive(categoryDto.isActive());

        //CHECK HAVE PARENT CATEGORY
        if (categoryDto.getParentCategoryId()!=null) {
            Optional<Category> optionalParentCategory = categoryRepository.findById(categoryDto.getParentCategoryId());

        //CHECK PARENT CATEGORY
            if (!optionalParentCategory.isPresent())
                return new Result("Ota kategoriya topilmadi",false);
            Category parentCategory = optionalParentCategory.get();

        //CHECK CURRENT NAME EQUALS NEW NAME
            if (!categoryDto.getName().equals(editingCategory.getName())){
        //CHECK UNIQUE category_id AND name
                boolean existsByNameAndCategoryId = categoryRepository.existsByNameAndCategoryId(categoryDto.getName(),
                        categoryDto.getParentCategoryId());
                if (existsByNameAndCategoryId)
                    return new Result("Ushbu ota kategoriyada joriy kategoriya mavjud",false);
                editingCategory.setCategory(parentCategory);
            }
        }

        categoryRepository.save(editingCategory);
        return new Result("Muvaffaqiyatli o'zgartirildi",true);
    }
}
