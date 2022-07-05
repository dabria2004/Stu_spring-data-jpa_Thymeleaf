package com.ppt.student_thymeleaf_jpa.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ppt.student_thymeleaf_jpa.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    // public void deleteStudentByStudentid(String studentid);
    // public void deleteByStudentid(String studentid);

    @Modifying
    @Transactional
    @Query(value = "delete from student s where s.student_id = ?1", nativeQuery = true)
    void deleteStudentById(String id);

    @Modifying
    @Query(value = "delete from student_course sc where sc.student_id = ?1", nativeQuery = true)
    void deleteCoursesByStudentId(String id);

    // List<Student> findDistinctByStudentidContainingOrStudentNameContainingOrAttendCourses_ClassnameContaining(String studentId, String studentName, String courseName);

    //public void deleteByStudentid(String studentId);
}
