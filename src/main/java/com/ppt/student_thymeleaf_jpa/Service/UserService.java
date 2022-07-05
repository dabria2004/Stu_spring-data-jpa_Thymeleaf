package com.ppt.student_thymeleaf_jpa.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppt.student_thymeleaf_jpa.entity.User;
import com.ppt.student_thymeleaf_jpa.repository.UserRepository;

@Service
public class UserService {
@Autowired
UserRepository userRepository;

    public Optional<User> selectUserById(String id) {
        return userRepository.findById(id);
    }
}
