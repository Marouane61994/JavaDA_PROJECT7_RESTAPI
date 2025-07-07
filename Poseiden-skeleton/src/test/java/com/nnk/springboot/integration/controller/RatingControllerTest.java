package com.nnk.springboot.integration.controller;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(username = "user", roles = "USER")
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RatingRepository ratingRepository;

    private Rating existingRating;

    @BeforeEach
    void setUp() {
        ratingRepository.deleteAll();
        existingRating = new Rating();
        existingRating.setMoodysRating("Moody");
        existingRating.setSandPRating("S&P");
        existingRating.setFitchRating("Fitch");
        existingRating.setOrderNumber(1);
        existingRating = ratingRepository.save(existingRating);
    }

    @Test
    void testPostValidate_withValidRating_shouldRedirect() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", "Moody A")
                        .param("sandPRating", "S&P A")
                        .param("fitchRating", "Fitch A")
                        .param("orderNumber", "10")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        assertThat(ratingRepository.findAll())
                .anyMatch(r -> r.getMoodysRating().equals("Moody A")
                        && r.getSandPRating().equals("S&P A")
                        && r.getFitchRating().equals("Fitch A")
                        && r.getOrderNumber() == 10);
    }

    @Test
    void testPostValidate_withInvalidRating_shouldReturnToForm() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .param("moodysRating", "") // Invalide
                        .param("sandPRating", "S&P A")
                        .param("fitchRating", "Fitch A")
                        .param("orderNumber", "10")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().attributeHasFieldErrors("rating", "moodysRating"))
                .andExpect(content().string(containsString("MoodysRating is mandatory")));

        assertThat(ratingRepository.findAll()).hasSize(1); // Aucun nouveau rating
    }

    @Test
    void testPostUpdate_withValidRating_shouldRedirect() throws Exception {
        mockMvc.perform(post("/rating/update/" + existingRating.getId())
                        .param("moodysRating", "Updated Moody")
                        .param("sandPRating", "Updated S&P")
                        .param("fitchRating", "Updated Fitch")
                        .param("orderNumber", "5")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        Rating updated = ratingRepository.findById(existingRating.getId()).orElseThrow();
        assertThat(updated.getMoodysRating()).isEqualTo("Updated Moody");
        assertThat(updated.getOrderNumber()).isEqualTo(5);
    }

    @Test
    void testPostUpdate_withInvalidRating_shouldReturnToForm() throws Exception {
        mockMvc.perform(post("/rating/update/" + existingRating.getId())
                        .param("moodysRating", "") // Invalide
                        .param("sandPRating", "S&P")
                        .param("fitchRating", "Fitch")
                        .param("orderNumber", "-1") // Invalide
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeHasFieldErrors("rating", "moodysRating", "orderNumber"))
                .andExpect(content().string(containsString("MoodysRating is mandatory")))
                .andExpect(content().string(containsString("Order must be positive")));

        Rating unchanged = ratingRepository.findById(existingRating.getId()).orElseThrow();
        assertThat(unchanged.getMoodysRating()).isEqualTo("Moody");
        assertThat(unchanged.getOrderNumber()).isEqualTo(1);
    }
}
