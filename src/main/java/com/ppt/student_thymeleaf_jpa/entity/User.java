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
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User {

    @Id
    @Column(name = "user_id")
    private String userid;

    @NotEmpty
    @Column(name = "user_name")
    private String username;

    @NotEmpty
    @Column(name = "user_email")
    private String email;

    @NotEmpty
    @Column(name = "user_password")
    private String password;

    @NotEmpty
    @Column(name = "user_conpassword")
    private String conpassword;

    @NotEmpty
    @Column(name = "user_role")
    private String role;
}
