package uz.pdp.ware_house.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.ware_house.entity.Currency;
import uz.pdp.ware_house.entity.Suplier;
import uz.pdp.ware_house.payload.Result;
import uz.pdp.ware_house.service.CurrencyService;

import java.util.List;

@RestController
@RequestMapping("/currency")
public class CurrencyController {
    @Autowired
    CurrencyService currencyService;

    @GetMapping
    public List<Currency> getAll(){
        return currencyService.getAll();
    }

    @GetMapping("/{id}")
    public Result getOne(@PathVariable Integer id){
        return currencyService.getOne(id);
    }

    @PostMapping
    public Result add(@RequestBody Currency currency){
        return currencyService.add(currency);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return currencyService.delete(id);
    }

    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id,@RequestBody Currency currency){
        return currencyService.edit(id,currency);
    }
}
