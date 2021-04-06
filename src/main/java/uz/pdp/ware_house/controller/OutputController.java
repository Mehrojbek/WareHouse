package uz.pdp.ware_house.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.ware_house.entity.Input;
import uz.pdp.ware_house.entity.Output;
import uz.pdp.ware_house.payload.InputDto;
import uz.pdp.ware_house.payload.OutputDto;
import uz.pdp.ware_house.payload.Result;
import uz.pdp.ware_house.service.OutputService;

import java.util.List;

@RestController
@RequestMapping("/output")
public class OutputController {
    @Autowired
    OutputService outputService;

    @GetMapping
    public List<Output> getAll(){
        return outputService.getAll();
    }

    @GetMapping("/{id}")
    public Result getOne(@PathVariable Integer id){
        return outputService.getOne(id);
    }

    @PostMapping
    public Result add(@RequestBody OutputDto outputDto){
        return outputService.add(outputDto);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return outputService.delete(id);
    }

    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id,@RequestBody OutputDto outputDto){
        return outputService.edit(id,outputDto);
    }
}
