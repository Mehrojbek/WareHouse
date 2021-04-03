package uz.pdp.ware_house.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.ware_house.entity.WareHouse;
import uz.pdp.ware_house.payload.Result;
import uz.pdp.ware_house.repository.WareHouseRepository;

import java.util.*;

@Service
public class WareHouseService {
    @Autowired
    WareHouseRepository wareHouseRepository;


    //CREATE
    public Result add(WareHouse wareHouse){
        boolean existsByName = wareHouseRepository.existsByName(wareHouse.getName());
        if (existsByName)
            return new Result("Ushbu ombor alaqachon qo'shilgan",false);
        wareHouseRepository.save(wareHouse);
        return new Result("Ombor muvaffaqiyatli qo'shildi",true);
    }


    //READ ALL
    public List<WareHouse> getWareHouseList(){
        return wareHouseRepository.findAll();
    }


    //READ ONE
    public Result getOne(Integer id){
        Optional<WareHouse> optionalWareHouse = wareHouseRepository.findById(id);
        if (!optionalWareHouse.isPresent())
            return new Result("Ombor topilmadi",false);
        return new Result("Ombor topildi",true,optionalWareHouse.get());
    }


    //DELETE
    public Result delete(Integer id){
        Optional<WareHouse> optionalWareHouse = wareHouseRepository.findById(id);
        if (!optionalWareHouse.isPresent())
            return new Result("Ombor topilmadi",false);
        WareHouse wareHouse = optionalWareHouse.get();
        wareHouse.setActive(false);
        return new Result("Ombor uchirildi",true);
    }


    //PUT
    public Result edit(Integer id, WareHouse wareHouse){
        Optional<WareHouse> optionalWareHouse = wareHouseRepository.findById(id);
        if (!optionalWareHouse.isPresent())
            return new Result("Ombor topilmadi",false);
        WareHouse editingWareHouse = optionalWareHouse.get();
         if (editingWareHouse.getName().equals(wareHouse.getName()))
             return new Result("Ombor muvaffaqiyatli o'zgartirildi",true);

        boolean existsByName = wareHouseRepository.existsByName(wareHouse.getName());
        if (existsByName)
            return new Result("Ushbu ombor avval kiritilgan",false);
        editingWareHouse.setName(wareHouse.getName());
        wareHouseRepository.save(editingWareHouse);
        return new Result("Ombor muvaffaqiyatli o'zhartirildi",true);
    }


}
