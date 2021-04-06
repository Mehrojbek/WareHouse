package uz.pdp.ware_house.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.ware_house.entity.WareHouse;
import uz.pdp.ware_house.payload.Deleted;
import uz.pdp.ware_house.payload.Result;
import uz.pdp.ware_house.repository.WareHouseRepository;

import java.util.*;

@Service
public class WareHouseService {
    @Autowired
    WareHouseRepository wareHouseRepository;


    //CREATE
    public Result add(WareHouse wareHouse){
        //CHECK SPECIAL NAME
        if (wareHouse.getName().startsWith(Deleted.DELETED))
            return new Result("Ombor nomi "+Deleted.DELETED+" bilan boshlanmasligi lozim",false);

        //CHECK ALLREADY EXIST
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

        //HOW MANY TIMES DELETED
        long numberOfDeletedWareHouse = wareHouseRepository.countAllByNameStartingWithAndNameEndingWith(Deleted.DELETED, wareHouse.getName())+1;
        wareHouse.setActive(false);
        wareHouse.setName(Deleted.DELETED+numberOfDeletedWareHouse+":"+wareHouse.getName());

        wareHouseRepository.save(wareHouse);
        return new Result("Ombor uchirildi",true);
    }


    //PUT
    public Result edit(Integer id, WareHouse wareHouse){
        Optional<WareHouse> optionalWareHouse = wareHouseRepository.findById(id);
        if (!optionalWareHouse.isPresent())
            return new Result("Ombor topilmadi",false);

        WareHouse editingWareHouse = optionalWareHouse.get();
        //CHECK NEW NAME EQUALS CURRENT NAME
         if (!editingWareHouse.getName().equals(wareHouse.getName())) {
             //CHECK NEW NAME ALLREADY EXIST
             boolean existsByName = wareHouseRepository.existsByName(wareHouse.getName());
             if (existsByName)
             return new Result("Ushbu ombor avval kiritilgan", false);
         }

        editingWareHouse.setName(wareHouse.getName());
        wareHouseRepository.save(editingWareHouse);
        return new Result("Ombor muvaffaqiyatli o'zhartirildi",true);
    }


}
