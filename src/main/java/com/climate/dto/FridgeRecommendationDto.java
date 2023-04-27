package com.climate.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class FridgeRecommendationDto extends RecommendationDto implements Serializable {
    private Double reductionPotential;
    private String suggestedFridgeSize;

    public FridgeRecommendationDto(RecommendationDto recommendationDto, Double reductionPotential, String suggestedFridgeSize) {
        super(recommendationDto);
        this.reductionPotential = reductionPotential;
        this.suggestedFridgeSize = suggestedFridgeSize;
    }

    public FridgeRecommendationDto(String currentCategoryType, Double gridSaving, Double co2KgReduction, Double reductionPotential, String suggestedFridgeSize) {
        super(currentCategoryType, gridSaving, co2KgReduction);
        this.reductionPotential = reductionPotential;
        this.suggestedFridgeSize = suggestedFridgeSize;
    }
}
