package com.ppt.student_thymeleaf_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ppt.student_thymeleaf_jpa.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    // List<Student> findDistinctByStudentidContainingOrStudentNameContainingOrAttendCourses_ClassnameContaining(String studentId, String studentName, String courseName);

    public void deleteByStudentid(String studentId);
}
