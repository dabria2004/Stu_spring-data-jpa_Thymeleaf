package com.ppt.student_thymeleaf_jpa.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ppt.student_thymeleaf_jpa.Service.UserService;
import com.ppt.student_thymeleaf_jpa.entity.User;
import com.ppt.student_thymeleaf_jpa.repository.UserRepository;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

	@Autowired
	UserService userService;

    @GetMapping(value="/setupadduser")
	public ModelAndView setupadduser() {
		return new ModelAndView("USR001","user",new User());
	}

    @GetMapping(value="/setupadduseragain")
	public ModelAndView setupadduseragain(ModelMap model) {
		model.addAttribute("success", "Succesfully Registered!!");
		return new ModelAndView("USR001","user",new User());
	}

    @RequestMapping(value="/adduser", method=RequestMethod.POST)
	public String adduser(@ModelAttribute("user") @Validated User user,BindingResult bs, ModelMap model) {
		if(bs.hasErrors()) {
			return "USR001";
		}
		if(user.getUsername().isEmpty() || user.getEmail().isBlank() || user.getPassword().isBlank() || user.getConpassword().isBlank() || user.getRole().isBlank()) {
			model.addAttribute("error", "You must fullfill the fields.");
			return "USR001";
				}else if(userRepository.existsByEmail(user.getEmail())){
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
	@RequestMapping(value="/usersearch", method=RequestMethod.POST)
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

	// String id = searchId.isBlank() ? ")#<>(}" : searchId;
    //     String name = searchName.isBlank() ? ")#<>(}" : searchName;
    //     List<User> searchUserList = null;
    //     searchUserList = userService.selectUserListByIdOrName(id, name);
    //     if (searchUserList.size() == 0) {
    //         searchUserList = userService.selectAllUsers();
    //     }
    //     model.addAttribute("userList", searchUserList);
    //     return "USR003";

    @GetMapping(value="/setupUpdateUser")
	public ModelAndView setupUpdateUser(@RequestParam("id") String userid) {
		return new ModelAndView("USR002","user", userRepository.findById(userid));
	}
	
	@RequestMapping(value="/updateuser", method=RequestMethod.POST)
	public String updatebook(@ModelAttribute("user") @Validated User user,BindingResult bs, ModelMap model) {
		User userId = userRepository.findById(user.getUserid()).get();
		String userEmail = userId.getEmail();
		String uemail = user.getEmail();
		if(bs.hasErrors()) {
			return "USR002";
		}
		if(user.getUsername().isBlank() || user.getEmail().isBlank() || user.getPassword().isBlank() || user.getConpassword().isBlank() || user.getRole().isBlank()) {
			model.addAttribute("error", "You must fullfill the fields.");
			return "USR002";
				}
		else if(!user.getPassword().equals(user.getConpassword())) {
			model.addAttribute("password", "Passwords do not match!!");
			return "USR002";
		}else if(userRepository.existsByEmail(user.getEmail()) && !userEmail.equals(uemail)){
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
	public String deleteuser(@RequestParam("id") String userid,ModelMap model) {
		userRepository.deleteById(userid);
		return "redirect:/setupusersearch";
	}
}
