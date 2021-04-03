package uz.pdp.ware_house.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.ware_house.entity.WareHouse;
import uz.pdp.ware_house.payload.Result;
import uz.pdp.ware_house.service.WareHouseService;

import java.util.*;

@RestController
@RequestMapping("/wareHouse")
public class WareHouseController {
    @Autowired
    WareHouseService wareHouseService;

    @GetMapping
    public List<WareHouse> getWareHouseList(){
        return wareHouseService.getWareHouseList();
    }

    @GetMapping("/{id}")
    public Result getWareHouseList(@PathVariable Integer id){
        return wareHouseService.getOne(id);
    }

    @PostMapping
    public Result add(@RequestBody WareHouse wareHouse){
        return wareHouseService.add(wareHouse);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return wareHouseService.delete(id);
    }

    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id, @RequestBody WareHouse wareHouse){
        return wareHouseService.edit(id,wareHouse);
    }

}
