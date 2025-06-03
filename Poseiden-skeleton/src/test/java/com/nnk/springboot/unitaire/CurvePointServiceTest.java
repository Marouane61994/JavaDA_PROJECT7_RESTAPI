package com.nnk.springboot.unitaire;


import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CurvePointServiceTest {

    @InjectMocks
    private CurvePointService curvePointService;

    @Mock
    private CurvePointRepository curvePointRepository;

    private CurvePoint curvePoint;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(10);
        curvePoint.setTerm(10d);
        curvePoint.setValue(30d);
    }


    @Test
    void testFindById() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

        CurvePoint result = curvePointService.findById(1);

        assertNotNull(result);
        assertEquals(10, result.getCurveId());
        verify(curvePointRepository, times(1)).findById(1);
    }


    @Test
    void testUpdate() {
        curvePoint.setValue(99d);
        when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);

        CurvePoint updated = curvePointService.update(1, curvePoint);

        assertEquals(99d, updated.getValue());
        assertEquals(1, updated.getId());
        verify(curvePointRepository, times(1)).save(curvePoint);
    }


}
