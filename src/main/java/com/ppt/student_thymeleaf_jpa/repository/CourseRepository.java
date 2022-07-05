package com.ppt.student_thymeleaf_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ppt.student_thymeleaf_jpa.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    public boolean existsByClassname(String className);

    //public void deleteStudentByStudentid(String studentid);
}
