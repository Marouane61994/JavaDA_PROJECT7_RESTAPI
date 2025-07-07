package com.nnk.springboot.integration.controller;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(username = "user", roles = "USER")
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RuleNameRepository ruleNameRepository;

    private RuleName existingRule;

    @BeforeEach
    public void setup() {
        ruleNameRepository.deleteAll();

        existingRule = new RuleName();
        existingRule.setName("Rule1");
        existingRule.setDescription("Description1");
        existingRule.setJson("{}");
        existingRule.setTemplate("Template1");
        existingRule.setSqlStr("SELECT *");
        existingRule.setSqlPart("WHERE 1=1");

        existingRule = ruleNameRepository.save(existingRule);
    }

    @Test
    public void testValidate_withValidData_shouldRedirectToList() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                        .param("name", "NewRule")
                        .param("description", "Desc")
                        .param("json", "{}")
                        .param("template", "Template")
                        .param("sqlStr", "SELECT *")
                        .param("sqlPart", "WHERE condition")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @Test
    public void testValidate_withInvalidData_shouldReturnToForm() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                        .param("name", "")
                        .param("description", "Desc")
                        .param("json", "{}")
                        .param("template", "Template")
                        .param("sqlStr", "SELECT *")
                        .param("sqlPart", "WHERE"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    public void testUpdate_withValidData_shouldRedirectToList() throws Exception {
        mockMvc.perform(post("/ruleName/update/" + existingRule.getId())
                        .param("name", "UpdatedName")
                        .param("description", "UpdatedDesc")
                        .param("json", "{}")
                        .param("template", "NewTemplate")
                        .param("sqlStr", "SELECT x")
                        .param("sqlPart", "WHERE x")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @Test
    public void testUpdate_withInvalidData_shouldReturnToForm() throws Exception {
        mockMvc.perform(post("/ruleName/update/" + existingRule.getId())
                        .param("name", "") // invalide
                        .param("description", "Desc")
                        .param("json", "{}")
                        .param("template", "Tpl")
                        .param("sqlStr", "SELECT")
                        .param("sqlPart", "WHERE")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));
    }
}
