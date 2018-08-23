package com.gym.application.controller;

import com.gym.application.model.Table;
import com.gym.application.model.User;
import com.gym.application.repository.TableRepository;
import com.gym.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class TableController {
    @Autowired
    TableRepository tableRepository;
    @Autowired
    UserService userService;

    @Value("${spring.redirect.url}")
    private String urlRedirect;
    @RequestMapping(value="admin/user/{userId}/table/{tableId}", method = RequestMethod.GET)
    public RedirectView userTest(@PathVariable Integer userId,@PathVariable Long tableId) {
        ModelAndView modelAndView = new ModelAndView();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getOne(userId);
        tableRepository.delete(tableId);
        modelAndView.addObject("fullname",user.getName()+" "+user.getLastName());
        modelAndView.addObject("user",user);
        List<Table> tables = tableRepository.findAllByUser(user);
        modelAndView.addObject("tables",tables);
        modelAndView.setViewName("admin/users/page");
        return new RedirectView(urlRedirect+"/admin/user/page/"+userId);
    }
}
