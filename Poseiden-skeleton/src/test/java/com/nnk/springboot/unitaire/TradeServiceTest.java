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
        trade.setAccount("Test Account");
        trade.setType("Test Type");
        trade.setBuyQuantity(100.0);
    }


    @Test
    void testFindById_Success() {
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        Trade result = tradeService.findById(1);

        assertEquals("Test Account", result.getAccount());
        verify(tradeRepository, times(1)).findById(1);
    }

    @Test
    void testFindById_NotFound() {
        when(tradeRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> tradeService.findById(1));
        assertEquals("Invalid trade Id:1", exception.getMessage());
    }


    @Test
    void testUpdateTrade_Success() {
        when(tradeRepository.existsById(1)).thenReturn(true);

        tradeService.updateTrade(trade);

        verify(tradeRepository, times(1)).save(trade);
    }

    @Test
    void testUpdateTrade_NotFound() {
        when(tradeRepository.existsById(1)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> tradeService.updateTrade(trade));
        assertEquals("Trade not found with id: 1", exception.getMessage());
    }

    @Test
    void testDeleteTrade_Success() {
        when(tradeRepository.existsById(1)).thenReturn(true);

        tradeService.deleteTrade(1);

        verify(tradeRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteTrade_NotFound() {
        when(tradeRepository.existsById(1)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> tradeService.deleteTrade(1));
        assertEquals("Trade not found with id: 1", exception.getMessage());
    }
}
