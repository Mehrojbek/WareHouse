package uz.pdp.ware_house.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ware_house.entity.Measurement;

public interface MeasurementRepository extends JpaRepository<Measurement,Integer> {
    boolean existsByName(String name);
    Measurement findByName(String name);

}
