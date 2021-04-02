package uz.pdp.ware_house.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.ware_house.payload.Result;
import uz.pdp.ware_house.service.AttachmentService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;

    @PostMapping("/uploadFile")
    public Result uploadFile(MultipartHttpServletRequest request){
        return attachmentService.uploadFile(request);
    }

    @GetMapping("/{id}")
    public Result getFile(@PathVariable Integer id, HttpServletResponse response){
        return attachmentService.getFile(id,response);
    }
}
