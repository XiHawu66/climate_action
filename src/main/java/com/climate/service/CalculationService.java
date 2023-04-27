package com.climate.service;

import com.climate.dto.CalculationDto;
import com.climate.dto.SolarOffsetDto;
import com.climate.dto.SolarRecommendationDto;
import com.climate.model.*;
import com.climate.model.repository.SolarConstRepository;
import com.climate.model.repository.SolarDayRepository;
import com.climate.model.repository.SolarSystemRepository;
import com.climate.model.repository.UnitConversionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalculationService {
    @Autowired
    private SolarSystemRepository solarSystemRepository;

    @Autowired
    private SolarConstRepository solarConstRepository;

    @Autowired
    private SolarDayRepository solarDayRepository;

    @Autowired
    private UnitConversionRepository unitConversionRepository;

    public CalculationDto calculate(Category category, Household household, Bedroom bedroom, CategoryType categoryType, UnitConversion unitConversion, String dryerLoad) {

        Double qty = 0.0;

        if (category.getCid() == 7) {
            qty = Integer.parseInt(dryerLoad) * categoryType.getRelativeEfficiency() * 52;
        }
        else {
            qty = household.getKwhPerYear() * category.getCategoryWeight() *  categoryType.getTypeWeight();
        }

        Double co2InKg = qty * unitConversion.getCo2Kg();
        Double co2Saving = categoryType.getReductionPotential() * 100;
        Double moneySaving = qty * unitConversion.getUnitCost() * categoryType.getReductionPotential();
        Double kwUsage = 0.0;

        if (categoryType.getFuel().equals("electricity")) {
            kwUsage += qty;
        }

        return new CalculationDto(category.getCid(),co2InKg,co2Saving,moneySaving,kwUsage);

    }

    public List<SolarRecommendationDto> produceSolar(Double totalKwUsage) {

        List<SolarRecommendationDto> solarRecommendationDtos = new ArrayList<>();
        List<SolarSystem> solarSystems = solarSystemRepository.findAll();
        Double sunHoursPerDay = solarConstRepository.findAll().get(0).getSunHoursPerDay();
        List<SolarDay> solarDays = solarDayRepository.findAll();
        UnitConversion unitConversion = unitConversionRepository.findByFuel("electricity");

        for (SolarSystem solarSystem : solarSystems) {

            Integer kw = solarSystem.getKw();
            Integer initialInvestment = solarSystem.getInitialInvestment();
            Double annualGeneration = kw * sunHoursPerDay * 365.25;
            Double annualConsumption = 0.0;
            Double dailyKwUsage = totalKwUsage / 365.25;

            for (SolarDay solarDay : solarDays) {

                if (kw * sunHoursPerDay * solarDay.getHourlyGeneration() > dailyKwUsage * solarDay.getHourlyUsage()) {
                    annualConsumption += dailyKwUsage * solarDay.getHourlyUsage() * 365.25;
                }
                else {
                    annualConsumption += kw * sunHoursPerDay * solarDay.getHourlyGeneration() * 365.25;
                }

            }

            Double payBackPeriod = initialInvestment / (annualConsumption * unitConversion.getUnitCost() + (annualGeneration - annualConsumption) * unitConversion.getUnitSell());
            Double dollarReduction = annualConsumption * unitConversion.getUnitCost() + (annualGeneration - annualConsumption) * unitConversion.getUnitSell();
            Double co2Reduction = unitConversion.getCo2Kg() * annualGeneration;

            solarRecommendationDtos.add(new SolarRecommendationDto(kw,initialInvestment,payBackPeriod,dollarReduction,co2Reduction));

        }

        return solarRecommendationDtos;
    }

    public SolarOffsetDto getAnnualGeneration(Integer kw) {
        Double sunHoursPerDay = solarConstRepository.findAll().get(0).getSunHoursPerDay();

        return new SolarOffsetDto(kw * sunHoursPerDay * 365.25);
    }
}
