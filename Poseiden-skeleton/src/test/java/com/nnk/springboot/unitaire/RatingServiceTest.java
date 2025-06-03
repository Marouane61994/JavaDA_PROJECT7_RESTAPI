package com.nnk.springboot.unitaire;


import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RatingServiceTest {

    @InjectMocks
    private RatingService ratingService;

    @Mock
    private RatingRepository ratingRepository;

    private Rating rating;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("Moodys");
        rating.setSandPRating("S&P");
        rating.setFitchRating("Fitch");
        rating.setOrderNumber(10);
    }

    @Test
    void testFindById() {
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

        Rating result = ratingService.findById(1);

        assertNotNull(result);
        assertEquals("Moodys", result.getMoodysRating());
        verify(ratingRepository, times(1)).findById(1);
    }

    @Test
    void testFindById_NotFound() {
        when(ratingRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> ratingService.findById(1));
        assertEquals("Invalid rating Id: 1", exception.getMessage());
    }


    @Test
    void testUpdate() {
        Rating updated = new Rating();
        updated.setMoodysRating("NewMoodys");
        updated.setSandPRating("NewS&P");
        updated.setFitchRating("NewFitch");
        updated.setOrderNumber(20);

        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        Rating result = ratingService.update(1, updated);

        assertEquals("NewMoodys", result.getMoodysRating());
        assertEquals(20, result.getOrderNumber());
        verify(ratingRepository).save(rating);
    }


}
