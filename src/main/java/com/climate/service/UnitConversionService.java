package com.climate.service;

import com.climate.model.UnitConversion;
import com.climate.model.repository.UnitConversionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnitConversionService {
    @Autowired
    private UnitConversionRepository unitConversionRepository;

    public UnitConversion findById(String fuel) {
        return unitConversionRepository.findByFuel(fuel);
    }
}
