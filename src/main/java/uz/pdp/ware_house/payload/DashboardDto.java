package uz.pdp.ware_house.payload;

import lombok.Data;
import uz.pdp.ware_house.entity.Product;

import java.util.*;

@Data
public class DashboardDto {

    private List<DashboardInputProductDto> inputProductDtos;
    private List<DashboardOutputProductDto> outputProductDtos;
    private Integer productListWithExpireDate;
}
