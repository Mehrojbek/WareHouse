package uz.pdp.ware_house;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.ware_house.entity.Category;
import uz.pdp.ware_house.repository.SuplierRepository;

import java.util.*;

@RestController
@RequestMapping("/suplier")
public class Sinov {
    @Autowired
    SuplierRepository suplierRepository;

    @GetMapping
    public long get(){
        return suplierRepository.count();
    }

    public static void main(String[] args) {
        Category category=new Category();
        category.setCategory(new Category());
        category.setActive(true);
        category.setName("one");

        Category category1=new Category();
        category1.setCategory(new Category());
        category1.setActive(true);
        category1.setName("one");

        System.out.println(category.equals(category1));
    }
}
