package com.climate.service;

import com.climate.model.*;

public class CalculationService {

    public CalculationResult calculate(Category category, Household household, Bedroom bedroom, CategoryType categoryType, UnitConversion unitConversion) {

        Double qty = household.getKwhPerYear() * category.getCategoryWeight() *  categoryType.getTypeWeight();

        Double co2InKg = qty * unitConversion.getCo2Kg();
        Double co2Saving = qty * unitConversion.getUnitCost() * categoryType.getReductionPotential();
        Double moneySaving = qty * unitConversion.getCo2Kg() * categoryType.getReductionPotential();

        return new CalculationResult(co2InKg,co2Saving,moneySaving);

    }
}
