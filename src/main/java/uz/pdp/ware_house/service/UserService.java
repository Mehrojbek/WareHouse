package uz.pdp.ware_house.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.ware_house.entity.User;
import uz.pdp.ware_house.entity.WareHouse;
import uz.pdp.ware_house.payload.Deleted;
import uz.pdp.ware_house.payload.Result;
import uz.pdp.ware_house.payload.UserDto;
import uz.pdp.ware_house.repository.UserRepository;
import uz.pdp.ware_house.repository.WareHouseRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    WareHouseRepository wareHouseRepository;

    //CREATE
    public Result add(UserDto userDto) {
        //CHECK SPECIAL NAME
        if (userDto.getPhoneNumber().startsWith(Deleted.DELETED))
            return new Result("Telefon raqami "+Deleted.DELETED+" bilan boshlanishi mumkin emas",false);

        boolean existsByPhoneNumber = userRepository.existsByPhoneNumber(userDto.getPhoneNumber());
        if (existsByPhoneNumber)
            return new Result("Bu telefon raqami avval qo'shilgan", false);
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        HashSet<WareHouse> wareHouseSet = new HashSet<>(wareHouseRepository.findAllById(userDto.getWareHouseList()));
        user.setWareHouse(wareHouseSet);

        //GENERATING UNIQUE CODE WITH ID
        Integer maxId = userRepository.getMaxId();
        if (maxId == null) {
            maxId = 1;
        } else {
            maxId += 1;
        }

        user.setCode(maxId.toString());
        userRepository.save(user);
        return new Result("User muvaffaqiyatli qo'shildi", true);
    }


    //READ ALL
    public List<User> getUserList() {
        return userRepository.findAll();
    }


    //READ ONE
    public Result getOne(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent())
            return new Result("User topilmadi", false);
        return new Result("User topildi", true, optionalUser.get());
    }


    //DELETE
    public Result delete(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent())
            return new Result("User topilmadi", false);
        User user = optionalUser.get();
        //HOW MANY TIMES DELETED
        long numberOfDeletedUser = userRepository.countAllByPhoneNumberStartingWithAndPhoneNumberEndingWith(Deleted.DELETED, user.getPhoneNumber())+1;
        user.setPhoneNumber(Deleted.DELETED+numberOfDeletedUser+":"+user.getPhoneNumber());
        user.setActive(false);
        userRepository.save(user);
        return new Result("User muvaffaqiyatli uchirildi", true);
    }


    //PUT
    public Result edit(Integer id, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent())
            return new Result("User topilmadi", false);

        User editingUser = optionalUser.get();
        editingUser.setFirstName(userDto.getFirstName());
        editingUser.setLastName(userDto.getLastName());
        editingUser.setPassword(userDto.getPassword());


        HashSet<WareHouse> wareHouseSet = new HashSet<>(wareHouseRepository.findAllById(userDto.getWareHouseList()));
        editingUser.setWareHouse(wareHouseSet);
        if (!editingUser.getPhoneNumber().equals(userDto.getPhoneNumber())) {
        //CHECK UNIQUE
            boolean existsByPhoneNumber = userRepository.existsByPhoneNumber(userDto.getPhoneNumber());
            if (existsByPhoneNumber)
                return new Result("Ushbu telefon raqami avval qo'shilgan", false);
            editingUser.setPhoneNumber(userDto.getPhoneNumber());
        }
        userRepository.save(editingUser);
        return new Result("User muvaffaqiyatli o'zgartirildi",true);
    }
}
