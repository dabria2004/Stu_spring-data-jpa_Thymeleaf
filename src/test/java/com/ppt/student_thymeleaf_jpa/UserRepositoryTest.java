package com.ppt.student_thymeleaf_jpa;

import org.springframework.boot.test.context.SpringBootTest;

import com.ppt.student_thymeleaf_jpa.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import com.ppt.student_thymeleaf_jpa.entity.User;

@SpringBootTest
public class UserRepositoryTest {
    
    @Mock
    UserRepository userRepository;

    @Test
    public void saveTest() {
		User user = User.builder()
        .userid("USR001")
        .username("Mg Mg")
        .email("mgmg@gmail.com")
        .password("mg")
        .conpassword("mg")
        .role("User")
        .build();
        userRepository.save(user);
		verify(userRepository,times(1)).save(user);
	}

    @Test
	public void TestFindAll() {
		List<User> list=new ArrayList<User>();
		User user1 = User.builder()
        .userid("USR001")
        .username("Mg Mg")
        .email("mgmg@gmail.com")
        .password("mg")
        .conpassword("mg")
        .role("User")
        .build();
		User user2 = User.builder()
        .userid("USR001")
        .username("Mg Mg")
        .email("mgmg@gmail.com")
        .password("mg")
        .conpassword("mg")
        .role("User")
        .build();
		list.add(user1);
		list.add(user2);
		when(userRepository.findAll()).thenReturn(list);
		List<User> userList = userRepository.findAll();
		assertEquals(2, userList.size());
		verify(userRepository, times(1)).findAll();
	}

    @Test
	public void deleteTest() {
        userRepository.deleteById("USR001");
        verify(userRepository,times(1)).deleteById("USR001");
	}

    @Test
    public void FindByUserIdTest(){
        User user = User.builder()
        .userid("USR001")
        .username("PPT")
        .email("ppt@gmail.com")
        .password("ppt")
        .conpassword("ppt")
        .role("User")
        .build();
		when(userRepository.findByUserid("USR001")).thenReturn(user);
		User getUser=userRepository.findByUserid("USR001");
		assertEquals("USR001", getUser.getUserid());
		assertEquals("PPT", getUser.getUsername());
        assertEquals("ppt@gmail.com", getUser.getEmail());
        assertEquals("ppt", getUser.getPassword());
        assertEquals("ppt", getUser.getConpassword());
        assertEquals("User", getUser.getRole());
    }

    @Test
    public void checkIfEmailExists() {
        Boolean check = true;
        when(userRepository.existsByEmail("phyuthin@gmail.com")).thenReturn(check);
        Boolean b = userRepository.existsByEmail("phyuthin@gmail.com");
        assertEquals(true, b);
        verify(userRepository, times(1)).existsByEmail("phyuthin@gmail.com");
    }

    @Test
    public void TestFindByIdContainingOrNameContaining(){
        List<User> list=new ArrayList<User>();
		User user1 = User.builder()
        .userid("USR001")
        .username("Mg Mg")
        .email("mgmg@gmail.com")
        .password("mg")
        .conpassword("mg")
        .role("User")
        .build();
		User user2 = User.builder()
        .userid("USR002")
        .username("Ag Ag")
        .email("agag@gmail.com")
        .password("ag")
        .conpassword("ag")
        .role("User")
        .build();
		list.add(user1);
		list.add(user2);
        when(userRepository.findByUseridContainingOrUsernameContaining("USR001", "Ag Ag")).thenReturn(list);
        List<User> userList = userRepository.findByUseridContainingOrUsernameContaining("USR001", "Ag Ag");
        assertEquals(2, userList.size());
        verify(userRepository, times(1)).findByUseridContainingOrUsernameContaining("USR001", "Ag Ag");
    }

    // when(userRepository.findAll()).thenReturn(list);
	// 	List<User> userList = userRepository.findAll();
	// 	assertEquals(2, userList.size());
	// 	verify(userRepository, times(1)).findAll();
}
