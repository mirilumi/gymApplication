package com.gym.application.controller;

import com.gym.application.model.SecondBox;
import com.gym.application.model.User;
import com.gym.application.repository.SecondBoxRepository;
import com.gym.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
public class SecondBoxController {
    @Autowired
    SecondBoxRepository secondBoxRepository;
    @Value("${spring.redirect.url}")
    private String urlRedirect;
    @Autowired
    UserService userService;
    @RequestMapping(value="/admin/user/secondBox/{id}", method = RequestMethod.POST)
    public RedirectView saveTable(@PathVariable Integer id , @Valid SecondBox secondBox, BindingResult bindingResult) {
        User user = userService.getOne(id);
        secondBox.setUser(user);
        secondBoxRepository.saveAndFlush(secondBox);
        return new RedirectView(urlRedirect+"/admin/user/page/"+id);
    }
}
