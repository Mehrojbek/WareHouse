package uz.pdp.ware_house.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.ware_house.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment,Integer> {
}
