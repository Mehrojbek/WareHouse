package uz.pdp.ware_house.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.ware_house.entity.Output;
import uz.pdp.ware_house.entity.OutputProduct;
import uz.pdp.ware_house.entity.Product;
import uz.pdp.ware_house.payload.OutputProductDto;
import uz.pdp.ware_house.payload.Result;
import uz.pdp.ware_house.repository.OutputProductRepository;
import uz.pdp.ware_house.repository.OutputRepository;
import uz.pdp.ware_house.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OutputProductService {
    @Autowired
    OutputProductRepository outputProductRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OutputRepository outputRepository;

    //CREATE
    public Result add(OutputProductDto outputProductDto){
        //CHECK OUTPUT
        Optional<Output> optionalOutput = outputRepository.findById(outputProductDto.getOutputId());
        if (!optionalOutput.isPresent())
            return new Result("Chiqim topilmadi",false);
        if (!optionalOutput.get().isActive())
            return new Result("Chiqim avval uchirib yuborilgan",false);

        //CHECK PRODUCT
        Optional<Product> optionalProduct = productRepository.findById(outputProductDto.getProductId());
        if (!optionalProduct.isPresent())
            return new Result("Mahsulot topilmadi",false);
        if (!optionalProduct.get().isActive())
            return new Result("Mahsulot uchirib yuborilgan",false);

        OutputProduct outputProduct=new OutputProduct();
        outputProduct.setProduct(optionalProduct.get());
        outputProduct.setOutput(optionalOutput.get());
        outputProduct.setAmount(outputProductDto.getAmount());
        outputProduct.setPrice(outputProductDto.getPrice());
        outputProductRepository.save(outputProduct);
        return new Result("Muvaffaqiyatli saqlandi",true);
    }


    //READ ALL
    public List<OutputProduct> getAll(){
        return outputProductRepository.findAll();
    }


    //READ ONE
    public Result getOne(Integer id){
        Optional<OutputProduct> optionalOutputProduct = outputProductRepository.findById(id);
        if (!optionalOutputProduct.isPresent())
            return new Result("Chiqim mahsuloti topilmadi",false);
        return new Result("Muvaffaqiyatli bajarildi",true,optionalOutputProduct.get());
    }


    //DELETE
    public Result delete(Integer id){
        Optional<OutputProduct> optionalOutputProduct = outputProductRepository.findById(id);
        if (!optionalOutputProduct.isPresent())
            return new Result("Chiqim mahsuloti topilmadi",false);
        OutputProduct outputProduct = optionalOutputProduct.get();
        outputProduct.setActive(false);
        outputProductRepository.save(outputProduct);
        return new Result("Muvaffaqiyatli uchirildi",true);
    }


    //PUT
    public Result edit(Integer id, OutputProductDto outputProductDto){
        //CHECK IS_PRESENT
        Optional<OutputProduct> optionalOutputProduct = outputProductRepository.findById(id);
        if (!optionalOutputProduct.isPresent())
            return new Result("Chiqim mahsuloti topilmadi",false);

        //CHECK OUTPUT
        Optional<Output> optionalOutput = outputRepository.findById(outputProductDto.getOutputId());
        if (!optionalOutput.isPresent())
            return new Result("Chiqim topilmadi",false);
        if (!optionalOutput.get().isActive())
            return new Result("Chiqim avval uchirib yuborilgan",false);

        //CHECK PRODUCT
        Optional<Product> optionalProduct = productRepository.findById(outputProductDto.getProductId());
        if (!optionalProduct.isPresent())
            return new Result("Mahsulot topilmadi",false);
        if (!optionalProduct.get().isActive())
            return new Result("Mahsulot uchirib yuborilgan",false);


        OutputProduct editingOutputProduct = optionalOutputProduct.get();
        editingOutputProduct.setProduct(optionalProduct.get());
        editingOutputProduct.setOutput(optionalOutput.get());
        editingOutputProduct.setAmount(outputProductDto.getAmount());
        editingOutputProduct.setPrice(outputProductDto.getPrice());
        outputProductRepository.save(editingOutputProduct);
        return new Result("Muvaffaqiyatli o'zgartirildi",true);
    }
}
