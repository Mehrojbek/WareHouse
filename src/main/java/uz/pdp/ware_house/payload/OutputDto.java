package uz.pdp.ware_house.payload;

import lombok.Data;

@Data
public class OutputDto {
    private Integer wareHouseId;
    private Integer currencyId;
    private String factureNumber;
    private Integer cientId;
}
