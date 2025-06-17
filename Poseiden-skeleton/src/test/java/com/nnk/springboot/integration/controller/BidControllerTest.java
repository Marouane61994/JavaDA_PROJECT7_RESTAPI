package com.nnk.springboot.integration.controller;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class BidControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BidListRepository bidListRepository;

    @BeforeEach
    public void setup() {
        bidListRepository.deleteAll();
    }

    @Test
    public void testPostValidate_withValidBidList_shouldRedirect() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("account", "Account Test")
                        .param("type", "Type Test")
                        .param("bidQuantity", "10.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

        assertThat(bidListRepository.findAll())
                .anyMatch(b -> b.getAccount().equals("Account Test") && b.getBidQuantity() == 10.0);
    }

    @Test
    public void testPostValidate_withErrors_shouldReturnForm() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("account", "") // Invalid
                        .param("type", "Type Test")
                        .param("bidQuantity", "10.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeHasFieldErrors("bidList", "account"))
                .andExpect(content().string(containsString("Account is mandatory")));

        assertThat(bidListRepository.findAll()).isEmpty();
    }

    @Test
    public void testPostUpdate_withValidBidList_shouldRedirect() throws Exception {

        BidList bid = new BidList();
        bid.setAccount("Initial Account");
        bid.setType("Initial Type");
        bid.setBidQuantity(5.0);
        bid = bidListRepository.save(bid);


        mockMvc.perform(post("/bidList/update/" + bid.getBidListId())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("account", "Updated Account")
                        .param("type", "Updated Type")
                        .param("bidQuantity", "15.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));


        BidList updated = bidListRepository.findById(bid.getBidListId()).orElseThrow();
        assertThat(updated.getAccount()).isEqualTo("Updated Account");
        assertThat(updated.getType()).isEqualTo("Updated Type");
        assertThat(updated.getBidQuantity()).isEqualTo(15.0);
    }

    @Test
    public void testPostUpdate_withErrors_shouldReturnForm() throws Exception {

        BidList bid = new BidList();
        bid.setAccount("Valid Account");
        bid.setType("Valid Type");
        bid.setBidQuantity(20.0);
        bid = bidListRepository.save(bid);


        mockMvc.perform(post("/bidList/update/" + bid.getBidListId())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("account", "") // Invalid
                        .param("type", "Updated Type")
                        .param("bidQuantity", "20.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeHasFieldErrors("bidList", "account"))
                .andExpect(content().string(containsString("Account is mandatory")));


        BidList unchanged = bidListRepository.findById(bid.getBidListId()).orElseThrow();
        assertThat(unchanged.getAccount()).isEqualTo("Valid Account");
        assertThat(unchanged.getType()).isEqualTo("Valid Type");
    }
}


