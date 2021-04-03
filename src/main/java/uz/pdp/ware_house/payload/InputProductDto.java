package uz.pdp.ware_house.payload;

import lombok.Data;

import java.sql.Date;

@Data
public class InputProductDto {
    private Integer productId;
    private Double amount;
    private Double price;
    private Date expireDate;
    private Integer inputId;
}
