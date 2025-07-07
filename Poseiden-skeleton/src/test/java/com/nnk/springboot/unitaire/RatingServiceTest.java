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
        rating.setMoodysRating("Aaa");
        rating.setSandPRating("AA");
        rating.setFitchRating("AAA");
        rating.setOrderNumber(1);
    }


    @Test
    void testFindById_WhenFound() {
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

        Rating result = ratingService.findById(1);

        assertNotNull(result);
        assertEquals("AA", result.getSandPRating());
    }

    @Test
    void testFindById_WhenNotFound() {
        when(ratingRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> ratingService.findById(1));
        assertTrue(exception.getMessage().contains("Invalid rating Id"));
    }


    @Test
    void testUpdate() {
        Rating updated = new Rating();
        updated.setMoodysRating("Baa");
        updated.setSandPRating("BB");
        updated.setFitchRating("BBB");
        updated.setOrderNumber(2);

        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        Rating result = ratingService.update(1, updated);

        assertEquals("Baa", result.getMoodysRating());
        assertEquals("BB", result.getSandPRating());
        assertEquals(2, result.getOrderNumber());
        verify(ratingRepository, times(1)).save(rating);
    }

}
