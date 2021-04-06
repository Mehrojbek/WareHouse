package uz.pdp.ware_house.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.ware_house.entity.Output;

public interface OutputRepository extends JpaRepository<Output,Integer> {
    @Query(value = "select max(id) from output;",nativeQuery = true)
    Integer getMaxId();
}
