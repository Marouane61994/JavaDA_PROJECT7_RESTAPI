package com.nnk.springboot.integration.controller;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(username = "user", roles = "USER")
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TradeRepository tradeRepository;

    private Trade existingTrade;

    @BeforeEach
    public void setup() {
        tradeRepository.deleteAll();

        existingTrade = new Trade();
        existingTrade.setAccount("InitialAccount");
        existingTrade.setType("InitialType");
        existingTrade.setBuyQuantity(100.0);
        existingTrade = tradeRepository.save(existingTrade);
    }

    @Test
    public void testValidate_withValidTrade_shouldRedirectToList() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("account", "TestAccount")
                        .param("type", "TestType")
                        .param("buyQuantity", "250.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    public void testValidate_withInvalidTrade_shouldReturnToForm() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("account", "")
                        .param("type", "TestType")
                        .param("buyQuantity", "100.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    public void testUpdate_withValidTrade_shouldRedirectToList() throws Exception {
        mockMvc.perform(post("/trade/update/" + existingTrade.getTradeId())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("account", "UpdatedAccount")
                        .param("type", "UpdatedType")
                        .param("buyQuantity", "300.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    public void testUpdate_withInvalidTrade_shouldReturnToForm() throws Exception {
        mockMvc.perform(post("/trade/update/" + existingTrade.getTradeId())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("account", "")
                        .param("type", "UpdatedType")
                        .param("buyQuantity", "100.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));
    }
}
