package uz.pdp.ware_house.payload;

import lombok.Data;

@Data
public class ProductDto {
    private String name;
    private Integer categoryId;
    private Integer photoId;
    private Integer measurementId;
}
