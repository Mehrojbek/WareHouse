package uz.pdp.ware_house;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.ware_house.entity.Category;
import uz.pdp.ware_house.repository.SuplierRepository;

import java.sql.Timestamp;
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
        ArrayList<Integer> integers=new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);

        HashSet<Integer> integerHashSet =new HashSet<>(integers);

        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp);
    }
}
