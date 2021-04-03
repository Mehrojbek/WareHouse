package uz.pdp.ware_house.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.ware_house.entity.InputProduct;
import uz.pdp.ware_house.payload.InputProductDto;
import uz.pdp.ware_house.payload.Result;
import uz.pdp.ware_house.service.InputProductService;

import java.util.List;

@RestController
@RequestMapping("/inputProduct")
public class InputProductController {
    @Autowired
    InputProductService inputProductService;

    @GetMapping
    public List<InputProduct> getAll(){
        return inputProductService.getAll();
    }

    @GetMapping("/{id}")
    public Result getOne(@PathVariable Integer id){
        return inputProductService.getOne(id);
    }

    @PostMapping
    public Result add(@RequestBody InputProductDto inputProductDto){
        return inputProductService.add(inputProductDto);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return inputProductService.delete(id);
    }

    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id,@RequestBody InputProductDto inputProductDto){
        return inputProductService.edit(id,inputProductDto);
    }
}
