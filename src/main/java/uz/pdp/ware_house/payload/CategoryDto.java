package uz.pdp.ware_house.payload;

import lombok.Data;

@Data
public class CategoryDto {
    private String name;
    private boolean active;
    private Integer parentCategoryId;
}
