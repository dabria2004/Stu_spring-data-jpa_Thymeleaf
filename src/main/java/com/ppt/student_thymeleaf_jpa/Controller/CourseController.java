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
import org.springframework.web.servlet.ModelAndView;

import com.ppt.student_thymeleaf_jpa.entity.Course;
import com.ppt.student_thymeleaf_jpa.repository.CourseRepository;

@Controller
public class CourseController {
    
    @Autowired
    CourseRepository courseRepository;

    @GetMapping(value = "/setupaddclass")
	public ModelAndView setupaddclass(ModelMap model, Course cbean) {
        List<Course> courseList = courseRepository.findAll();
		if (courseList.size() == 0) {
			cbean.setClassid("COU001");
		}else {
		int tempId = Integer.parseInt(courseList.get(courseList.size() - 1).getClassid().substring(3)) + 1;
		String classid = String.format("COU%03d", tempId);
		cbean.setClassid(classid);
		}
		return new ModelAndView("BUD003", "cbean", cbean);
	}
	
	@GetMapping(value = "/setupaddclassagain")
	public ModelAndView setupaddclassagain(ModelMap model) {
		Course cbean = new Course();
		List<Course> courseList = courseRepository.findAll();
		if (courseList.size() == 0) {
			cbean.setClassid("COU001");
		}else {
		int tempId = Integer.parseInt(courseList.get(courseList.size() - 1).getClassid().substring(3)) + 1;
		String classid = String.format("COU%03d", tempId);
		cbean.setClassid(classid);
		}
		model.addAttribute("success", "Successfully Registered!!");
		return new ModelAndView("BUD003", "cbean", cbean);
	}
	
	@PostMapping(value = "/addclass")
	public String addclass(@ModelAttribute("cbean") @Validated Course cbean, BindingResult bs, ModelMap model) {
		// if(bs.hasErrors()) {
		// 	return "BUD003";
		// }
		if (cbean.getClassid().equals("") || cbean.getClassname().equals("")) {
			model.addAttribute("error", "You must fullfill the fields!!");
			return "BUD003";
		}else if(courseRepository.existsByClassname(cbean.getClassname())){
            model.addAttribute("classname", "Class name already exists.");
            return "BUD003";
        }
         else {
			courseRepository.save(cbean);
				return "redirect:/setupaddclassagain";
		}
	}
}
