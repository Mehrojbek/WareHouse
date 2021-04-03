package uz.pdp.ware_house.payload;

import lombok.Data;

import java.sql.Date;

@Data
public class InputDto {
    private Date date;
    private Integer wareHouseId;
    private Integer suplierId;
    private Integer currencyId;
    private String factureNumber;
}
