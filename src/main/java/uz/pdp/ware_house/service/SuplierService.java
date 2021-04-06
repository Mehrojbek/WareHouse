package uz.pdp.ware_house.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.ware_house.entity.Suplier;
import uz.pdp.ware_house.payload.Deleted;
import uz.pdp.ware_house.payload.Result;
import uz.pdp.ware_house.repository.SuplierRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SuplierService {
    @Autowired
    SuplierRepository suplierRepository;


    //CREATE
    public Result add(Suplier suplier){
        //CHECK SPECIAL NAME
        if (suplier.getPhoneNumber().startsWith(Deleted.DELETED))
            return new Result("Telefon raqami "+Deleted.DELETED+" bilan boshlanishi mumkin emas",false);

        //CHECK UNIQUE PHONE NUMBER
        boolean existsByPhoneNumber = suplierRepository.existsByPhoneNumber(suplier.getPhoneNumber());
        if (existsByPhoneNumber)
            return new Result("Bu telefon raqami avval qo'shilgan",false);
        suplierRepository.save(suplier);
        return new Result("Yetkazib beruvchi muvaffaqiyatli qo'shildi",true);
    }


    //READ ALL
    public List<Suplier> getAll(){
        return suplierRepository.findAll();
    }


    //READ ONE
    public Result getOne(Integer id){
        Optional<Suplier> optionalSuplier = suplierRepository.findById(id);
        if (!optionalSuplier.isPresent())
            return new Result("Yetkazib beruvchi topilmadi",false);
        return new Result("muvaffaqiyatli bajarildi",true,optionalSuplier.get());
    }


    //DELETE
    public Result delete(Integer id){
        Optional<Suplier> optionalSuplier = suplierRepository.findById(id);
        if (!optionalSuplier.isPresent())
            return new Result("Yetkazib beruvchi topilmadi",false);
        Suplier suplier = optionalSuplier.get();

        //HOW MANY TIMES DELETED
        long numberOfDeletedSuplier = suplierRepository.countAllByPhoneNumberStartingWithAndPhoneNumberEndingWith(Deleted.DELETED, suplier.getPhoneNumber())+1;
        suplier.setActive(false);
        suplier.setPhoneNumber(Deleted.DELETED+numberOfDeletedSuplier+":"+suplier.getPhoneNumber());

        suplierRepository.save(suplier);
        return new Result("Muvaffaqiyatli uchirildi",true);
    }


    //PUT
    public Result edit(Integer id, Suplier suplier){
        Optional<Suplier> optionalSuplier = suplierRepository.findById(id);
        if (!optionalSuplier.isPresent())
            return new Result("Yetkazib beruvchi topilmadi",false);
        Suplier editingSuplier = optionalSuplier.get();
        editingSuplier.setName(suplier.getName());

        //CHECK UNIQUE PHONE NUMBER
        if (!editingSuplier.getPhoneNumber().equals(suplier.getPhoneNumber())){
            boolean existsByPhoneNumber = suplierRepository.existsByPhoneNumber(suplier.getPhoneNumber());
            if (existsByPhoneNumber)
                return new Result("Bu telefon raqami avval qo'shilgan",false);
            editingSuplier.setPhoneNumber(suplier.getPhoneNumber());
        }
        suplierRepository.save(editingSuplier);
        return new Result("Yetkazib beruvchi muvaffaqiyatli o'zgartirildi",true);
    }

}
