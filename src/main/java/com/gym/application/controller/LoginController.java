package com.gym.application.controller;

import javax.validation.Valid;

import com.gym.application.model.SecondBox;
import com.gym.application.model.Table;
import com.gym.application.model.ThirdBox;
import com.gym.application.repository.SecondBoxRepository;
import com.gym.application.repository.TableRepository;
import com.gym.application.repository.ThirdBoxRepository;
import com.gym.application.model.User;
import com.gym.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	@Autowired
    TableRepository tableRepository;
	@Autowired
    SecondBoxRepository secondBoxRepository;
	@Autowired
	ThirdBoxRepository thirdBoxRepository;
	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registration");
			
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		modelAndView.setViewName("/admin/home");
		return modelAndView;
	}
	@RequestMapping(value="/user/home", method = RequestMethod.GET)
	public ModelAndView userHome(){

		ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		ThirdBox thirdBox  = thirdBoxRepository.findAllByUser(user);
		SecondBox secondBox = secondBoxRepository.findAllByUser(user);
		modelAndView.addObject("fullname",user.getName()+" "+user.getLastName());
		modelAndView.addObject("user",user);
		List<Table> tables = tableRepository.findAllByUser(user);
		modelAndView.addObject("tables",tables);
		modelAndView.addObject("secondBox",secondBox);
		modelAndView.addObject("thirdBox",thirdBox);
		modelAndView.setViewName("/admin/users/home");
		return modelAndView;
	}
	@RequestMapping(value="/admin/user/page/preview/{id}", method = RequestMethod.GET)
	public ModelAndView userHome(@PathVariable Integer id){

		ModelAndView modelAndView = new ModelAndView();
		User user = userService.getOne(id);
		ThirdBox thirdBox  = thirdBoxRepository.findAllByUser(user);
		SecondBox secondBox = secondBoxRepository.findAllByUser(user);
		modelAndView.addObject("fullname",user.getName()+" "+user.getLastName());
		modelAndView.addObject("user",user);
		List<Table> tables = tableRepository.findAllByUser(user);
		modelAndView.addObject("tables",tables);
		modelAndView.addObject("secondBox",secondBox);
		modelAndView.addObject("thirdBox",thirdBox);
		modelAndView.setViewName("/admin/users/home");
		return modelAndView;
	}
}
