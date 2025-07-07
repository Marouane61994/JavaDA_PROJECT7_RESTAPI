package com.nnk.springboot.integration.controller;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testPostValidate_shouldCreateUserAndRedirect() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "testuser")
                        .param("fullname", "Test User")
                        .param("password", "Secret123!")
                        .param("role", "USER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        User savedUser = userRepository.findByUsername("testuser");
        assertNotNull(savedUser);
        assertEquals("Test User", savedUser.getFullname());
        assertEquals("USER", savedUser.getRole());
    }


    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testPostValidate_withErrors_shouldReturnForm() throws Exception {
        mockMvc.perform(post("/user/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "")
                        .param("fullname", "")
                        .param("password", "")
                        .param("role", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeHasFieldErrors("user", "username", "fullname", "password", "role"));

        assertThat(userRepository.findAll()).isEmpty();
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testPostUpdate_withValidUser_shouldRedirect() throws Exception {
        User user = new User();
        user.setUsername("olduser");
        user.setFullname("Old Fullname");
        user.setPassword("oldpass");
        user.setRole("USER");
        user = userRepository.save(user);

        mockMvc.perform(post("/user/update/" + user.getId())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "updateduser")
                        .param("fullname", "Updated Fullname")
                        .param("password", "newpassword")
                        .param("role", "ADMIN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        User updated = userRepository.findById(user.getId()).orElseThrow();
        assertEquals("updateduser", updated.getUsername());
        assertEquals("Updated Fullname", updated.getFullname());
        assertEquals("ADMIN", updated.getRole());
    }

}



