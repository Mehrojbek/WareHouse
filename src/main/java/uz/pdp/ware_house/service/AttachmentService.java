package uz.pdp.ware_house.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.ware_house.entity.Attachment;
import uz.pdp.ware_house.entity.AttachmentContent;
import uz.pdp.ware_house.payload.Result;
import uz.pdp.ware_house.repository.AttachmentContentRepository;
import uz.pdp.ware_house.repository.AttachmentRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    AttachmentContentRepository attachmentContentRepository;


    //CREATE
    @SneakyThrows
    public Result uploadFile(MultipartHttpServletRequest request) {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());

        if (file != null) {
            Attachment attachment = new Attachment();
            attachment.setContentType(file.getContentType());
            attachment.setName(file.getOriginalFilename());
            attachment.setSize(file.getSize());
            Attachment savedAttachment = attachmentRepository.save(attachment);

            AttachmentContent attachmentContent = new AttachmentContent();
            attachmentContent.setBytes(file.getBytes());
            attachmentContent.setAttachment(savedAttachment);
            attachmentContentRepository.save(attachmentContent);
            return new Result("Fayl saqlandi",true,savedAttachment.getId());
        }
        return new Result("Fayl yuklashda xatolik",false);
    }


    //READ FILE
    @SneakyThrows
    public Result getFile(Integer id, HttpServletResponse response){
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if(optionalAttachment.isPresent()){
            Attachment attachment = optionalAttachment.get();
            Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepository.findById(attachment.getId());
            if (optionalAttachmentContent.isPresent()) {
                AttachmentContent attachmentContent = optionalAttachmentContent.get();
                response.setHeader("Content-Disposition","attachment;filename=\""+attachment.getName()+"\"");
                response.setContentType(attachment.getContentType());
                FileCopyUtils.copy(attachmentContent.getBytes(),response.getOutputStream());
                return new Result("Muvaffaqiyatli yuklab olindi",true);
            }
            return new Result("File contenti topilmadi",false);
        }
        return new Result("File topilmadi",false);
    }


    //READ ALL ATTACHMENT
    public List<Attachment> getAll(){
        return attachmentRepository.findAll();
    }


}
