package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class CurveController {
    /**
     * Controller that handles CRUD operations for {@link CurvePoint} entities.
     * Maps HTTP requests related to curve points to service calls and views.
     */
    @Autowired
    private CurvePointService curvePointService;

    /**
     * Displays the list of all curve points.
     *
     * @param model Spring model to hold attributes for the view
     * @return the name of the list view
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        model.addAttribute("curvePoints", curvePointService.findAll());
        return "curvePoint/list";
    }

    /**
     * Shows the form to add a new curve point.
     *
     * @param bid a {@link CurvePoint} object used to bind form fields
     * @return the name of the add view
     */
    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        return "curvePoint/add";
    }

    /**
     * Validates and saves a new curve point. If successful, redirects to the list view.
     *
     * @param curvePoint the form-bound {@link CurvePoint} object
     * @param result binding result to capture validation errors
     * @param model Spring model
     * @return the redirect URL or the form view if validation fails
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            curvePointService.save(curvePoint);
            return "redirect:/curvePoint/list";
        }
        return "curvePoint/add";
    }

    /**
     * Displays the update form for a specific curve point.
     *
     * @param id the ID of the curve point to update
     * @param model Spring model to hold the curve point
     * @return the name of the update view
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        CurvePoint curvePoint = curvePointService.findById(id);
        if (curvePoint == null) {
            return "redirect:/curvePoint/list";
        }
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    /**
     * Validates and updates an existing curve point.
     *
     * @param id the ID of the curve point to update
     * @param curvePoint the updated curve point object
     * @param result binding result for validation
     * @param model Spring model
     * @return redirect to the list view or back to the update form if validation fails
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "curvePoint/update";
        }
        curvePointService.update(id, curvePoint);
        return "redirect:/curvePoint/list";
    }

    /**
     * Deletes a curve point based on its ID.
     *
     * @param id the ID of the curve point to delete
     * @param model Spring model
     * @return redirect to the list view after deletion
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {

        curvePointService.delete(id);
        return "redirect:/curvePoint/list";
    }
}
