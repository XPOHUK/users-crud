package hiber.web.controller;

import hiber.model.User;
import hiber.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping(value = "/users")
	public String printWelcome(ModelMap model) {
		model.addAttribute("users", userService.listUsers());
		model.addAttribute("user", new User());
		return "users";
	}

	@PostMapping(value = "/users")
	public String addUser(@ModelAttribute User user, Model model){
		userService.add(user);
		model.addAttribute("users", userService.listUsers());
		model.addAttribute("user", new User());
		return "users";

	}

	@PostMapping(value = "/users/modify")
	public String modifyUser(@ModelAttribute User user, Model model){
		userService.updateUser(user);
		model.addAttribute("users", userService.listUsers());
		return "redirect:/users";

	}

	@PostMapping(value = "/users/delete")
	public String deleteUser(@ModelAttribute User user, Model model){
		userService.removeUser(user);
		model.addAttribute("users", userService.listUsers());
		return "redirect:/users";
	}
	
}