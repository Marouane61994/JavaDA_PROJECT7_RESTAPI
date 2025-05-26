package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private  RatingRepository ratingRepository;

    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    public Rating findById(Integer id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rating Id: " + id));
    }

    public Rating save(Rating rating) {
        return ratingRepository.save(rating);
    }

    public Rating update(Integer id, Rating updatedRating) {
        Rating existing = findById(id);
        existing.setMoodysRating(updatedRating.getMoodysRating());
        existing.setSandPRating(updatedRating.getSandPRating());
        existing.setFitchRating(updatedRating.getFitchRating());
        existing.setOrderNumber(updatedRating.getOrderNumber());
        return ratingRepository.save(existing);
    }

    public void delete(Integer id) {
        ratingRepository.deleteById(id);
    }
}


