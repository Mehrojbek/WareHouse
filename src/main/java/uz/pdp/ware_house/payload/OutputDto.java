package uz.pdp.ware_house.payload;

import lombok.Data;

@Data
public class OutputDto {
    private Integer wareHouseId;
    private Integer CurrencyId;
    private String factureNumber;
    private Integer cientId;
}
