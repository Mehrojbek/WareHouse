package uz.pdp.ware_house.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.ware_house.payload.ProductDto;
import uz.pdp.ware_house.payload.Result;
import uz.pdp.ware_house.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;


    @PostMapping
    public Result add(@RequestBody ProductDto productDto){
        return productService.add(productDto);
    }
}
