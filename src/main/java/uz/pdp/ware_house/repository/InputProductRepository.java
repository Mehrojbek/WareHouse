package uz.pdp.ware_house.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ware_house.entity.Input;
import uz.pdp.ware_house.entity.InputProduct;

import java.util.*;

public interface InputProductRepository extends JpaRepository<InputProduct,Integer> {

        List<InputProduct> findAllByInputId(Integer input_id);
}
