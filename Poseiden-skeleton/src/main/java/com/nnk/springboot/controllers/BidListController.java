package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

/**
 * Controller class that handles web requests for BidList operations.
 * Provides endpoints for listing, creating, updating, and deleting BidList entities.
 */
@Controller
public class BidListController {

    @Autowired
    private BidListService bidListService;

    /**
     * Displays the list of all BidList entries.
     *
     * @param model the model to pass attributes to the view.
     * @return the name of the list view template.
     */
    @RequestMapping("/bidList/list")
    public String home(Model model) {
        model.addAttribute("bidLists", bidListService.findAll());
        return "bidList/list";
    }

    /**
     * Displays the form to add a new BidList entry.
     *
     * @param bid a new BidList instance for the form.
     * @return the name of the add form view.
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    /**
     * Validates and saves a new BidList entry.
     *
     * @param bid the BidList entity to validate and save.
     * @param result the binding result to hold validation errors.
     * @param model the model to pass attributes to the view.
     * @return a redirect to the list view if successful, otherwise back to the add form.
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            bidListService.save(bid);
            return "redirect:/bidList/list";
        }
        return "bidList/add";
    }

    /**
     * Displays the form to update an existing BidList entry.
     *
     * @param id the ID of the BidList to update.
     * @param model the model to pass the BidList to the view.
     * @return the name of the update form view.
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidList bid = bidListService.findById(id);
        model.addAttribute("bidList", bid);
        return "bidList/update";
    }

    /**
     * Validates and updates an existing BidList entry.
     *
     * @param id the ID of the BidList to update.
     * @param bidList the updated BidList entity.
     * @param result the binding result to hold validation errors.
     * @param model the model to pass attributes to the view.
     * @return a redirect to the list view if successful, otherwise back to the update form.
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/update";
        }
        bidListService.update(id, bidList);
        return "redirect:/bidList/list";
    }

    /**
     * Deletes a BidList entry by its ID.
     *
     * @param id the ID of the BidList to delete.
     * @param model the model to pass attributes to the view.
     * @return a redirect to the list view after deletion.
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        bidListService.delete(id);
        return "redirect:/bidList/list";
    }
}
