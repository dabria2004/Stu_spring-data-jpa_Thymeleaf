package com.ppt.student_thymeleaf_jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ppt.student_thymeleaf_jpa.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
    //List<User> findByIdContainingOrNameContaining(String id, String name);
    //public User findByUserid(String userid);
    public List<User> findByUseridContainingOrUsernameContaining(String userid, String username);
    public User findByUserid(String userid);
    // public List<User> findByUseridContaining(String username);
    // public List<User> findByUsernameContaining(String username);
    public User findByEmail(String email);
    public boolean existsByEmailAndPassword(String email, String password);
    public void deleteByUserid(String userId);
    public boolean existsByEmail(String userEmail);
    //public Optional<User> findById(String userid);
}
