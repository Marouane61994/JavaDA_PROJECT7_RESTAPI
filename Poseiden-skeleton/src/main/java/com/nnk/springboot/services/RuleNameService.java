package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public class RuleNameService {

    private final  RuleNameRepository ruleNameRepository;

    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    public List<RuleName> findAll() {
        return ruleNameRepository.findAll();
    }

    public Optional<RuleName> findById(Integer id) {
        return ruleNameRepository.findById(id);
    }

    public RuleName save(@Valid RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    public RuleName update(Integer id, @Valid RuleName updatedRuleName) {
        return ruleNameRepository.findById(id).map(ruleName -> {
            ruleName.setName(updatedRuleName.getName());
            ruleName.setDescription(updatedRuleName.getDescription());
            ruleName.setJson(updatedRuleName.getJson());
            ruleName.setTemplate(updatedRuleName.getTemplate());
            ruleName.setSqlStr(updatedRuleName.getSqlStr());
            ruleName.setSqlPart(updatedRuleName.getSqlPart());
            return ruleNameRepository.save(ruleName);
        }).orElseThrow(() -> new IllegalArgumentException("Invalid RuleName ID: " + id));
    }

    public void delete(Integer id) {
        ruleNameRepository.deleteById(id);
    }

}
