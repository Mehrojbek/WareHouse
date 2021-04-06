package uz.pdp.ware_house.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ware_house.entity.Suplier;

public interface SuplierRepository extends JpaRepository<Suplier,Integer> {
    boolean existsByPhoneNumber(String phoneNumber);
    long countAllByPhoneNumberStartingWithAndPhoneNumberEndingWith(String deleted, String phoneNumber);
}
