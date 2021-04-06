package uz.pdp.ware_house.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.ware_house.entity.Category;
import uz.pdp.ware_house.payload.CategoryDto;
import uz.pdp.ware_house.payload.Deleted;
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
        //CHECK SPECIAL_NAME
        if (categoryDto.getName().startsWith(Deleted.DELETED))
            return new Result("Categoriya nomi "+Deleted.DELETED+" bilan boshlanmasligi lozim",false);

        //CHECK UNIQUE NAME
        boolean existsByName = categoryRepository.existsByName(categoryDto.getName());
        if (existsByName)
            return new Result("Ushbu kategoriya avval qo'shilgan",false);

        Category category=new Category();
        category.setName(categoryDto.getName());

        if (categoryDto.getParentCategoryId()!=null){
            Optional<Category> optionalCategory = categoryRepository.findById(categoryDto.getParentCategoryId());
            if (!optionalCategory.isPresent())
                return new Result("Bunday ota kategoriya topilmadi",false);



            //CHECK Parent category IS_ACTIVE
            if (!optionalCategory.get().isActive())
                return new Result("Bu ota kategoriya avval uchirilgan",false);

            Category parentCategory = optionalCategory.get();

            //CHECK UNIQUE category_id and category_name
            boolean existsByNameAndCategoryId = categoryRepository.existsByNameAndCategoryId(categoryDto.getName(), categoryDto.getParentCategoryId());
            if (existsByNameAndCategoryId)
                    return new Result("Ushbu ota kategoriyada joriy kategoriya allaqachon yaratilgan", false);

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

        //HOW MANY TIMES DELETED
        long numberOfDeletedCategory = categoryRepository.countAllByNameStartingWithAndNameEndingWith(Deleted.DELETED, category.getName()) + 1;

        category.setActive(false);
        category.setName(Deleted.DELETED+numberOfDeletedCategory+":"+category.getName());
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

        //CHECK CURRENT NAME EQUALS NEW NAME
        if (!categoryDto.getName().equals(editingCategory.getName())){
            //CHECK UNIQUE NAME
            boolean existsByName = categoryRepository.existsByName(categoryDto.getName());
            if (existsByName)
                return new Result("Bu nomdagi categoriya allaqachon mavjud",false);
        }


        //CHECK EDIT CATEGORY
        if (!categoryDto.getParentCategoryId().equals(editingCategory.getCategory().getId())) {
            //CHECK HAVE PARENT CATEGORY
            if (categoryDto.getParentCategoryId() != null) {
                Optional<Category> optionalParentCategory = categoryRepository.findById(categoryDto.getParentCategoryId());

                //CHECK PARENT CATEGORY
                if (!optionalParentCategory.isPresent())
                    return new Result("Ota kategoriya topilmadi", false);
                Category parentCategory = optionalParentCategory.get();

                //CHECK UNIQUE category_id AND name
                boolean existsByNameAndCategoryId = categoryRepository.existsByNameAndCategoryId(categoryDto.getName(),
                        categoryDto.getParentCategoryId());
                if (existsByNameAndCategoryId)
                    return new Result("Ushbu ota kategoriyada joriy kategoriya mavjud", false);
                editingCategory.setCategory(parentCategory);
            }
        }

        categoryRepository.save(editingCategory);
        return new Result("Muvaffaqiyatli o'zgartirildi",true);
    }
}
