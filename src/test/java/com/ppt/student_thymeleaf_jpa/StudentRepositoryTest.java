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

import com.ppt.student_thymeleaf_jpa.entity.Course;
import com.ppt.student_thymeleaf_jpa.entity.Student;
import com.ppt.student_thymeleaf_jpa.repository.StudentRepository;

@SpringBootTest
public class StudentRepositoryTest {
    
    @Mock
    StudentRepository studentRepository;

    @Test
    public void saveTest() {
        Course course = Course.builder()
        .classid("COU001")
        .classname("Java")
        .build();
    
        List<Course> cList = new ArrayList<>();
        cList.add(course);

		Student student = Student.builder()
        .studentid("STU001")
        .studentname("Ag Ag")
        .dob("2/23/2004")
        .gender("Male")
        .phone("123456789")
        .education("Diploma in IT")
        .attendCourses(cList)
        .build();
        studentRepository.save(student);
		verify(studentRepository,times(1)).save(student);
	}

    @Test
    public void testFindAll(){
        List<Student> sList = new ArrayList<>();
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
        sList.add(student);
        when(studentRepository.findAll()).thenReturn(sList);
		List<Student> userList = studentRepository.findAll();
		assertEquals(1, userList.size());
		verify(studentRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteStudentById(){
        studentRepository.deleteStudentById("STU001");
		verify(studentRepository,times(1)).deleteStudentById("STU001");
    }

    @Test
    public void testFindByStudentId(){
        List<Student> sList = new ArrayList<>();

        Course course = Course.builder()
        .classid("COU001")
        .classname("Java")
        .build();
        List<Course> cList = new ArrayList<>();
        cList.add(course);

		Student student = Student.builder()
        .studentid("STU001")
        .studentname("Ag Ag")
        .dob("2/23/2004")
        .gender("Male")
        .phone("123456789")
        .education("Diploma in IT")
        .attendCourses(cList)
        .build();
        sList.add(student);
		when(studentRepository.findByStudentid("STU001")).thenReturn(sList);
		List<Student> getStu=studentRepository.findByStudentid("STU001");
        for(Student stu : getStu){
            assertEquals("STU001", stu.getStudentid());
            assertEquals("Ag Ag", stu.getStudentname());
            assertEquals("2/23/2004", stu.getDob());
            assertEquals("Male", stu.getGender());
            assertEquals("123456789", stu.getPhone());
            assertEquals("Diploma in IT", stu.getEducation());
        }
    }
	
    @Test
    public void testFindByOneExists() {
        List<Student> sList = new ArrayList<>();

        Course course = Course.builder()
        .classid("COU001")
        .classname("Java")
        .build();
        List<Course> cList = new ArrayList<>();
        cList.add(course);

		Student student = Student.builder()
        .studentid("STU001")
        .studentname("Ag Ag")
        .dob("2/23/2004")
        .gender("Male")
        .phone("123456789")
        .education("Diploma in IT")
        .attendCourses(cList)
        .build();
        sList.add(student);
        when(studentRepository.findDistinctByStudentidContainingOrStudentnameContainingOrAttendCourses_ClassnameContaining("STU001", "PPT", "Java"))
                .thenReturn(sList);
        List<Student> stuList = studentRepository.findDistinctByStudentidContainingOrStudentnameContainingOrAttendCourses_ClassnameContaining("STU001", "PPT", "Java");
        assertEquals(1, stuList.size());
        verify(studentRepository, times(1)).findDistinctByStudentidContainingOrStudentnameContainingOrAttendCourses_ClassnameContaining("STU001", "PPT", "Java");
    }
}
