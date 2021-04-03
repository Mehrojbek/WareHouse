package uz.pdp.ware_house.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.ware_house.entity.Input;
import uz.pdp.ware_house.entity.InputProduct;
import uz.pdp.ware_house.entity.Product;
import uz.pdp.ware_house.payload.InputProductDto;
import uz.pdp.ware_house.payload.Result;
import uz.pdp.ware_house.repository.InputRepository;
import uz.pdp.ware_house.repository.InputProductRepository;
import uz.pdp.ware_house.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InputProductService {

    @Autowired
    InputProductRepository inputProductRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    InputRepository inputRepository;

    //CREATE
    public Result add(InputProductDto inputProductDto){
        //CHECK PRODUCT
        Optional<Product> optionalProduct = productRepository.findById(inputProductDto.getProductId());
        if (!optionalProduct.isPresent())
            return new Result("Mahsulot topilmadi",false);
        if (!optionalProduct.get().isActive())
            return new Result("Mahsulot uchirib yuborilgan",false);

        //CHECK INPUT
        Optional<Input> optionalInput = inputRepository.findById(inputProductDto.getInputId());
        if (!optionalInput.isPresent())
            return new Result("Kirim topilmadi",false);
        if (!optionalInput.get().isActive())
            return new Result("Kirim uchirib yuborilgan",false);

        InputProduct inputProduct=new InputProduct();
        inputProduct.setProduct(optionalProduct.get());
        inputProduct.setInput(optionalInput.get());

        inputProduct.setAmount(inputProductDto.getAmount());
        inputProduct.setPrice(inputProductDto.getPrice());
        inputProduct.setExpireDate(inputProductDto.getExpireDate());

        inputProductRepository.save(inputProduct);
        return new Result("Kirim mahsuloti saqlandi",true);
    }


    //READ ALL
    public List<InputProduct> getAll(){
        return inputProductRepository.findAll();
    }


    //READ ONE
    public Result getOne(Integer id){
        Optional<InputProduct> optionalInputProduct = inputProductRepository.findById(id);
        if (!optionalInputProduct.isPresent())
            return new Result("Kirim mahsuloti topilmadi",false);
        return new Result("Muvaffaqiyatli bajarildi",true,optionalInputProduct.get());
    }


    //DELETE
    public Result delete(Integer id){
        Optional<InputProduct> optionalInputProduct = inputProductRepository.findById(id);
        if (!optionalInputProduct.isPresent())
            return new Result("Kirim mahsuloti topilmadi",false);
        InputProduct inputProduct = optionalInputProduct.get();
        inputProduct.setActive(false);
        inputProductRepository.save(inputProduct);
        return new Result("Kirim mahsuloti muvaffaqiyatli uchirildi",true);
    }


    //PUT
    public Result edit(Integer id, InputProductDto inputProductDto){
        Optional<InputProduct> optionalInputProduct = inputProductRepository.findById(id);
        if (!optionalInputProduct.isPresent())
            return new Result("Kirim Mahsuloti topilmadi",false);
        InputProduct editingInputProduct = optionalInputProduct.get();

        //CHECK PRODUCT
        Optional<Product> optionalProduct = productRepository.findById(inputProductDto.getProductId());
        if (!optionalProduct.isPresent())
            return new Result("Mahsulot topilmadi",false);
        if (!optionalProduct.get().isActive())
            return new Result("Mahsulot uchirib yuborilgan",false);

        //CHECK INPUT
        Optional<Input> optionalInput = inputRepository.findById(inputProductDto.getInputId());
        if (!optionalInput.isPresent())
            return new Result("Kirim topilmadi",false);
        if (!optionalInput.get().isActive())
            return new Result("Kirim uchirib yuborilgan",false);

        editingInputProduct.setProduct(optionalProduct.get());
        editingInputProduct.setInput(optionalInput.get());
        editingInputProduct.setPrice(inputProductDto.getPrice());
        editingInputProduct.setAmount(inputProductDto.getAmount());
        editingInputProduct.setExpireDate(inputProductDto.getExpireDate());
        inputProductRepository.save(editingInputProduct);
        return new Result("Kirim mahsuloti muvaffaqiyatli o'zgartirildi",true);
    }

}
