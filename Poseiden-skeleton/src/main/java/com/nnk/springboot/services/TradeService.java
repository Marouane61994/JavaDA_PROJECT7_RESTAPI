package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    /**
     * Retrieves all trades from the repository.
     *
     * @return list of all trades
     */
    public List<Trade> findAll() {
        return tradeRepository.findAll();
    }

    /**
     * Finds a trade by its ID.
     *
     * @param id the trade ID
     * @return the trade if found
     * @throws IllegalArgumentException if no trade with given ID exists
     */
    public Trade findById(Integer id) {
        return tradeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
    }

    /**
     * Saves a new trade to the repository.
     *
     * @param trade the trade to save
     * @return the saved trade
     */
    public Trade saveTrade(Trade trade) {
        return tradeRepository.save(trade);
    }

    /**
     * Updates an existing trade.
     *
     * @param trade the trade object with updated data
     * @return the updated trade
     * @throws IllegalArgumentException if trade ID does not exist
     */
    public Trade updateTrade(Trade trade) {
        if (!tradeRepository.existsById(trade.getTradeId())) {
            throw new IllegalArgumentException("Trade not found with id: " + trade.getTradeId());
        }
        return tradeRepository.save(trade);
    }

    /**
     * Deletes a trade by ID.
     *
     * @param id the trade ID
     * @throws IllegalArgumentException if trade ID does not exist
     */
    public void deleteTrade(Integer id) {
        if (!tradeRepository.existsById(id)) {
            throw new IllegalArgumentException("Trade not found with id: " + id);
        }
        tradeRepository.deleteById(id);
    }

}
