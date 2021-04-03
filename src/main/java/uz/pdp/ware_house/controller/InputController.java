package uz.pdp.ware_house.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.ware_house.entity.Input;
import uz.pdp.ware_house.payload.InputDto;
import uz.pdp.ware_house.payload.Result;
import uz.pdp.ware_house.service.InputService;

import java.util.List;

@RestController
@RequestMapping("/input")
public class InputController {
    @Autowired
    InputService inputService;

    @GetMapping
    public List<Input> getAll(){
        return inputService.getAll();
    }

    @GetMapping("/{id}")
    public Result getOne(@PathVariable Integer id){
        return inputService.getOne(id);
    }

    @PostMapping
    public Result add(@RequestBody InputDto inputDto){
        return inputService.add(inputDto);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return inputService.delete(id);
    }

    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id,@RequestBody InputDto inputDto){
        return inputService.edit(id,inputDto);
    }
}
