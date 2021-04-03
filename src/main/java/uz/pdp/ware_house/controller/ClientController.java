package uz.pdp.ware_house.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.ware_house.entity.Client;
import uz.pdp.ware_house.payload.Result;
import uz.pdp.ware_house.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    ClientService clientService;

    @GetMapping
    public List<Client> getAll(){
        return clientService.getAll();
    }

    @GetMapping("/{id}")
    public Result getOne(@PathVariable Integer id){
        return clientService.getOne(id);
    }

    @PostMapping
    public Result add(@RequestBody Client client){
        return clientService.add(client);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return clientService.delete(id);
    }

    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id,@RequestBody Client client){
        return clientService.edit(id,client);
    }
}
