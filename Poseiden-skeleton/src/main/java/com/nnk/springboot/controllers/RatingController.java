package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
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
 * Controller class responsible for handling web requests related to the {@link Rating} entity.
 * It manages listing, adding, updating, and deleting rating records.
 */
@Controller
public class RatingController {
    @Autowired
    private RatingService ratingService;

    /**
     * Displays the list of all ratings.
     *
     * @param model Spring's UI model to pass data to the view
     * @return the path to the rating list view
     */
    @RequestMapping("/rating/list")
    public String home(Model model) {
        model.addAttribute("ratings", ratingService.findAll());
        return "rating/list";
    }

    /**
     * Displays the form to add a new rating.
     *
     * @param rating a new Rating object to bind form data
     * @return the path to the rating add view
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    /**
     * Validates and saves a new rating if valid.
     *
     * @param rating the rating object populated from form input
     * @param result holds validation results
     * @param model Spring's UI model
     * @return redirect to list view if successful, otherwise returns the form
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/add";
        }
        ratingService.save(rating);
        return "rating/add";
    }

    /**
     * Displays the form to update an existing rating.
     *
     * @param id the ID of the rating to update
     * @param model Spring's UI model to pass the rating to the view
     * @return the path to the rating update view
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Rating rating = ratingService.findById(id);
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    /**
     * Validates and updates an existing rating.
     *
     * @param id the ID of the rating to update
     * @param rating the updated rating object
     * @param result holds validation results
     * @param model Spring's UI model
     * @return redirect to list view if successful, otherwise returns the update form
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/update";
        }
        ratingService.update(id, rating);
        return "redirect:/rating/list";
    }

    /**
     * Deletes a rating by its ID.
     *
     * @param id the ID of the rating to delete
     * @param model Spring's UI model
     * @return redirect to list view after deletion
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        ratingService.delete(id);
        return "redirect:/rating/list";
    }
}
