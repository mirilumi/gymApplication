package com.gym.application.controller;

import com.gym.application.model.ThirdBox;
import com.gym.application.model.User;
import com.gym.application.repository.ThirdBoxRepository;
import com.gym.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ThirdController {
    @Autowired
    ThirdBoxRepository thirdBoxRepository;
    @Value("${spring.redirect.url}")
    private String urlRedirect;
    @Autowired
    UserService userService;
    @RequestMapping(value="/admin/user/thirdBox/{id}", method = RequestMethod.POST)
    public RedirectView saveTable(@PathVariable Integer id , @Valid ThirdBox thirdBox, BindingResult bindingResult) {
        User user = userService.getOne(id);
        thirdBox.setUser(user);
        thirdBoxRepository.saveAndFlush(thirdBox);
        return new RedirectView(urlRedirect+"/admin/user/page/"+id);
    }
    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "src/main/resources/static/images/uploaded/";


    @PostMapping("/admin/user/thirdBox/upload/{id}")
    public RedirectView singleFileUpload(@PathVariable Integer id,@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return new RedirectView(urlRedirect+"/admin/user/page/"+id);
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + "image_"+id+".jpg");
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
            ThirdBox thirdBox1 = thirdBoxRepository.findAllByUser(userService.getOne(id));
            if(thirdBox1 == null){
                ThirdBox thirdBox = new ThirdBox();
                thirdBox.setUser(userService.getOne(id));
                thirdBox.setDescription("");
                thirdBox.setImage("/images/uploaded/image_"+id+".jpg");
                thirdBoxRepository.saveAndFlush(thirdBox);
            }else{
                thirdBox1.setImage("/images/uploaded/image_"+id+".jpg");
                thirdBoxRepository.saveAndFlush(thirdBox1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new RedirectView(urlRedirect+"/admin/user/page/"+id);
    }


}

