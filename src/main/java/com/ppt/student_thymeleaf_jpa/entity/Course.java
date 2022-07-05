package com.ppt.student_thymeleaf_jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Course {

    @Id
    @Column(name = "class_id")
    private String classid;

    @NotEmpty
    @Column(name = "class_name")
    private String classname;
}
