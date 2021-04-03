package uz.pdp.ware_house.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.ware_house.entity.User;
import uz.pdp.ware_house.payload.Result;
import uz.pdp.ware_house.payload.UserDto;
import uz.pdp.ware_house.service.UserService;

import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public List<User> getUserList(){
        return userService.getUserList();
    }

    @GetMapping("/{id}")
    public Result getOne(@PathVariable Integer id){
        return userService.getOne(id);
    }

    @PostMapping
    public Result add(@RequestBody UserDto userDto){
        return userService.add(userDto);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return userService.delete(id);
    }

    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id, @RequestBody UserDto userDto){
        return userService.edit(id,userDto);
    }
}
