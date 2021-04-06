package uz.pdp.ware_house.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.ware_house.entity.OutputProduct;
import uz.pdp.ware_house.payload.OutputProductDto;
import uz.pdp.ware_house.payload.Result;
import uz.pdp.ware_house.service.OutputProductService;

import java.util.List;

@RestController
@RequestMapping("/outputProduct")
public class OutputProductController {
    @Autowired
    OutputProductService outputProductService;

    @GetMapping
    public List<OutputProduct> getAll(){
        return outputProductService.getAll();
    }

    @GetMapping("/{id}")
    public Result getOne(@PathVariable Integer id){
        return outputProductService.getOne(id);
    }

    @PostMapping
    public Result add(@RequestBody OutputProductDto outputProductDto){
        return outputProductService.add(outputProductDto);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return outputProductService.delete(id);
    }

    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id,@RequestBody OutputProductDto outputProductDto){
        return outputProductService.edit(id,outputProductDto);
    }
}
