package uz.pdp.ware_house.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardInputProductDto {
    private String name;
    private Double amount;
    private Double price;
}
