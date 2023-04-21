package com.climate.service;

import com.climate.dto.CalculationDto;
import com.climate.model.*;

public class CalculationService {

    public CalculationDto calculate(Category category, Household household, Bedroom bedroom, CategoryType categoryType, UnitConversion unitConversion) {

        Double qty = household.getKwhPerYear() * category.getCategoryWeight() *  categoryType.getTypeWeight();

        Double co2InKg = qty * unitConversion.getCo2Kg();
        Double co2Saving = categoryType.getReductionPotential() * 100;
        Double moneySaving = qty * unitConversion.getUnitCost() * categoryType.getReductionPotential();

        return new CalculationDto(category.getCid(),co2InKg,co2Saving,moneySaving);

    }
}
