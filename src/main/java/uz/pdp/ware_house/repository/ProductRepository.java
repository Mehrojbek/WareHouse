package uz.pdp.ware_house.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.ware_house.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    boolean existsByNameAndCategoryId(String name, Integer category_id);

    @Query(value = "select max(id) from product",nativeQuery = true)
    Integer getMaxId();

    long countAllByNameStartingWithAndNameEndingWith(String deleted, String name);



}
