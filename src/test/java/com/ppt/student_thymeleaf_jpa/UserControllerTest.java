package com.ppt.student_thymeleaf_jpa;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.ppt.student_thymeleaf_jpa.repository.UserRepository;
import com.ppt.student_thymeleaf_jpa.entity.User;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
	private MockMvc mockMvc;
	
	@Autowired
	MockHttpSession mockHttpSession;
	
	@MockBean
	UserRepository userRepository;

	@BeforeEach
	public void LoginSession() throws Exception{
		User user = User.builder()
		.userid("USR012")
        .username("Junit test")
        .email("unittest@gmail.com")
        .password("unit")
        .conpassword("unit")
        .role("User")
		.build();
		mockHttpSession.setAttribute("userInfo", user);
	}

    @Test
	public void testsetupadduser() throws Exception {
		this.mockMvc.perform(get("/setupadduser").session(mockHttpSession))
		.andExpect(status().isOk())
		.andExpect(view().name("USR001"))
		.andExpect(model().attributeExists("user"));
	}

	@Test
	public void testsetupadduseragain() throws Exception {
		this.mockMvc.perform(get("/setupadduseragain").session(mockHttpSession))
		.andExpect(status().isOk())
		.andExpect(view().name("USR001"))
		.andExpect(model().attributeExists("success"))
		.andExpect(model().attributeExists("user"));
	}

    @Test
	public void testAddUserValidate() throws Exception {
		this.mockMvc.perform(post("/adduser").session(mockHttpSession))
		.andExpect(status().isOk())
		.andExpect(view().name("USR001"))
		.andExpect(model().attributeExists("error"));	
	}

    @Test
	public void testAddUser() throws Exception {
        User user = User.builder()
        .userid("USR012")
        .username("Junit test")
        .email("unittest@gmail.com")
        .password("unit")
        .conpassword("unit")
        .role("User")
        .build();
		this.mockMvc.perform(post("/adduser").flashAttr("user", user).session(mockHttpSession))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/setupadduseragain"));	
	}

    @Test
	public void testSetupUserSearch() throws Exception {
		this.mockMvc.perform(get("/setupusersearch").session(mockHttpSession))
		.andExpect(status().isOk())
		.andExpect(view().name("USR003"))
		.andExpect(model().attributeExists("searchInfo"));
	}

    @Test
    public void testUserSearch() throws Exception{
        this.mockMvc.perform(post("/usersearch").param("userid", "USR005").param("username", "Stella").session(mockHttpSession))
		.andExpect(status().isOk())
		.andExpect(view().name("USR003"))
		.andExpect(model().attributeExists("searchInfo"));
    }

    @Test
    public void testSetupUserUpdate() throws Exception {
    	User user = User.builder()
    	.userid("USR012")
    	.username("Junit test")
    	.email("unittest@gmail.com")
    	.password("unit")
    	.conpassword("unit")
    	.role("User")
   		.build();
    	
        this.mockMvc.perform(get("/setupUpdateUser").param("id", "USR005").flashAttr("user", user).session(mockHttpSession))
		.andExpect(status().isOk())
		.andExpect(view().name("USR002"));   
    }

	@Test
	public void testUpdateUserValidate() throws Exception {
		this.mockMvc.perform(post("/updateuser").session(mockHttpSession))
		.andExpect(status().isOk())
		.andExpect(view().name("USR002"))
		.andExpect(model().attributeExists("error"));
	}

    @Test
	public void testUpdateUser() throws Exception {
		User user = User.builder()
        .userid("USR005")
        .username("Stella")
        .email("stella2004@gmail.com")
        .password("ss")
        .conpassword("ss")
        .role("User")
        .build();
		this.mockMvc.perform(post("/updateuser").flashAttr("user", user).session(mockHttpSession))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/setupusersearch"));
	}

	@Test
	public void testAddUserEmailExists() throws Exception {
		User user = User.builder()
        .userid("USR012")
        .username("Junit test")
        .email("unittest@gmail.com")
        .password("unit")
        .conpassword("unit")
        .role("User")
        .build();
		Mockito.when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);
		this.mockMvc.perform(post("/adduser").flashAttr("user", user).session(mockHttpSession))
		.andExpect(model().attributeExists("email"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR001"));
	}

	@Test
	public void testUpdateUserEmailExists() throws Exception {
		User user1 = User.builder()
        .userid("USR012")
        .username("Junit test")
        .email("unittest11@gmail.com")
        .password("unit")
        .conpassword("unit")
        .role("User")
        .build();

		User user = User.builder()
        .userid("USR012")
        .username("Junit test")
        .email("unittest@gmail.com")
        .password("unit")
        .conpassword("unit")
        .role("User")
        .build();
		Mockito.when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);
		Mockito.when(userRepository.findByUserid(user.getUserid())).thenReturn(user1);
		this.mockMvc.perform(post("/updateuser").flashAttr("user", user).session(mockHttpSession))
		.andExpect(model().attributeExists("error"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR002"));
	}

	@Test
	public void testAddPasswordMatch() throws Exception{
		User user = User.builder()
        .userid("USR005")
        .username("Stella")
        .email("stella2004@gmail.com")
        .password("ss")
        .conpassword("s")
        .role("User")
        .build();
		this.mockMvc.perform(post("/adduser").flashAttr("user", user).session(mockHttpSession))
		.andExpect(status().isOk())
		.andExpect(view().name("USR001"))
		.andExpect(model().attributeExists("password"));
	}

	@Test
	public void testUpdatePasswordMatch() throws Exception{
		User user = User.builder()
        .userid("USR005")
        .username("Stella")
        .email("stella2004@gmail.com")
        .password("ss")
        .conpassword("s")
        .role("User")
        .build();
		this.mockMvc.perform(post("/updateuser").flashAttr("user", user).session(mockHttpSession))
		.andExpect(status().isOk())
		.andExpect(view().name("USR002"))
		.andExpect(model().attributeExists("password"));
	}

    @Test
		public void testDeleteUser() throws Exception {
			this.mockMvc.perform(get("/deleteuser").param("id","USR001").session(mockHttpSession))
			.andExpect(status().is(302))
			.andExpect(redirectedUrl("/setupusersearch"));	
		}

	@Test
	public void testIdAutoGenerate() throws Exception {
		User user = User.builder()
        .userid("USR005")
        .username("Stella")
        .email("stella2004@gmail.com")
        .password("ss")
        .conpassword("ss")
        .role("User")
        .build();
		List<User> userList = new ArrayList<>();
		userList.add(user);
		Mockito.when(userRepository.findAll()).thenReturn(userList);
		this.mockMvc.perform(post("/adduser").flashAttr("user", user).session(mockHttpSession))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/setupadduseragain"));	
	}

	// @Test
	// public void testUserListIsZero() throws Exception {
	// 	List<User> userList = new ArrayList<>();
	// 	Mockito.when(userRepository.findAll()).thenReturn(userList);
	// 	this.mockMvc.perform(post("/usersearch").param("userid", "USR005").param("username", "Stella"))
	// 	.andExpect(status().isOk())
	// 	.andExpect(view().name("USR003"))
	// 	.andExpect(model().attributeExists("searchInfo"));	
	// 	}
	 }

// String userId = "USR005";
        // User user = User.builder()
        // .userid("USR005")
        // .username("Stella")
        // .email("stella2004@gmail.com")
        // .password("ss")
        // .conpassword("ss")
        // .role("User")
        // .build();
        // //Assertions.assertThat(user.getUserid().equals(userId));
        // this.mockMvc.perform(get("/setupUpdateUser").flashAttr("user", user))
	 	// .andExpect(status().isOk())
	 	// .andExpect(view().name("USR002"));	
