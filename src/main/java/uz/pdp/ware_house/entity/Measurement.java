package uz.pdp.ware_house.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.pdp.ware_house.entity.template.AbsEntity;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Measurement extends AbsEntity {
}
