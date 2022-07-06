package com.ppt.student_thymeleaf_jpa.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ppt.student_thymeleaf_jpa.entity.Course;
import com.ppt.student_thymeleaf_jpa.entity.Student;
import com.ppt.student_thymeleaf_jpa.repository.CourseRepository;
import com.ppt.student_thymeleaf_jpa.repository.StudentRepository;

@Controller
public class StudentController {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @GetMapping(value = "/setupaddstudent")
	public ModelAndView setupaddstudent(ModelMap model) {
		List<Course> courseList = courseRepository.findAll();
		model.addAttribute("courseList", courseList);
		return new ModelAndView ("STU001", "sbean", new Student());
	}
	
	@GetMapping(value = "/setupaddstudentagain")
	public ModelAndView setupaddstudentagain(ModelMap model) {
		List<Course> courseList = courseRepository.findAll();
		model.addAttribute("courseList", courseList);
		model.addAttribute("success", "Successfully Registered!!");
		return new ModelAndView ("STU001", "sbean", new Student());
	}
	
	@PostMapping(value = "/addstudent")
	public String addstudent(@ModelAttribute("sbean") @Validated Student sbean,BindingResult bs, ModelMap model) {
		
		List<Student> studentList = studentRepository.findAll();
		List<Course> courseList = courseRepository.findAll();
		model.addAttribute("courseList", courseList);
		System.out.println(studentList);
		if (studentList == null) {
			studentList = new ArrayList<>();
		}
		if (studentList.size() == 0) {
			sbean.setStudentid("STU001");
		} else {
			int tempId = Integer.parseInt(studentList.get(studentList.size() - 1).getStudentid().substring(3)) + 1;
			String userId = String.format("STU%03d", tempId);
			sbean.setStudentid(userId);
		}
        if(bs.hasErrors()) {
			System.out.println("Taw 123123123123thar");
			return "STU001";
		}
        if (sbean.getAttendCourses().size() == 0) {
            model.addAttribute("error", "You must fullfill the fields!!");
            model.addAttribute("data", sbean);
            return "STU001";
        }else if (sbean.getStudentname().isBlank() || sbean.getDob().isBlank() || sbean.getGender().isBlank() || sbean.getPhone().isBlank() || sbean.getEducation().isBlank()) {
            model.addAttribute("error", "You must fullfill the fields!!");
            model.addAttribute("data", sbean);
            return "STU001";
        }
        studentRepository.save(sbean);
		return "redirect:/setupaddstudentagain";	
	}

    @GetMapping("/setupstudentsearch")
	public String studentManagement(ModelMap model) {	
		List<Student> studentList = studentRepository.findAll();
		model.addAttribute("studentList", studentList);
		return "STU003";
	}
	
	@GetMapping("/studentdetail")
	public ModelAndView seeMore(@RequestParam("id") String studentid, ModelMap model) {
        Student student = studentRepository.findById(studentid).get();
        List<Course> courseList = courseRepository.findAll();
		model.addAttribute("courseList", courseList);
		return new ModelAndView ("STU002", "sbean", student);
	}
    @PostMapping("/updatestudent")
	public String updateStudent(@ModelAttribute("sbean") @Validated Student sbean, BindingResult bs, ModelMap model) {
		//String studentid = sbean.getStudentid();
		System.out.println("sbean => " + sbean);
		List<Course> courseList = courseRepository.findAll();
		model.addAttribute("courseList", courseList);
		
		if(bs.hasErrors()) {
			model.addAttribute("data", sbean);
			return "STU002";
		}
		if (sbean.getAttendCourses().size() == 0) {
			model.addAttribute("error", "Fill the blank !!");
			model.addAttribute("data", sbean);
			return "STU002";
		}
		if (sbean.getStudentname().isBlank() || sbean.getDob().isBlank() || sbean.getGender().isBlank()
				|| sbean.getPhone().isBlank() || sbean.getEducation().isBlank()) {
			model.addAttribute("error", "Fill the blank !!");
			model.addAttribute("data", sbean);
			return "STU002";
		}
		// studentRepository.deleteById(studentid);
        studentRepository.save(sbean);
		return "redirect:/setupstudentsearch";
	}
	
	
	@GetMapping("/deleteStudent/{id}")
	public String deleteStudent(@PathVariable("id") String studentid) {
        System.out.println(studentid);
        //studentRepository.deleteCoursesByStudentId(studentid);
		studentRepository.deleteStudentById(studentid);
		return "redirect:/setupstudentsearch";
	}

	@PostMapping("/searchstudent")
	public String searchStudent(@RequestParam("id") String id, @RequestParam("name") String name,
			@RequestParam("course") String course, ModelMap model) {
		String sid = id.isBlank() ? "%$&*" : id;
		String sname = name.isBlank() ? "%$&*" : name;
		String scourse = course.isBlank() ? "%$&*" : course;
		System.out.println( "sid => " + sid + " " + "sname => " + sname + " " + "scourse => " + scourse);
		
		if(id.isBlank() && name.isBlank() && course.isBlank()){
			System.out.println("if condition");
			return "redirect:/setupstudentsearch";
		}else{
			System.out.println("else condition");
			List<Student> studentList = studentRepository.findDistinctByStudentidContainingOrStudentnameContainingOrAttendCourses_ClassnameContaining(sid, sname, scourse);
			System.out.println("studentList => "+ studentList);
			model.addAttribute("studentList", studentList);
		return "STU003";
		}	
	}
}
