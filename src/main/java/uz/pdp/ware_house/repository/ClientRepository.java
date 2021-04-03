package uz.pdp.ware_house.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ware_house.entity.Client;

public interface ClientRepository extends JpaRepository<Client,Integer> {
    boolean existsByPhoneNumber(String phoneNumber);
}
