package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class for managing {@link RuleName} entities.
 * Provides endpoints for listing, creating, updating, and deleting rule definitions.
 */
@Controller
public class RuleNameController {

    @Autowired
    private RuleNameService ruleNameService;

    /**
     * Displays the list of all rule names.
     *
     * @param model the Spring MVC model
     * @return the view displaying the list of rules
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        model.addAttribute("ruleNames", ruleNameService.findAll());
        return "ruleName/list";
    }

    /**
     * Displays the form to add a new rule.
     *
     * @param bid the RuleName object to bind to the form
     * @return the view name of the add rule form
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        return "ruleName/add";
    }

    /**
     * Validates and saves a new rule to the database.
     *
     * @param ruleName the rule to validate and save
     * @param result   binding result for validation
     * @param model    the Spring MVC model
     * @return redirect to rule list if valid, otherwise reload add form
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ruleName/add";
        }
        ruleNameService.save(ruleName);
        return "ruleName/add";
    }

    /**
     * Displays the update form pre-filled with the rule's current data.
     *
     * @param id    the ID of the rule to update
     * @param model the Spring MVC model
     * @return the view name of the update form
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleName ruleName = ruleNameService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid RuleName Id:" + id));
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    /**
     * Validates and updates an existing rule.
     *
     * @param id       the ID of the rule to update
     * @param ruleName the updated rule data
     * @param result   binding result for validation
     * @param model    the Spring MVC model
     * @return redirect to rule list if valid, otherwise reload update form
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            ruleName.setId(id);
            return "ruleName/update";
        }
        ruleNameService.update(id, ruleName);
        return "redirect:/ruleName/list";
    }

    /**
     * Deletes a rule by its ID.
     *
     * @param id    the ID of the rule to delete
     * @param model the Spring MVC model
     * @return redirect to rule list after deletion
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        ruleNameService.delete(id);
        return "redirect:/ruleName/list";
    }
}
