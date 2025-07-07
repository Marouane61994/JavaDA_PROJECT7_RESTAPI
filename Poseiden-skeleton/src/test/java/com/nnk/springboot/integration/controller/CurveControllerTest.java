package com.nnk.springboot.integration.controller;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(username = "user", roles = "USER")
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class CurveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CurvePointRepository curvePointRepository;

    @BeforeEach
    public void setup() {
        curvePointRepository.deleteAll();
    }

    @Test
    public void testPostValidate_withValidCurvePoint_shouldRedirect() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("curveId", "10")
                        .param("term", "10.5")
                        .param("value", "20.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        assertThat(curvePointRepository.findAll())
                .anyMatch(c -> c.getCurveId() == 10 && c.getTerm() == 10.5 && c.getValue() == 20.0);
    }

    @Test
    public void testPostValidate_withErrors_shouldReturnForm() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("curveId", "") // Invalide
                        .param("term", "10.5")
                        .param("value", "20.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeHasFieldErrors("curvePoint", "curveId"));


        assertThat(curvePointRepository.findAll()).isEmpty();
    }

    @Test
    public void testPostUpdate_withValidCurvePoint_shouldRedirect() throws Exception {

        CurvePoint curve = new CurvePoint();
        curve.setCurveId(1);
        curve.setTerm(10.0);
        curve.setValue(20.0);
        curve = curvePointRepository.save(curve);

        mockMvc.perform(post("/curvePoint/update/" + curve.getId())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("curveId", "2")
                        .param("term", "12.5")
                        .param("value", "25.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));


        CurvePoint updated = curvePointRepository.findById(curve.getId()).orElseThrow();
        assertThat(updated.getCurveId()).isEqualTo(2);
        assertThat(updated.getTerm()).isEqualTo(12.5);
        assertThat(updated.getValue()).isEqualTo(25.0);
    }

    @Test
    public void testPostUpdate_withErrors_shouldReturnForm() throws Exception {

        CurvePoint curve = new CurvePoint();
        curve.setCurveId(1);
        curve.setTerm(10.0);
        curve.setValue(20.0);
        curve = curvePointRepository.save(curve);


        mockMvc.perform(post("/curvePoint/update/" + curve.getId())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("curveId", "") // Invalide
                        .param("term", "12.5")
                        .param("value", "25.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeHasFieldErrors("curvePoint", "curveId"));



        CurvePoint unchanged = curvePointRepository.findById(curve.getId()).orElseThrow();
        assertThat(unchanged.getCurveId()).isEqualTo(1);
        assertThat(unchanged.getTerm()).isEqualTo(10.0);
        assertThat(unchanged.getValue()).isEqualTo(20.0);
    }
}