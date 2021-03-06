package uz.pdp.ware_house.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.ware_house.entity.Currency;
import uz.pdp.ware_house.payload.Deleted;
import uz.pdp.ware_house.payload.Result;
import uz.pdp.ware_house.repository.CurrencyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {

    @Autowired
    CurrencyRepository currencyRepository;

    //CREATE
    public Result add(Currency currency) {
        //CHECK UNIQUE NAME
        boolean existsByName = currencyRepository.existsByName(currency.getName());
        if (existsByName) {
            Currency byName = currencyRepository.findAllByName(currency.getName());
            if (byName.isActive())
            return new Result("Bu valyuta avval kiritilgan", false);
            byName.setActive(true);
            currencyRepository.save(byName);
            return new Result("Valyuta mavaffaqiyatli saqlandi", true);
        }
        currencyRepository.save(currency);
        return new Result("Valyuta mavaffaqiyatli saqlandi", true);
    }

    //READ ALL
    public List<Currency> getAll() {
        return currencyRepository.findAll();
    }


    //READ ONE
    public Result getOne(Integer id) {
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        if (!optionalCurrency.isPresent())
            return new Result("Valyuta topilmadi", false);
        return new Result("Muvaffaqiyatli bajarildi", true, optionalCurrency.get());
    }


    //DELETE
    public Result delete(Integer id) {
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        if (!optionalCurrency.isPresent())
            return new Result("Valyuta topilmadi", false);

        Currency currency = optionalCurrency.get();
        currency.setActive(false);

        currencyRepository.save(currency);
        return new Result("Valyuta muvaffaqiyatli uchirildi", true);
    }


    //PUT
    public Result edit(Integer id, Currency currency) {
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        if (!optionalCurrency.isPresent())
            return new Result("Valyuta topilmadi", false);

        Currency editingCurrency = optionalCurrency.get();

        if (!editingCurrency.getName().equals(currency.getName())) {
            boolean existsByName = currencyRepository.existsByName(currency.getName());
            if (existsByName)
                return new Result("Bu valyuta avval kiritilgan", false);
            editingCurrency.setName(currency.getName());
        }

        currencyRepository.save(editingCurrency);
        return new Result("Valyuta muvaffaqiyatli o'zgartirildi", true);
    }
}
