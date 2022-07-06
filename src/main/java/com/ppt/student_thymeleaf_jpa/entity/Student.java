package com.ppt.student_thymeleaf_jpa.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Student {

    @Id
    @Column(name = "student_id")
    private String studentid;

    @Column(name = "student_name")
    @NotEmpty(message = "Name cannot be blank.")
    private String studentname;

    @NotEmpty(message = "Date of birth cannot be blank.")
    private String dob;
    @NotEmpty(message = "Select your gender.")
    private String gender;
    @NotEmpty(message = "Enter your phone number.")
    private String phone;
    @NotEmpty(message = "Education cannot be blank.")
    private String education;

    @NotEmpty(message = "Please select the course(s) you want to attend!!")
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "student_course", 
        joinColumns = @JoinColumn(name = "student_id"), 
        inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> attendCourses;

    public void addCourses(Course course){
        if(attendCourses == null)
        attendCourses = new ArrayList<>();
        attendCourses.add(course);
    }

    public void addCourse(Course course){
       this.attendCourses.add(course);
    }

    public void removeCourse( Course course) {
        this.attendCourses.remove(course);
    }
}
