package com.climate.service;

import com.climate.dto.*;
import com.climate.model.*;
import com.climate.model.repository.UnitConversionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendationService {
    @Autowired
    private UnitConversionRepository unitConversionRepository;

    private RecommendationDto getRecommendationDto(CategoryType categoryType, UnitConversion unitConversion, Double qty) {

        String currentCategoryType = categoryType.getTypeName();
        Double gridSaving = qty * categoryType.getReductionPotential() * unitConversion.getUnitCost();
        //TODO temporary change the unit conversion
//        Double co2KgReduction = qty * unitConversion.getCo2Kg() * categoryType.getReductionPotential();
        Double co2KgReduction = qty * 0.473 * categoryType.getReductionPotential();

        return new RecommendationDto(currentCategoryType,gridSaving,co2KgReduction);
    }

    public HotWaterRecommendationDto getHotWaterRecommendationDto(Household household, Category category, CategoryType categoryType, UnitConversion unitConversion) {
        Double qty = household.getKwhPerYear() * category.getCategoryWeight() * categoryType.getTypeWeight();

        RecommendationDto recommendationDto = getRecommendationDto(categoryType,unitConversion,qty);

        String currentFuel = categoryType.getFuel();
        String suggestedTankSize = household.getSuggestedTankSize();
        Double unitSell = unitConversionRepository.findByFuel("electricity").getUnitSell();
        Double unitCost = unitConversionRepository.findByFuel("electricity").getUnitCost();
        Double solarSaving = qty * categoryType.getReductionPotential() * unitConversion.getUnitCost() * (unitCost / unitSell);

        return new HotWaterRecommendationDto(recommendationDto,currentFuel,suggestedTankSize,unitSell,solarSaving);

    }

    public LightingRecommendationDto getLightingRecommendationDto(Household household, Category category, CategoryType categoryType, UnitConversion unitConversion) {
        Double qty = household.getKwhPerYear() * category.getCategoryWeight() * categoryType.getTypeWeight();

        RecommendationDto recommendationDto = getRecommendationDto(categoryType,unitConversion,qty);
        Double potentialSaving = categoryType.getReductionPotential();

        return new LightingRecommendationDto(recommendationDto,potentialSaving);
    }

    public FridgeRecommendationDto getFridgeRecommendationDto(Household household, Category category, CategoryType categoryType, UnitConversion unitConversion) {
        Double qty = household.getKwhPerYear() * category.getCategoryWeight() * categoryType.getTypeWeight();

        RecommendationDto recommendationDto = getRecommendationDto(categoryType,unitConversion,qty);

        Double potentialSaving = categoryType.getReductionPotential();
        String suggestedFridgeSize = household.getSuggestedFridgeSize();

        return new FridgeRecommendationDto(recommendationDto,potentialSaving,suggestedFridgeSize);
    }

    public DryerRecommendationDto getDryerRecommendationDto(CategoryType categoryType, UnitConversion unitConversion, Integer load) {
        Double qty = load * categoryType.getRelativeEfficiency() * 52;

        RecommendationDto recommendationDto = getRecommendationDto(categoryType,unitConversion,qty);

        Double potentialSaving = categoryType.getReductionPotential();

        return new DryerRecommendationDto(recommendationDto,potentialSaving);
    }

    public HeatingRecommendationDto getHeatingRecommendationDto(CategoryType categoryType, UnitConversion unitConversion, BedroomCategory bedroomCategory) {
        Double qty = bedroomCategory.getKwhPerYear() * categoryType.getTypeWeight();

        RecommendationDto recommendationDto = getRecommendationDto(categoryType,unitConversion,qty);

        String currentFuel = categoryType.getFuel();

        return new HeatingRecommendationDto(recommendationDto,currentFuel);
    }

}
