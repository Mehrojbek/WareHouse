package uz.pdp.ware_house.payload;

import lombok.Data;

import java.util.ArrayList;

@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private ArrayList<Integer> wareHouseList;
}
