package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing {@link RuleName} entities.
 * Provides business logic for CRUD operations on RuleName.
 */
@Data
@Service
public class RuleNameService {

    @Autowired
    private final RuleNameRepository ruleNameRepository;

    /**
     * Retrieves all RuleName entries from the database.
     *
     * @return a list of all RuleName objects
     */
    public List<RuleName> findAll() {
        return ruleNameRepository.findAll();
    }

    /**
     * Retrieves a RuleName by its ID.
     *
     * @param id the ID of the RuleName to find
     * @return an Optional containing the RuleName if found, or empty if not
     */
    public Optional<RuleName> findById(Integer id) {
        return ruleNameRepository.findById(id);
    }

    /**
     * Saves a new RuleName in the database.
     *
     * @param ruleName the RuleName to save
     * @return the saved RuleName object
     */
    public RuleName save(@Valid RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    /**
     * Updates an existing RuleName with new values.
     *
     * @param id               the ID of the RuleName to update
     * @param updatedRuleName the new values for the RuleName
     * @return the updated RuleName
     * @throws IllegalArgumentException if the RuleName with the given ID is not found
     */
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

    /**
     * Deletes a RuleName from the database by its ID.
     *
     * @param id the ID of the RuleName to delete
     */
    public void delete(Integer id) {
        ruleNameRepository.deleteById(id);
    }

}
