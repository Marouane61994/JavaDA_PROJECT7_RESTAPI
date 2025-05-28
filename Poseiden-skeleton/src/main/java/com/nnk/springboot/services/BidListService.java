package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class that handles business logic for BidList entities.
 * Provides methods for CRUD operations on bid data.
 */
@Service
public class BidListService {

    @Autowired
    private BidListRepository bidListRepository;

    /**
     * Retrieves all BidList entries from the database.
     *
     * @return a list of all BidList entries.
     */
    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }

    /**
     * Retrieves a BidList entry by its ID.
     *
     * @param id the ID of the BidList to retrieve.
     * @return the BidList entry.
     * @throws IllegalArgumentException if the ID is not found.
     */
    public BidList findById(Integer id) {
        return bidListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid BidList ID: " + id));
    }

    /**
     * Saves a new BidList entry to the database.
     *
     * @param bid the BidList entity to save.
     * @return the saved BidList entity.
     */
    public BidList save(BidList bid) {
        return bidListRepository.save(bid);
    }

    /**
     * Updates an existing BidList entry.
     *
     * @param id the ID of the BidList to update.
     * @param updatedBid the updated BidList entity.
     * @return the updated BidList entity.
     */
    public BidList update(Integer id, BidList updatedBid) {
        updatedBid.setBidListId(id);
        return bidListRepository.save(updatedBid);
    }

    /**
     * Deletes a BidList entry by its ID.
     *
     * @param id the ID of the BidList to delete.
     */
    public void delete(Integer id) {
        bidListRepository.deleteById(id);
    }
}



