package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class that handles business logic related to {@link CurvePoint}.
 * It provides methods to perform CRUD operations via the {@link CurvePointRepository}.
 */
@Service
public class CurvePointService {

    @Autowired
    private CurvePointRepository curvePointRepository;

    /**
     * Retrieves all curve points from the database.
     *
     * @return a list of all {@link CurvePoint} entities
     */
    public List<CurvePoint> findAll() {
        return curvePointRepository.findAll();
    }

    /**
     * Retrieves a specific curve point by its ID.
     *
     * @param id the ID of the curve point
     * @return the found {@link CurvePoint}
     * @throws IllegalArgumentException if the curve point is not found
     */
    public CurvePoint findById(Integer id) {
        return curvePointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid CurvePoint ID: " + id));
    }

    /**
     * Saves a new curve point to the database.
     *
     * @param curvePoint the curve point to save
     * @return the saved {@link CurvePoint}
     */
    public CurvePoint save(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }

    /**
     * Updates an existing curve point by its ID.
     *
     * @param id the ID of the curve point to update
     * @param updatedCurvePoint the updated curve point data
     * @return the updated {@link CurvePoint}
     */
    public CurvePoint update(Integer id, CurvePoint updatedCurvePoint) {
        updatedCurvePoint.setId(id);
        return curvePointRepository.save(updatedCurvePoint);
    }

    /**
     * Deletes a curve point by its ID.
     *
     * @param id the ID of the curve point to delete
     */
    public void delete(Integer id) {
        curvePointRepository.deleteById(id);
    }
}

