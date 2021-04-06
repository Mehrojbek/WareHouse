package uz.pdp.ware_house.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Output {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Timestamp date;

    @ManyToOne
    private WareHouse wareHouse;


    @ManyToOne
    private Currency currency;

    private String factureNumber;

    @Column(unique = true,nullable = false)
    private String code;

    @ManyToOne
    private Client client;

    private boolean active=true;
}
