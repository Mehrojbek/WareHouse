package uz.pdp.ware_house.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ware_house.entity.OutputProduct;

import java.util.List;

public interface OutputProductRepository extends JpaRepository<OutputProduct,Integer> {

    List<OutputProduct> findAllByOutputId(Integer output_id);
}
