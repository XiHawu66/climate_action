package com.climate.service;

import com.climate.model.Bedroom;
import com.climate.model.repository.BedroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BedroomService {

    @Autowired
    private BedroomRepository bedroomRepository;

    public Bedroom findById(Integer bid) {
        return bedroomRepository.findById(bid).get();
    }

    public List<Bedroom> findAll() {
        return bedroomRepository.findAll();
    }
}
