package com.ppt.student_thymeleaf_jpa;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.ppt.student_thymeleaf_jpa.entity.Course;
import com.ppt.student_thymeleaf_jpa.repository.CourseRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerTest {
    
    @Autowired
	private MockMvc mockMvc;

    @MockBean
    CourseRepository courseRepository;

    @Test
	public void testSetupAddCourse() throws Exception {
		this.mockMvc.perform(get("/setupaddclass"))
		.andExpect(status().isOk())
		.andExpect(view().name("BUD003"))
		.andExpect(model().attributeExists("cbean"));
	}

    @Test
	public void testSetupAddCourseAgain() throws Exception {
		this.mockMvc.perform(get("/setupaddclassagain"))
		.andExpect(status().isOk())
		.andExpect(view().name("BUD003"))
		.andExpect(model().attributeExists("cbean"));
	}

    @Test
	public void testAddCourseValidate() throws Exception {
		this.mockMvc.perform(post("/addclass"))
		.andExpect(status().isOk())
		.andExpect(view().name("BUD003"));	
	}

    @Test
	public void testAddCourse() throws Exception {
        Course cbean = Course.builder()
        .classid("COU012")
        .classname("Java SE")
        .build();
		this.mockMvc.perform(post("/addclass").flashAttr("cbean", cbean))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/setupaddclassagain"));	
	}

	@Test
	public void testCourseNameExists() throws Exception {
		Course cbean = Course.builder()
        .classid("COU012")
        .classname("Java SE")
        .build();
		Mockito.when(courseRepository.existsByClassname(cbean.getClassname())).thenReturn(true);
		this.mockMvc.perform(post("/addclass").flashAttr("cbean", cbean))
		.andExpect(model().attributeExists("classname"))
		.andExpect(status().isOk())
		.andExpect(view().name("BUD003"));	
	}

	@Test
	public void testAutoGenerateId() throws Exception {
		Course cbean = Course.builder()
        .classid("COU012")
        .classname("Java SE")
        .build();
		List<Course> courseList = new ArrayList<>();
		courseList.add(cbean);
		Mockito.when(courseRepository.findAll()).thenReturn(courseList);
		this.mockMvc.perform(get("/setupaddclass").flashAttr("cbean", cbean))
		.andExpect(status().isOk())
		.andExpect(view().name("BUD003"));
	}

	@Test
	public void testAutoGenerateIdAgain() throws Exception {
		Course cbean = Course.builder()
        .classid("COU012")
        .classname("Java SE")
        .build();
		List<Course> courseList = new ArrayList<>();
		courseList.add(cbean);
		Mockito.when(courseRepository.findAll()).thenReturn(courseList);
		this.mockMvc.perform(get("/setupaddclassagain").flashAttr("cbean", cbean))
		.andExpect(status().isOk())
		.andExpect(view().name("BUD003"));
	}
}
