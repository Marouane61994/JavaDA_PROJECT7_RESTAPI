package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidListService {

    @Autowired
    private BidListRepository bidListRepository;

    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }

    public BidList findById(Integer id) {
        return bidListRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid BidList ID: " + id));
    }

    public BidList save(BidList bid) {
        return bidListRepository.save(bid);
    }

    public BidList update(Integer id, BidList updatedBid) {
        updatedBid.setBidListId(id);
        return bidListRepository.save(updatedBid);
    }

    public void delete(Integer id) {
        bidListRepository.deleteById(id);
    }
}



