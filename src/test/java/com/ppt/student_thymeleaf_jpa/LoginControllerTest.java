package com.ppt.student_thymeleaf_jpa;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.ppt.student_thymeleaf_jpa.entity.User;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

	@Autowired
	MockHttpSession mockHttpSession;

    @Autowired
	private MockMvc mockMvc;

    @Test
	public void testMenu() throws Exception {
		this.mockMvc.perform(get("/menu"))
		.andExpect(status().isOk())
		.andExpect(view().name("MNU001"));
	}

    @Test
	public void testLogin() throws Exception {
		this.mockMvc.perform(get("/login"))
		.andExpect(status().isOk())
		.andExpect(view().name("LGN001"));
	}

	@Test
	public void testLogout() throws Exception {
		this.mockMvc.perform(get("/logout"))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/"));
	}

	@Test
	public void testLoginFail() {

	}

	@Test
	public void testLoginSuccess() throws Exception {
	//Map<String, Object> sessionObj=new HashMap<String, Object>();
        User user=new User("USR001", "saw mon","sawmon@gmail.com","111","111", "admin");
        mockHttpSession.setAttribute("userInfo", user);
         this.mockMvc.perform(post("/welcomepage").param("email", "sawmon@gmail.com").param("password", "111").session(mockHttpSession))
         .andExpect(status().isOk())
         .andExpect(view().name("MNU001"));
	}
}
//        this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk());

// Boolean check = true;
    //     when(userRepository.existsByEmail("phyuthin@gmail.com")).thenReturn(check);
    //     Boolean b = userRepository.existsByEmail("phyuthin@gmail.com");
    //     assertEquals(true, b);
    //     verify(userRepository, times(1)).existsByEmail("phyuthin@gmail.com");
