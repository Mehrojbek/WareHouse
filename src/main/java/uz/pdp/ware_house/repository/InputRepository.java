package uz.pdp.ware_house.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.ware_house.entity.Input;

import java.sql.Timestamp;
import java.util.List;

public interface InputRepository extends JpaRepository<Input,Integer> {
        @Query(value = "select max(id) from input",nativeQuery = true)
        Integer getMaxId();

        List<Input> findAllByDateBetween(Timestamp start, Timestamp end);
}
