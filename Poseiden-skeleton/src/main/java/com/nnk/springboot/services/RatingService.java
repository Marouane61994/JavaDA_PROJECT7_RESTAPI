package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for managing {@link Rating} entities.
 * Provides business logic for CRUD operations on Rating objects.
 */
@Service
public class RatingService {

    @Autowired
    private  RatingRepository ratingRepository;

    /**
     * Retrieves all ratings from the database.
     *
     * @return a list of all {@link Rating} entities
     */
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    /**
     * Retrieves a single rating by its ID.
     *
     * @param id the ID of the rating to retrieve
     * @return the found {@link Rating}
     * @throws IllegalArgumentException if the rating is not found
     */
    public Rating findById(Integer id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rating Id: " + id));
    }

    /**
     * Saves a new rating to the database.
     *
     * @param rating the {@link Rating} entity to save
     * @return the saved {@link Rating} entity
     */
    public Rating save(Rating rating) {
        return ratingRepository.save(rating);
    }

    /**
     * Updates an existing rating with the provided data.
     *
     * @param id the ID of the rating to update
     * @param updatedRating the updated rating information
     * @return the updated {@link Rating} entity
     */
    public Rating update(Integer id, Rating updatedRating) {
        Rating existing = findById(id);
        existing.setMoodysRating(updatedRating.getMoodysRating());
        existing.setSandPRating(updatedRating.getSandPRating());
        existing.setFitchRating(updatedRating.getFitchRating());
        existing.setOrderNumber(updatedRating.getOrderNumber());
        return ratingRepository.save(existing);
    }

    /**
     * Deletes a rating from the database by its ID.
     *
     * @param id the ID of the {@link Rating} to delete
     */
    public void delete(Integer id) {
        ratingRepository.deleteById(id);
    }
}


