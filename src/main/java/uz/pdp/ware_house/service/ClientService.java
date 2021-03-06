package uz.pdp.ware_house.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.ware_house.entity.Client;
import uz.pdp.ware_house.payload.Deleted;
import uz.pdp.ware_house.payload.Result;
import uz.pdp.ware_house.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;

    //CREATE
    public Result add(Client client) {
        //CHECK SPECIAL_NAME
        if (client.getPhoneNumber().startsWith(Deleted.DELETED))
            return new Result("Telefon raqam " + Deleted.DELETED + " bilan boshlanmasligi lozim", false);

        boolean existsByPhoneNumber = clientRepository.existsByPhoneNumber(client.getPhoneNumber());
        if (existsByPhoneNumber)
            return new Result("Bu telefon raqami avval saqlangan", false);
        clientRepository.save(client);
        return new Result("Mijoz muvaffaqiyatli saqlandi", true);
    }


    //READ ALL
    public List<Client> getAll() {
        return clientRepository.findAll();
    }


    //READ ONE
    public Result getOne(Integer id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (!optionalClient.isPresent())
            return new Result("Mijoz topilmadi", false);
        return new Result("Muvaffaqiyatli abjarildi", true, optionalClient.get());
    }


    //DELETE
    public Result delete(Integer id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (!optionalClient.isPresent())
            return new Result("Mijoz topilmadi", false);

        Client client = optionalClient.get();
        if (!client.isActive())
            return new Result("Bu mijoz allaqachon uchirilgan", false);

        //HOW MANY DELETED
        long numberOfDeletedClient = clientRepository.countAllByPhoneNumberStartingWithAndPhoneNumberEndingWith(Deleted.DELETED, client.getPhoneNumber()) + 1;

        client.setActive(false);
        client.setPhoneNumber(Deleted.DELETED + numberOfDeletedClient + ":" + client.getPhoneNumber());

        clientRepository.save(client);
        return new Result("Mijoz uchirildi", true);
    }


    //PUT
    public Result edit(Integer id, Client client) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (!optionalClient.isPresent())
            return new Result("Mijoz topilmadi", false);
        Client editingClient = optionalClient.get();
        editingClient.setName(client.getName());
        if (!editingClient.getPhoneNumber().equals(client.getPhoneNumber())) {
            boolean existsByPhoneNumber = clientRepository.existsByPhoneNumber(client.getPhoneNumber());
            if (existsByPhoneNumber)
                return new Result("Bu telefon raqami avval saqlangan", false);
            editingClient.setPhoneNumber(client.getPhoneNumber());
        }
        clientRepository.save(editingClient);
        return new Result("Mijoz muvaffaqiyatli o'zgartirildi", true);
    }
}
