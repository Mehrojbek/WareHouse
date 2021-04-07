package uz.pdp.ware_house.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.ware_house.entity.Output;

import java.sql.Timestamp;
import java.util.List;

public interface OutputRepository extends JpaRepository<Output,Integer> {
    @Query(value = "select max(id) from output;",nativeQuery = true)
    Integer getMaxId();


    List<Output> findAllByDateBetween(Timestamp start, Timestamp end);
}
