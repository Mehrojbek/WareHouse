package uz.pdp.ware_house.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.ware_house.entity.*;
import uz.pdp.ware_house.payload.InputDto;
import uz.pdp.ware_house.payload.OutputDto;
import uz.pdp.ware_house.payload.Result;
import uz.pdp.ware_house.repository.ClientRepository;
import uz.pdp.ware_house.repository.CurrencyRepository;
import uz.pdp.ware_house.repository.OutputRepository;
import uz.pdp.ware_house.repository.WareHouseRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class OutputService {
    @Autowired
    OutputRepository outputRepository;
    @Autowired
    WareHouseRepository wareHouseRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    //CREATE
    public Result add(OutputDto outputDto){
        //CHECK WAREHOUSE
        Optional<WareHouse> optionalWareHouse = wareHouseRepository.findById(outputDto.getWareHouseId());
        if (!optionalWareHouse.isPresent())
            return new Result("Ombor topilmadi",false);
        if (!optionalWareHouse.get().isActive())
            return new Result("Ushbu ombor uchirilgan",false);

        //CHECK CLIENT
        Optional<Client> optionalClient = clientRepository.findById(outputDto.getCientId());
        if (!optionalClient.isPresent())
            return new Result("Yetkazib beruvchi topilmadi",false);
        if (!optionalClient.get().isActive())
            return new Result("Yetkazib beruvchi uchirib yuborilgan",false);

        //CHECK CURRENCY
        Optional<Currency> optionalCurrency = currencyRepository.findById(outputDto.getCurrencyId());
        if (!optionalCurrency.isPresent())
            return new Result("Valyuta turi topilmadi",false);
        if (!optionalCurrency.get().isActive())
            return new Result("Valyuta uchirib yuborilgan",false);

        Integer maxId = outputRepository.getMaxId();
        if (maxId==null){
            maxId=1;
        }else {
            maxId+=1;
        }

        Output output=new Output();
        output.setWareHouse(optionalWareHouse.get());
        output.setClient(optionalClient.get());
        output.setCurrency(optionalCurrency.get());
        output.setCode(maxId.toString());
        output.setFactureNumber(outputDto.getFactureNumber());
        output.setDate(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Asia/Tashkent"))));
        outputRepository.save(output);
        return new Result("Muvaffaqiyatli saqlandi",true);
    }


    //READ ALL
    public List<Output> getAll(){
        return outputRepository.findAll();
    }

    //READ ONE
    public Result getOne(Integer id){
        Optional<Output> optionalOutput = outputRepository.findById(id);
        if (!optionalOutput.isPresent())
            return new Result("Chiqim topilmadi",false);
        return new Result("Muvaffaqiyatli bajarildi",true,optionalOutput.get());
    }


    //DELETE
    public Result delete(Integer id){
        Optional<Output> optionalOutput = outputRepository.findById(id);
        if (!optionalOutput.isPresent())
            return new Result("Chiqim topilmadi",false);
        Output output = optionalOutput.get();
        output.setActive(false);
        outputRepository.save(output);
        return new Result("Chiqim uchirildi",true);
    }


    //PUT
    public Result edit(Integer id, OutputDto outputDto){
        Optional<Output> optionalOutput = outputRepository.findById(id);
        if (!optionalOutput.isPresent())
            return new Result("Kirim topilmadi",false);

        //CHECK WAREHOUSE
        Optional<WareHouse> optionalWareHouse = wareHouseRepository.findById(outputDto.getWareHouseId());
        if (!optionalWareHouse.isPresent())
            return new Result("Ombor topilmadi",false);
        if (!optionalWareHouse.get().isActive())
            return new Result("Ushbu ombor uchirilgan",false);

        //CHECK CLIENT
        Optional<Client> optionalClient = clientRepository.findById(outputDto.getCientId());
        if (!optionalClient.isPresent())
            return new Result("Yetkazib beruvchi topilmadi",false);
        if (!optionalClient.get().isActive())
            return new Result("Yetkazib beruvchi uchirib yuborilgan",false);

        //CHECK CURRENCY
        Optional<Currency> optionalCurrency = currencyRepository.findById(outputDto.getCurrencyId());
        if (!optionalCurrency.isPresent())
            return new Result("Valyuta turi topilmadi",false);
        if (!optionalCurrency.get().isActive())
            return new Result("Valyuta uchirib yuborilgan",false);

        Output editingOutput = optionalOutput.get();
        editingOutput.setClient(optionalClient.get());
        editingOutput.setCurrency(optionalCurrency.get());
        editingOutput.setWareHouse(optionalWareHouse.get());
        editingOutput.setFactureNumber(outputDto.getFactureNumber());

        outputRepository.save(editingOutput);
        return new Result("Chiqim muvaffaqiyatli o'zgartirildi",true);
    }
}
