package com.climate.service;

import com.climate.model.*;

import java.util.ArrayList;
import java.util.List;

public class CalculationService {

    public List<CalculationResult> calculate(List<Category> categories, Household household, Bedroom bedroom, List<CategoryType> categoryTypes, List<UnitConversion> unitConversions) {

        List<CalculationResult> calculationResults = new ArrayList<>();

        for (int i = 0; i < categories.size(); i++) {
            //String categoryName = category.getCategoryName();
            Category category = categories.get(i);
            CategoryType categoryType = categoryTypes.get(i);
            UnitConversion unitConversion = unitConversions.get(i);

            Double categoryWeight = category.getCategoryWeight();
            Integer cid = category.getCid();

            Double qty = household.getKwhPerYear() * categoryWeight *  categoryType.getTypeWeight();

            Double co2InKg = qty * unitConversion.getCo2Kg();
            Double co2Saving = qty * unitConversion.getCo2Kg() * categoryType.getReductionPotential();
            Double moneySaving = qty * unitConversion.getUnitCost() * categoryType.getReductionPotential();

            calculationResults.add(new CalculationResult(cid,co2InKg,co2Saving,moneySaving));

        }



        return calculationResults;

    }
}
