package com.climate.service;

import com.climate.model.SolarSystem;
import com.climate.model.repository.SolarSystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolarSystemService {
    @Autowired
    private SolarSystemRepository solarSystemRepository;

    public List<SolarSystem> getSolarSystems() {
        return solarSystemRepository.findAll();
    }
}
