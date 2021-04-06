package uz.pdp.ware_house.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ware_house.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    boolean existsByNameAndCategoryId(String name, Integer category_id);
    long countAllByNameStartingWithAndNameEndingWith(String deleted, String name);
    boolean existsByName(String name);
}
