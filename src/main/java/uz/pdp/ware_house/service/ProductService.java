package uz.pdp.ware_house.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.ware_house.entity.Attachment;
import uz.pdp.ware_house.entity.Category;
import uz.pdp.ware_house.entity.Measurement;
import uz.pdp.ware_house.entity.Product;
import uz.pdp.ware_house.payload.Deleted;
import uz.pdp.ware_house.payload.ProductDto;
import uz.pdp.ware_house.payload.Result;
import uz.pdp.ware_house.repository.AttachmentRepository;
import uz.pdp.ware_house.repository.CategoryRepository;
import uz.pdp.ware_house.repository.MeasurementRepository;
import uz.pdp.ware_house.repository.ProductRepository;

import java.util.*;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    MeasurementRepository measurementRepository;
    @Autowired
    AttachmentRepository attachmentRepository;


    //CREATE
    public Result add(ProductDto productDto){
        //CHECK SPECIAL NAME
        if (productDto.getName().startsWith(Deleted.DELETED))
            return new Result("Mahsulot nomi "+Deleted.DELETED+" bilan boshlanmasligi lozim",false);

        //CHECK UNIQUE NAME AND CATEGORY_ID
        boolean existsByNameAndCategoryId = productRepository.existsByNameAndCategoryId(productDto.getName(), productDto.getCategoryId());
        if (existsByNameAndCategoryId)
            return new Result("Ushbu mahsulot bu kategoriyada mavjud",false);

        //CHECK CATEGORY
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (!optionalCategory.isPresent())
            return new Result("Bunday kategoriya topilmadi",false);
        if (!optionalCategory.get().isActive())
            return new Result("Bu kategoriya uchirib yuborilgan",false);

        //CHECK MEASUREMENT
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(productDto.getMeasurementId());
        if (!optionalMeasurement.isPresent())
            return new Result("Bunday o'lchov birligi topilmadi",false);
        if (!optionalMeasurement.get().isActive())
            return new Result("Bu o'lchov birligi uchirib yuborilgan",false);

        //CHECK PHOTO
        Optional<Attachment> optionalPhoto = attachmentRepository.findById(productDto.getPhotoId());
        if (!optionalPhoto.isPresent())
            return new Result("Bunday surat topilmadi",false);

        //CREATE code
        Integer maxId = productRepository.getMaxId();
        if (maxId==null){
            maxId=1;
        }else {
            maxId+=1;
        }

        Product product=new Product();

        product.setName(productDto.getName());
        product.setCategory(optionalCategory.get());
        product.setAttachment(optionalPhoto.get());
        product.setMeasurement(optionalMeasurement.get());
        product.setCode(String.valueOf(maxId));
        productRepository.save(product);

        return new Result("Muvaffaqiyatli saqlandi",true);
    }


    //READ ALL
    public List<Product> getAll(){
        return productRepository.findAll();
    }


    //READ ONE
    public Result getOne(Integer id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent())
            return new Result("Mahsulot topilmadi",false);

        return new Result("Muvaffaqiyatli bajarildi",true,optionalProduct.get());
    }


    //DELETE
    public Result delete(Integer id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(!optionalProduct.isPresent())
            return new Result("Mahsulot topilmadi",false);
        Product product = optionalProduct.get();

        //HOW MANY TIMES DELETED
        long numberOfDeletedProduct = productRepository.countAllByNameStartingWithAndNameEndingWith(Deleted.DELETED, product.getName())+1;
        product.setActive(false);
        product.setName(Deleted.DELETED+numberOfDeletedProduct+":"+product.getName());

        productRepository.save(product);
        return new Result("Mahsulot uchirildi",true);
    }


    //PUT
    public Result edit(Integer id, ProductDto productDto){
        //CHECK PRODUCT
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent())
            return new Result("Mahsulot topilmadi",false);

       //CHECK CATEGORY
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (!optionalCategory.isPresent())
            return new Result("Bunday kategoriya topilmadi",false);
        if (!optionalCategory.get().isActive())
            return new Result("Bu kategoriya uchirib yuborilgan",false);

        //CHECK UNIQUE
        boolean existsByNameAndCategoryId = productRepository.existsByNameAndCategoryId(productDto.getName(),
                productDto.getCategoryId());
        if (existsByNameAndCategoryId)
            return new Result("Ushbu mahsulot bu kategoriyada mavjud",false);

        //CHECK MEASUREMENT
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(productDto.getMeasurementId());
        if (!optionalMeasurement.isPresent())
            return new Result("Bunday o'lchov birligi topilmadi",false);
        if (!optionalMeasurement.get().isActive())
            return new Result("Bu o'lchov birligi uchirib yuborilgan",false);

        //CHECK PHOTO
        Optional<Attachment> optionalPhoto = attachmentRepository.findById(productDto.getPhotoId());
        if (!optionalPhoto.isPresent())
            return new Result("Bunday surat topilmadi",false);

        Product editingProduct = optionalProduct.get();
        editingProduct.setName(productDto.getName());
        editingProduct.setCategory(optionalCategory.get());
        editingProduct.setMeasurement(optionalMeasurement.get());
        editingProduct.setAttachment(optionalPhoto.get());
        productRepository.save(editingProduct);
        return new Result("Muvaffaqiyatli o'zgartirildi",true);
    }
}
