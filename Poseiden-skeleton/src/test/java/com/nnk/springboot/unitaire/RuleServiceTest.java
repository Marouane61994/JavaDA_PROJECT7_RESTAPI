package com.nnk.springboot.unitaire;


import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RuleServiceTest {

    @InjectMocks
    private RuleNameService ruleNameService;

    @Mock
    private RuleNameRepository ruleNameRepository;

    private RuleName ruleName;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("Rule 1");
        ruleName.setDescription("Description");
        ruleName.setJson("{\"field\":\"value\"}");
        ruleName.setTemplate("Template");
        ruleName.setSqlStr("SELECT *");
        ruleName.setSqlPart("WHERE id = 1");
    }


    @Test
    void testFindById_Found() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));

        Optional<RuleName> result = ruleNameService.findById(1);

        assertTrue(result.isPresent());
        assertEquals("Rule 1", result.get().getName());
    }

    @Test
    void testFindById_NotFound() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());

        Optional<RuleName> result = ruleNameService.findById(1);

        assertFalse(result.isPresent());
    }



    @Test
    void testUpdate_Success() {
        RuleName updated = new RuleName();
        updated.setName("Updated Rule");
        updated.setDescription("Updated Desc");
        updated.setJson("{\"new\":\"data\"}");
        updated.setTemplate("Updated Template");
        updated.setSqlStr("SELECT name");
        updated.setSqlPart("WHERE name='test'");

        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));
        when(ruleNameRepository.save(any(RuleName.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RuleName result = ruleNameService.update(1, updated);

        assertEquals("Updated Rule", result.getName());
        assertEquals("Updated Desc", result.getDescription());
        verify(ruleNameRepository).save(ruleName);
    }

    @Test
    void testUpdate_NotFound() {
        RuleName updated = new RuleName();
        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ruleNameService.update(1, updated);
        });

        assertEquals("Invalid RuleName ID: 1", exception.getMessage());
    }


}

