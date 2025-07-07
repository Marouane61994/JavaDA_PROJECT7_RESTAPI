package com.nnk.springboot.unitaire;


import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TradeServiceTest {

    @InjectMocks
    private TradeService tradeService;

    @Mock
    private TradeRepository tradeRepository;

    private Trade trade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("TestAccount");
        trade.setType("Buy");
        trade.setBuyQuantity(100.0);
    }


    @Test
    void testFindById_Found() {
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        Trade result = tradeService.findById(1);

        assertNotNull(result);
        assertEquals("TestAccount", result.getAccount());
    }

    @Test
    void testFindById_NotFound() {
        when(tradeRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tradeService.findById(1);
        });

        assertEquals("Invalid trade Id:1", exception.getMessage());
    }


    @Test
    void testUpdateTrade() {
        Trade updated = new Trade();
        updated.setAccount("UpdatedAccount");
        updated.setType("Sell");
        updated.setBuyQuantity(200.0);

        tradeService.updateTrade(1, updated);

        assertEquals(1, updated.getTradeId());
        verify(tradeRepository).save(updated);
    }


}