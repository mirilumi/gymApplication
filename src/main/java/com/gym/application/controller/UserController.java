package com.gym.application.controller;


import com.gym.application.model.SecondBox;
import com.gym.application.model.Table;
import com.gym.application.model.ThirdBox;
import com.gym.application.model.User;
import com.gym.application.repository.SecondBoxRepository;
import com.gym.application.repository.TableRepository;
import com.gym.application.repository.ThirdBoxRepository;
import com.gym.application.repository.UserRepository;
import com.gym.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TableRepository tableRepository;

    @Autowired
    SecondBoxRepository secondBoxRepository;
    @Autowired
    ThirdBoxRepository thirdBoxRepository;
    @Value("${spring.redirect.url}")
    private String urlRedirect;
    @RequestMapping(value="/user", method = RequestMethod.GET)
    public ModelAndView userTest() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with User Role");
        modelAndView.setViewName("user/user");
        return modelAndView;
    }
    @RequestMapping(value="admin/users/list", method = RequestMethod.GET)
    public ModelAndView findAll() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<User> users = userService.findAll();
        modelAndView.addObject("users",users);
        modelAndView.setViewName("admin/users/list");
        return modelAndView;
    }
    @RequestMapping(value="admin/user/approved/{id}", method = RequestMethod.GET)
    public ModelAndView findAll(@PathVariable Integer id) {
        User user = userService.getOne(id);
        user.setActive(!user.getActive());
        userRepository.saveAndFlush(user);
        return findAll();
    }
    @RequestMapping(value="/admin/user/page/edit/{id}", method = RequestMethod.POST)
    public RedirectView saveTable(@PathVariable Integer id ,@Valid Table table, BindingResult bindingResult) {
        table.setUser(userService.getOne(id));
        tableRepository.saveAndFlush(table);
        return new RedirectView(urlRedirect+"/admin/user/page/"+id);
    }
    @RequestMapping(value="admin/user/page/{id}", method = RequestMethod.GET)
    public ModelAndView createPage(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.getOne(id);
        SecondBox secondBox = secondBoxRepository.findAllByUser(user);
        ThirdBox thirdBox  = thirdBoxRepository.findAllByUser(user);
        modelAndView.addObject("fullname",user.getName()+" "+user.getLastName());
        modelAndView.addObject("user",user);
        List<Table> tables = tableRepository.findAllByUser(user);
        modelAndView.addObject("tables",tables);
        modelAndView.addObject("secondBox",secondBox);
        modelAndView.addObject("thirdBox",thirdBox);
        modelAndView.setViewName("admin/users/page");
        return modelAndView;
    }
}
