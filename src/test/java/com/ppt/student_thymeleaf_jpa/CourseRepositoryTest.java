package com.ppt.student_thymeleaf_jpa;

import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import com.ppt.student_thymeleaf_jpa.repository.CourseRepository;
import com.ppt.student_thymeleaf_jpa.entity.Course;

@SpringBootTest
public class CourseRepositoryTest {
    
    @Mock
	CourseRepository courseRepository;

    @Test
	public void TestFindAll() {
		List<Course> list=new ArrayList<Course>();
		Course course1 = Course.builder()
        .classid("COU021")
        .classname("Java")
        .build();
		Course course2 = Course.builder()
        .classid("COU022")
        .classname("PHP")
        .build();
		Course course3 = Course.builder()
        .classid("COU023")
        .classname("C")
        .build();
		list.add(course1);
		list.add(course2);
		list.add(course3);
		when(courseRepository.findAll()).thenReturn(list);
		List<Course> cList=courseRepository.findAll();
		assertEquals(3,cList.size());
		verify(courseRepository, times(1)).findAll();
	}

    @Test
	public void saveTest() {
		Course course = Course.builder()
        .classid("COU078")
        .classname("OJT")
        .build();
		courseRepository.save(course);
		verify(courseRepository,times(1)).save(course);
	}

    @Test
    public void checkIfClassnameExists() {
        Boolean check = true;
        when(courseRepository.existsByClassname("Java")).thenReturn(check);
        Boolean b = courseRepository.existsByClassname("Java");
        assertEquals(true, b);
        verify(courseRepository, times(1)).existsByClassname("Java");
    }
}

//         import static org.junit.jupiter.api.Assertions.assertTrue;
//          String className = "Java";
//         Course course = Course.builder()
//         .classid("COU012")
//         .classname(className)
//         .build();
//         courseRepository.save(course);
//         boolean excepted = courseRepository.existsByClassname(className);
//         assertThat(excepted).isTrue();
