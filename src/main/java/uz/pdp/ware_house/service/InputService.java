package uz.pdp.ware_house.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.ware_house.entity.Currency;
import uz.pdp.ware_house.entity.Input;
import uz.pdp.ware_house.entity.Suplier;
import uz.pdp.ware_house.entity.WareHouse;
import uz.pdp.ware_house.payload.InputDto;
import uz.pdp.ware_house.payload.Result;
import uz.pdp.ware_house.repository.CurrencyRepository;
import uz.pdp.ware_house.repository.InputRepository;
import uz.pdp.ware_house.repository.SuplierRepository;
import uz.pdp.ware_house.repository.WareHouseRepository;

import java.awt.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class InputService {
    @Autowired
    InputRepository inputRepository;
    @Autowired
    WareHouseRepository wareHouseRepository;
    @Autowired
    SuplierRepository suplierRepository;
    @Autowired
    CurrencyRepository currencyRepository;

    //CREATE
    public Result add(InputDto inputDto) {
        //CHECK WARE HOUSE
        Optional<WareHouse> optionalWareHouse = wareHouseRepository.findById(inputDto.getWareHouseId());
        if (!optionalWareHouse.isPresent())
            return new Result("Ombor topilmadi",false);
        if (!optionalWareHouse.get().isActive())
            return new Result("Ushbu ombor uchirilgan",false);

        //CHECK SUPLIER
        Optional<Suplier> optionalSuplier = suplierRepository.findById(inputDto.getSuplierId());
        if (!optionalSuplier.isPresent())
            return new Result("Yetkazib beruvchi topilmadi",false);
        if (!optionalSuplier.get().isActive())
            return new Result("Yetkazib beruvchi uchirib yuborilgan",false);

        //CHECK CURRENCY
        Optional<Currency> optionalCurrency = currencyRepository.findById(inputDto.getCurrencyId());
        if (!optionalCurrency.isPresent())
            return new Result("Valyuta turi topilmadi",false);
        if (!optionalCurrency.get().isActive())
            return new Result("Valyuta uchirib yuborilgan",false);

        Integer maxId = inputRepository.getMaxId();
        if (maxId==null){
            maxId=1;
        }else {
            maxId+=1;
        }

        Input input = new Input();
        input.setDate(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Asia/Tashkent"))));
        input.setFactureNumber(inputDto.getFactureNumber());
        input.setCode(maxId.toString());

        input.setCurrency(optionalCurrency.get());
        input.setSuplier(optionalSuplier.get());
        input.setWareHouse(optionalWareHouse.get());
        inputRepository.save(input);
        return new Result("Kirim muvaffaqiyatli saqlandi",true);
    }


    //READ ALL
    public List<Input> getAll(){
        return inputRepository.findAll();
    }


    //READ ONE
    public Result getOne(Integer id){
        Optional<Input> optionalInput = inputRepository.findById(id);
        if (!optionalInput.isPresent())
            return new Result("Ushbu Kirim topilmadi",false);
        return new Result("Muvaffaqiyatli topildi",true,optionalInput.get());
    }


    //DELETE
    public Result delete(Integer id){
        Optional<Input> optionalInput = inputRepository.findById(id);
        if (!optionalInput.isPresent())
            return new Result("Kirim topilmadi",false);
        Input input = optionalInput.get();
        input.setActive(false);
        inputRepository.save(input);
        return new Result("Kirim muvaffaqiyatli uchirildi",true);
    }


    //PUT
    public Result edit(Integer id, InputDto inputDto){
        Optional<Input> optionalInput = inputRepository.findById(id);
        if (!optionalInput.isPresent())
            return new Result("Kirim topilmadi",false);

        //CHECK WARE HOUSE
        Optional<WareHouse> optionalWareHouse = wareHouseRepository.findById(inputDto.getWareHouseId());
        if (!optionalWareHouse.isPresent())
            return new Result("Ombor topilmadi",false);
        if (!optionalWareHouse.get().isActive())
            return new Result("Ushbu ombor uchirilgan",false);

        //CHECK SUPLIER
        Optional<Suplier> optionalSuplier = suplierRepository.findById(inputDto.getSuplierId());
        if (!optionalSuplier.isPresent())
            return new Result("Yetkazib beruvchi topilmadi",false);
        if (!optionalSuplier.get().isActive())
            return new Result("Yetkazib beruvchi uchirib yuborilgan",false);

        //CHECK CURRENCY
        Optional<Currency> optionalCurrency = currencyRepository.findById(inputDto.getCurrencyId());
        if (!optionalCurrency.isPresent())
            return new Result("Valyuta turi topilmadi",false);
        if (!optionalCurrency.get().isActive())
            return new Result("Valyuta uchirib yuborilgan",false);

        Input editingInput = optionalInput.get();
        editingInput.setSuplier(optionalSuplier.get());
        editingInput.setCurrency(optionalCurrency.get());
        editingInput.setWareHouse(optionalWareHouse.get());
        editingInput.setFactureNumber(inputDto.getFactureNumber());

        inputRepository.save(editingInput);
        return new Result("Kirim muvaffaqiyatli o'zgartirildi",true);
    }
}
