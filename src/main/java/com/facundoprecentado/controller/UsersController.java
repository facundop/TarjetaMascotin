package com.facundoprecentado.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.facundoprecentado.domain.User;
import com.facundoprecentado.repository.UserRepository;
import com.facundoprecentado.service.MailService;
import com.facundoprecentado.service.UserService;

@Controller
public class UsersController {

	private final UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MailService mailService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UsersController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Collection<User> getUsers() {
		Collection<User> clients = userService.findAll();
		return clients;
	}

	@RequestMapping("/registration")
	public String registration(Model model) {
		model.addAttribute("user", new User());
		return "registration";
	}

	@PostMapping("/registration")
	public String registration(@ModelAttribute User user) {
		if (!isUserRegistered(user)) {
			try {
				user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
				userRepository.save(user);
				if (user.getType() == 0) { // Es SOCIO
					mailService.sendNewSocioMail(user);
				}
				if (user.getType() == 1) { // Es ASOCIADO
					mailService.sendNewAsociadoMail(user);
				}

				return "registration-success";
			} catch (Error e) {
				return "error";
			}
		} else {
			return "registration-fail";
		}

	}

	private boolean isUserRegistered(User user) {
		if (userRepository.findByUsername(user.getUsername()) == null) {
			return false;
		}
		return true;
	}
}