package com.nnk.springboot.unitaire;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class BidServiceTest {


    @InjectMocks
    private BidListService bidListService;

    @Mock
    private BidListRepository bidListRepository;

    private BidList bid;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bid = new BidList();
        bid.setBidListId(1);
        bid.setAccount("Account");
        bid.setType("Type");
        bid.setBidQuantity(100.0);
    }


    @Test
    void testFindById_WhenFound() {
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));

        BidList result = bidListService.findById(1);

        assertNotNull(result);
        assertEquals("Type", result.getType());
    }

    @Test
    void testFindById_WhenNotFound() {
        when(bidListRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> bidListService.findById(1));
        assertTrue(exception.getMessage().contains("Invalid BidList ID"));
    }


    @Test
    void testUpdate() {
        bid.setBidQuantity(200.0);
        when(bidListRepository.save(bid)).thenReturn(bid);

        BidList updated = bidListService.update(1, bid);

        assertEquals(200.0, updated.getBidQuantity());
        assertEquals(1, updated.getBidListId());
        verify(bidListRepository, times(1)).save(bid);
    }


}


