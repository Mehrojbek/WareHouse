package uz.pdp.ware_house.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ware_house.entity.WareHouse;

public interface WareHouseRepository extends JpaRepository<WareHouse,Integer> {
    boolean existsByName(String name);
    long countAllByNameStartingWithAndNameEndingWith(String deleted, String name);
}
