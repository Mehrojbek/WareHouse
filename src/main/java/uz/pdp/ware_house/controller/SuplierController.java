package uz.pdp.ware_house.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.ware_house.entity.Suplier;
import uz.pdp.ware_house.payload.Result;
import uz.pdp.ware_house.service.SuplierService;

import java.util.*;

@RestController
@RequestMapping("/suplier")
public class SuplierController {

    @Autowired
    SuplierService suplierService;

    @GetMapping
    public List<Suplier> getAll(){
        return suplierService.getAll();
    }

    @GetMapping("/{id}")
    public Result getOne(@PathVariable Integer id){
        return suplierService.getOne(id);
    }

    @PostMapping
    public Result add(@RequestBody Suplier suplier){
        return suplierService.add(suplier);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return suplierService.delete(id);
    }

    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id,@RequestBody Suplier suplier){
        return suplierService.edit(id,suplier);
    }

}
