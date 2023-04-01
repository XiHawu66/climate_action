package com.climate.service;

import com.climate.model.Household;
import com.climate.model.repository.HouseholdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseholdService {
    @Autowired
    private HouseholdRepository householdRepository;

    public Household findById(Integer pid) {
        return householdRepository.findById(pid).get();
    }

    public List<Household> findAll() {
        return householdRepository.findAll();
    }
}
