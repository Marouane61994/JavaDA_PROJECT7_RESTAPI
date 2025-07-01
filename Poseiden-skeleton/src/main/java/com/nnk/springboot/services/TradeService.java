package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Data
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
     */
    public void saveTrade(Trade trade) {
         tradeRepository.save(trade);
    }

    /**
     * Updates an existing trade.
     *
     * @param id           the trade object with updated data
     * @param updatedTrade the updated BidList entity.
     */
    public void updateTrade(Integer id, Trade updatedTrade) {
       updatedTrade.setTradeId(id);
        tradeRepository.save(updatedTrade);
    }

    /**
     * Deletes a trade by ID.
     *
     * @param id the trade ID
     * @throws IllegalArgumentException if trade ID does not exist
     */
    public void deleteTrade(Integer id) {
        tradeRepository.deleteById(id);
    }

}
