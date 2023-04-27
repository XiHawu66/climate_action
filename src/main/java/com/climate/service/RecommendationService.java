package com.climate.service;

import com.climate.dto.*;
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

    private RecommendationDto getRecommendationDto(Household household, Category category, CategoryType categoryType, UnitConversion unitConversion) {
        Double qty = household.getKwhPerYear() * category.getCategoryWeight() * categoryType.getTypeWeight();

        String currentCategoryType = categoryType.getTypeName();
        Double co2KgReduction = qty * unitConversion.getCo2Kg() * categoryType.getReductionPotential();
        Double gridSaving = qty * categoryType.getReductionPotential() * unitConversion.getUnitCost();

        return new RecommendationDto(currentCategoryType,co2KgReduction,gridSaving);
    }

    public HotWaterRecommendationDto getHotWaterRecommendationDto(Household household, Category category, CategoryType categoryType, UnitConversion unitConversion) {
        Double qty = household.getKwhPerYear() * category.getCategoryWeight() * categoryType.getTypeWeight();

        RecommendationDto recommendationDto = getRecommendationDto(household,category,categoryType,unitConversion);

        String currentFuel = categoryType.getFuel();
        String suggestedTankSize = household.getSuggestedTankSize();
        Double unitSell = unitConversionRepository.findByFuel("electricity").getUnitSell();
        Double unitCost = unitConversionRepository.findByFuel("electricity").getUnitCost();
        Double solarSaving = qty * categoryType.getReductionPotential() * unitConversion.getUnitCost() * (unitCost / unitSell);

        return new HotWaterRecommendationDto(recommendationDto,currentFuel,suggestedTankSize,unitSell,solarSaving);

    }

    public LightingRecommendationDto getLightingRecommendationDto(Household household, Category category, CategoryType categoryType, UnitConversion unitConversion) {
        RecommendationDto recommendationDto = getRecommendationDto(household,category,categoryType,unitConversion);
        Double potentialSaving = categoryType.getReductionPotential();

        return new LightingRecommendationDto(recommendationDto,potentialSaving);
    }

    public FridgeRecommendationDto getFridgeRecommendationDto(Household household, Category category, CategoryType categoryType, UnitConversion unitConversion) {
        RecommendationDto recommendationDto = getRecommendationDto(household,category,categoryType,unitConversion);

        Double potentialSaving = categoryType.getReductionPotential();
        String suggestedFridgeSize = household.getSuggestedFridgeSize();

        return new FridgeRecommendationDto(recommendationDto,potentialSaving,suggestedFridgeSize);
    }

    public DryerRecommendationDto getDryerRecommendationDto(Household household, Category category, CategoryType categoryType, UnitConversion unitConversion, Integer load) {
        Double qty = load * categoryType.getRelativeEfficiency() * 52;

        String currentCategoryType = categoryType.getTypeName();
        Double gridSaving = qty * categoryType.getReductionPotential() * unitConversion.getUnitCost();
        Double co2KgReduction = qty * unitConversion.getCo2Kg() * categoryType.getReductionPotential();

        Double potentialSaving = categoryType.getReductionPotential();

        return new DryerRecommendationDto(currentCategoryType,gridSaving,co2KgReduction,potentialSaving);
    }

}
