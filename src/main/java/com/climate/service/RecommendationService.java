package com.climate.service;

import com.climate.dto.HotWaterRecommendationDto;
import com.climate.model.Category;
import com.climate.model.CategoryType;
import com.climate.model.Household;
import com.climate.model.UnitConversion;
import com.climate.model.repository.UnitConversionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendationService {
    @Autowired
    private UnitConversionRepository unitConversionRepository;

    public HotWaterRecommendationDto hotWaterRecommendation(Household household, Category category, CategoryType categoryType, UnitConversion unitConversion) {
        Double qty = household.getKwhPerYear() * category.getCategoryWeight() * categoryType.getTypeWeight();

        String currentCategoryType = categoryType.getTypeName();
        String currentFuel = categoryType.getFuel();
        String suggestedTankSize = household.getSuggestedTankSize();
        Double co2KgReduction = qty * unitConversion.getCo2Kg() * categoryType.getReductionPotential();
        Double gridSaving = qty * categoryType.getReductionPotential() * unitConversion.getUnitCost();
        Double unitSell = unitConversionRepository.findByFuel("electricity").getUnitSell();
        Double unitCost = unitConversionRepository.findByFuel("electricity").getUnitCost();
        Double solarSaving = gridSaving * (unitCost / unitSell);

        return new HotWaterRecommendationDto(currentCategoryType,currentFuel,suggestedTankSize,co2KgReduction,gridSaving,unitSell,solarSaving);

    }

}
