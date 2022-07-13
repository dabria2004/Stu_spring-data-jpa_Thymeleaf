package com.ppt.student_thymeleaf_jpa.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ppt.student_thymeleaf_jpa.entity.User;
import com.ppt.student_thymeleaf_jpa.repository.UserRepository;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;


    @GetMapping(value="/setupadduser")
	public ModelAndView setupadduser() {
		return new ModelAndView("USR001","user",new User());
	}

    @GetMapping(value="/setupadduseragain")
	public ModelAndView setupadduseragain(ModelMap model) {
		model.addAttribute("success", "Succesfully Registered!!");
		return new ModelAndView("USR001","user",new User());
	}

    @PostMapping(value="/adduser")
	public String adduser(@ModelAttribute("user") @Validated User user,BindingResult bs, ModelMap model) {
		if(bs.hasErrors()) {
			model.addAttribute("error", "You must fullfill the fields.");
			return "USR001";
		}
		if(userRepository.existsByEmail(user.getEmail())){
			model.addAttribute("email", "Email already exists!!");
			return "USR001";
			}
		if(!user.getPassword().equals(user.getConpassword())) {
			model.addAttribute("password", "Passwords do not match!!");
			return "USR001";
		}else {
			List<User> userlist = userRepository.findAll();
			if(userlist.size() == 0 ) {
				user.setUserid("USR001");
			}else {
				int tempId = Integer.parseInt(userlist.get(userlist.size() - 1).getUserid().substring(3)) + 1;
				String userId = String.format("USR%03d", tempId);
				user.setUserid(userId);
			}
			userRepository.save(user);
			return "redirect:/setupadduseragain";
		}			
	}

    @GetMapping(value="/setupusersearch")
	public String usersearchpage(ModelMap model) { 
		List<User> userlist = userRepository.findAll();
		model.addAttribute("searchInfo", userlist);
		System.out.println("ShowAllUsers =>" + userlist.toString()); 
		return "USR003";
	}

	@PostMapping(value="/usersearch")
	public String usersearch(@RequestParam("userid") String userid,@RequestParam("username") String username,ModelMap model) {
		String id = userid.isBlank() ? "#&^@)" : userid;
		String name = username.isBlank() ? "#&^@)" : username;
		List<User> searchList = null;
		searchList = userRepository.findByUseridContainingOrUsernameContaining(id, name);
		if(searchList.size() == 0 ){
			searchList = userRepository.findAll();
		}
		model.addAttribute("searchInfo", searchList);
		return "USR003";
	}

    @GetMapping(value="/setupUpdateUser")
	public ModelAndView setupUpdateUser(@RequestParam("id") String userid) {
		return new ModelAndView("USR002","user", userRepository.findById(userid));
	}
	
	@PostMapping(value="/updateuser")
	public String updateuser(@ModelAttribute("user") @Validated User user,BindingResult bs, ModelMap model) {
		
		User userId = userRepository.findByUserid(user.getUserid());
		// String userEmail = userId.getEmail();
		// String uemail = user.getEmail();
		if(bs.hasErrors()) {
			model.addAttribute("error", "You must fullfill the fields.");
			return "USR002";
		}
		if(!user.getPassword().equals(user.getConpassword())) {
			model.addAttribute("password", "Passwords do not match!!");
			return "USR002";
		}else if(userRepository.existsByEmail(user.getEmail()) && !userId.getEmail().equals(user.getEmail())){
			model.addAttribute("error", "Email already exists!!");
			return "USR002";
		}
		else {
		    user = new User(user.getUserid(), user.getUsername(), user.getEmail(), user.getPassword(), user.getConpassword(), user.getRole());
			userRepository.save(user);
            return "redirect:/setupusersearch";
		}
	}
	
	@GetMapping(value="/deleteuser")
	public String deleteuser(@RequestParam("id") String userid) {
		userRepository.deleteById(userid);
		return "redirect:/setupusersearch";
	}
}
