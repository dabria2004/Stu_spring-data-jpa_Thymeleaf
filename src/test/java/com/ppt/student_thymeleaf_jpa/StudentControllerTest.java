package com.ppt.student_thymeleaf_jpa;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.ppt.student_thymeleaf_jpa.entity.Student;
import com.ppt.student_thymeleaf_jpa.entity.Course;
import com.ppt.student_thymeleaf_jpa.repository.StudentRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
	private MockMvc mockMvc;

    @MockBean
    StudentRepository studentRepository;

    @Test
	public void testSetupAddStudent() throws Exception {
		this.mockMvc.perform(get("/setupaddstudent"))
		.andExpect(status().isOk())
		.andExpect(view().name("STU001"))
        .andExpect(model().attributeExists("courseList"))
		.andExpect(model().attributeExists("sbean"));
	}

    @Test
	public void testSetupAddStudentAgain() throws Exception {
		this.mockMvc.perform(get("/setupaddstudentagain"))
		.andExpect(status().isOk())
		.andExpect(view().name("STU001"))
        .andExpect(model().attributeExists("courseList"))
        .andExpect(model().attributeExists("success"))
		.andExpect(model().attributeExists("sbean"));
	}

    @Test
	public void testAddStudentValidate() throws Exception {
		this.mockMvc.perform(post("/addstudent"))
		.andExpect(status().isOk())
		.andExpect(view().name("STU001"));	
	}

    @Test
	public void testAddStudent() throws Exception {
        Course course1 = Course.builder()
        .classid("COU023")
        .classname("IT")
        .build();

        Course course2 = Course.builder()
        .classid("COU021")
        .classname("Engineering")
        .build();

        ArrayList<Course> list = new ArrayList<Course>();
        list.add(course1);
        list.add(course2);

        Student student = Student.builder()
        .studentid("STU017")
        .studentname("Mg Mg")
        .dob("2/23/2004")
        .gender("Male")
        .phone("123456789")
        .education("Diploma in IT")
        .attendCourses(list)
        .build();
		this.mockMvc.perform(post("/addstudent").flashAttr("sbean", student))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/setupaddstudentagain"));	
	}

    @Test
	public void testSetupStudentSearch() throws Exception {
		this.mockMvc.perform(get("/setupstudentsearch"))
		.andExpect(status().isOk())
		.andExpect(view().name("STU003"))
        .andExpect(model().attributeExists("studentList"));
	}

    @Test
    public void testStudentSearchIsBlank() throws Exception{
        this.mockMvc.perform(post("/searchstudent").param("id", "").param("name", "").param("course", ""))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/setupstudentsearch"));	
    }

    @Test
    public void testStudentSearch() throws Exception{
        this.mockMvc.perform(post("/searchstudent").param("id", "STU005").param("name", "Stella").param("course", "values"))
		.andExpect(status().isOk())
		.andExpect(view().name("STU003"))
		.andExpect(model().attributeExists("studentList"));
    }

    @Test
    public void testStudentDetail() throws Exception {
        this.mockMvc.perform(get("/studentdetail").param("id", "STU017"))
        .andExpect(model().attributeExists("sbean"))
        .andExpect(model().attributeExists("data"))
        .andExpect(model().attributeExists("courseList"))
		.andExpect(status().isOk())
		.andExpect(view().name("STU002")); 
    }  

    @Test
    public void testStudentUpdateValidate() throws Exception {
        this.mockMvc.perform(post("/updatestudent"))
        .andExpect(model().attributeExists("error"))
        .andExpect(model().attributeExists("data"))
        .andExpect(model().attributeExists("courseList"))
		.andExpect(status().isOk())
		.andExpect(view().name("STU002")); 
    }

    @Test
    public void testStudentUpdate() throws Exception {
        Course course1 = Course.builder()
        .classid("COU023")
        .classname("IT")
        .build();

        Course course2 = Course.builder()
        .classid("COU021")
        .classname("Engineering")
        .build();

        ArrayList<Course> list = new ArrayList<Course>();
        list.add(course1);
        list.add(course2);

        Student student = Student.builder()
        .studentid("STU017")
        .studentname("Mg Mg")
        .dob("2/23/2004")
        .gender("Male")
        .phone("123456789")
        .education("Diploma in IT")
        .attendCourses(list)
        .build();
		this.mockMvc.perform(post("/updatestudent").flashAttr("sbean", student))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/setupstudentsearch"));	
    }

    @Test
		public void testDeleteStudent() throws Exception {
		 this.mockMvc.perform(get("/deleteStudent").param("id","STU017"))
		 .andExpect(status().is(302))
		 .andExpect(redirectedUrl("/setupstudentsearch"));	
		}
}

// Course course1 = Course.builder()
            // .classid("COU023")
            // .classname("IT")
            // .build();
    
            // Course course2 = Course.builder()
            // .classid("COU021")
            // .classname("Engineering")
            // .build();
    
            // ArrayList<Course> list = new ArrayList<Course>();
            // list.add(course1);
            // list.add(course2);
    
            // Student student = Student.builder()
            // .studentid("STU017")
            // .studentname("Mg Mg")
            // .dob("2/23/2004")
            // .gender("Male")
            // .phone("123456789")
            // .education("Diploma in IT")
            // .attendCourses(list)
            // .build();
            //studentRepository.save(student);
            //studentRepository.deleteStudentById(student.getStudentid());
            //assertTrue(studentRepository.findById(student.getStudentid()).isEmpty());
