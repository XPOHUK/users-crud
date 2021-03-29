package hiber.controller;

import hiber.model.Role;
import hiber.model.User;
import hiber.model.UserDto;
import hiber.service.RoleService;
import hiber.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private RoleService roleService;

	@GetMapping(value = "/admin")
	public String printWelcome(ModelMap model) {
		model.addAttribute("users", userService.listUsers());
		model.addAttribute("user", new UserDto());
		return "admin";
	}

	@PostMapping(value = "/admin")
	public String addUser(@ModelAttribute UserDto userDto, Model model){
		Map<String, String> errorMap = userService.validateUser(userDto);
		if(!errorMap.isEmpty()) {
			model.addAttribute("errorMap", errorMap);
			model.addAttribute("user", userDto);
		} else {
			userService.createUser(userDto);
			model.addAttribute("user", new UserDto());
		}
		model.addAttribute("users", userService.listUsers());
		return "admin";
	}

	@PostMapping(value = "/admin/modify")
	public String modifyUser(@ModelAttribute User user, Model model){
		userService.updateUser(user);
		model.addAttribute("users", userService.listUsers());
		return "redirect:/admin";

	}

	@PostMapping(value = "/admin/delete")
	public String deleteUser(@ModelAttribute User user, Model model){
		userService.removeUser(user);
		model.addAttribute("users", userService.listUsers());
		return "redirect:/admin";
	}

	@GetMapping(value = "/admin/manage_roles")
	public String manageRoles( Model model,
							  @RequestParam(value = "user_id") String user_id){
		User user = userService.getUserById(Long.parseLong(user_id));
		model.addAttribute("user", user);
		List<Role> availableRoles = roleService.getAllRoles();
		availableRoles.removeAll(user.getRoles());
		model.addAttribute("availableRoles", availableRoles);
		return "manage_roles";
	}

	@GetMapping(value = "/admin/add_role")
	public String addRole(@RequestParam(value = "role") String role,
						  @RequestParam(value = "user_id") String user_id){
		User user = userService.getUserById(Long.parseLong(user_id));
		user.getRoles().add(roleService.getRoleByName(role));
		userService.updateUser(user);
		return "redirect:/admin/manage_roles?user_id=" + user_id;
	}

	@GetMapping(value = "/admin/remove_role")
	public String removeRole(@RequestParam(value = "role") String role,
						  @RequestParam(value = "user_id") String user_id){
		User user = userService.getUserById(Long.parseLong(user_id));
		user.getRoles().remove(user.getRoles().stream().filter(r -> r.getRole().equals(role)).findFirst().get());
		userService.updateUser(user);
		return "redirect:/admin/manage_roles?user_id=" + user_id;
	}

	@GetMapping(value = "/user")
	public String printHello(UsernamePasswordAuthenticationToken token, ModelMap model) {
		User user1 = (User) token.getPrincipal();
		model.addAttribute("user", user1); //userDetailsService.loadUserByUsername(token.getName()));
		return "user";
	}

}