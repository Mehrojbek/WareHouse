package uz.pdp.ware_house.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.ware_house.entity.Input;
import uz.pdp.ware_house.entity.InputProduct;
import uz.pdp.ware_house.entity.Product;
import uz.pdp.ware_house.payload.DashboardDto;
import uz.pdp.ware_house.payload.DashboardInputProductDto;
import uz.pdp.ware_house.repository.InputProductRepository;
import uz.pdp.ware_house.repository.InputRepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Service
public class DashboardService {
    @Autowired
    InputProductRepository inputProductRepository;
    @Autowired
    InputRepository inputRepository;

    //GET GENERAL INFO
    public DashboardDto getGeneralInfo() {
        DashboardDto dashboardDto=new DashboardDto();
        HashSet<Product> productHashSet=new HashSet<>();
        List<InputProduct> inputProducts=new ArrayList<>();
        List<DashboardInputProductDto> inputProductDtos=new ArrayList<>();
        LocalDate date=LocalDate.now(ZoneId.of("Asia/Tashkent"));
        Timestamp start=Timestamp.valueOf(LocalDateTime.of(date, LocalTime.MIN));
        Timestamp end=Timestamp.valueOf(LocalDateTime.of(date,LocalTime.MAX));

        List<Input> allByDateBetween = inputRepository.findAllByDateBetween(start, end);
        for (Input input : allByDateBetween) {
            List<InputProduct> allByInputId = inputProductRepository.findAllByInputId(input.getId());
            if (allByInputId!=null){
                inputProducts.addAll(allByInputId);
            }
        }

        for (int i = 0; i < inputProducts.size(); i++) {
            InputProduct inputProduct = inputProducts.get(i);
            Product product = inputProduct.getProduct();
            if (productHashSet.add(product)) {
                inputProductDtos.add(new DashboardInputProductDto(product.getName(), inputProduct.getAmount(), inputProduct.getPrice()));
            }else {
                for (DashboardInputProductDto inputProductDto : inputProductDtos) {
                    if (inputProductDto.getName().equals(product.getName())) {
                        inputProductDto.setPrice(inputProduct.getPrice()+ inputProductDto.getPrice());
                        inputProductDto.setAmount(inputProduct.getAmount()+ inputProductDto.getAmount());
                    }
                }
            }
        }

        return dashboardDto;
    }
}
