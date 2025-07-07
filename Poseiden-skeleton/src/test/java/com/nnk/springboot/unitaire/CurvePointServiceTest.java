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
        curvePoint.setTerm(2.5);
        curvePoint.setValue(100.0);
    }


    @Test
    void testFindById_WhenFound() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

        CurvePoint result = curvePointService.findById(1);

        assertNotNull(result);
        assertEquals(100.0, result.getValue());
    }

    @Test
    void testFindById_WhenNotFound() {
        when(curvePointRepository.findById(1)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class, () -> curvePointService.findById(1));
        assertTrue(ex.getMessage().contains("Invalid CurvePoint ID"));
    }

    @Test
    void testUpdate() {
        curvePoint.setTerm(3.0);
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

        CurvePoint updated = curvePointService.update(1, curvePoint);

        assertEquals(1, updated.getId());
        assertEquals(3.0, updated.getTerm());
        verify(curvePointRepository, times(1)).save(curvePoint);
    }


}
