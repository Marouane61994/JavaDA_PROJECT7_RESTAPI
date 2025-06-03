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
        bid.setBidQuantity(10d);
    }


    @Test
    void testFindById() {
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));

        BidList result = bidListService.findById(1);

        assertNotNull(result);
        assertEquals("Account", result.getAccount());
        verify(bidListRepository, times(1)).findById(1);
    }



    @Test
    void testUpdate() {
        bid.setBidQuantity(20d);
        when(bidListRepository.save(bid)).thenReturn(bid);

        BidList updated = bidListService.update(1, bid);

        assertEquals(20d, updated.getBidQuantity());
        assertEquals(1, updated.getBidListId());
        verify(bidListRepository, times(1)).save(bid);
    }


}


