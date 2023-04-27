package com.climate.service;

import com.climate.model.BedroomCategory;
import com.climate.model.repository.BedroomCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BedroomCategoryService {

    @Autowired
    private BedroomCategoryRepository bedroomCategoryRepository;

    public BedroomCategory findByBidAndCid(Integer bid, Integer cid) {
        return bedroomCategoryRepository.findBedroomCategoryByBidAndCid(bid, cid);
    }

}
