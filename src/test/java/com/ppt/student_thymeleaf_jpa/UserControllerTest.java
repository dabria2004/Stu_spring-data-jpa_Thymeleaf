package com.ppt.student_thymeleaf_jpa;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.ppt.student_thymeleaf_jpa.repository.UserRepository;
import com.ppt.student_thymeleaf_jpa.Service.UserService;
import com.ppt.student_thymeleaf_jpa.entity.User;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
	private MockMvc mockMvc;
	
	@MockBean
	UserService userService;
	
	@MockBean
	UserRepository userRepository;

    @Test
	public void testsetupadduser() throws Exception {
		this.mockMvc.perform(get("/setupadduser"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR001"))
		.andExpect(model().attributeExists("user"));
	}

    @Test
	public void testAddUserValidate() throws Exception {
		this.mockMvc.perform(post("/adduser"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR001"));	
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
		this.mockMvc.perform(post("/adduser").flashAttr("user", user))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/setupadduseragain"));	
	}

    @Test
	public void testSetupUserSearch() throws Exception {
		this.mockMvc.perform(get("/setupusersearch"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR003"))
		.andExpect(model().attributeExists("searchInfo"));
	}

    @Test
    public void testUserSearch() throws Exception{
        this.mockMvc.perform(post("/usersearch").param("userid", "USR005").param("username", "Stella"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR003"))
		.andExpect(model().attributeExists("searchInfo"));
    }

    @Test
    public void testSetupUserUpdate() throws Exception {
        this.mockMvc.perform(post("/setupUpdateUser").param("userid", "USR005"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR003"))
		.andExpect(model().attributeExists("searchInfo"));

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
    }

    @Test
	public void testUpdateUserok() throws Exception {
		User user = User.builder()
        .userid("USR005")
        .username("Stella")
        .email("stella2004@gmail.com")
        .password("ss")
        .conpassword("ss")
        .role("User")
        .build();
		this.mockMvc.perform(post("/updateuser").flashAttr("user", user))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/setupusersearch"));	
	}

    @Test
		public void testDeleteUser() throws Exception {
			this.mockMvc.perform(get("/deleteuser").param("id","USR001"))
			.andExpect(status().is(302))
			.andExpect(redirectedUrl("/setupusersearch"));	
		}

}
