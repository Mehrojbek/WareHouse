package uz.pdp.ware_house.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ware_house.entity.Currency;

public interface CurrencyRepository extends JpaRepository<Currency,Integer> {
    boolean existsByName(String name);
    Currency findAllByName(String name);
}
