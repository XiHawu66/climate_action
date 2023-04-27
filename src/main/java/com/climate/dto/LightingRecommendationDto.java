package com.climate.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class LightingRecommendationDto extends RecommendationDto implements Serializable {
    private Double reductionPotential;

    public LightingRecommendationDto(RecommendationDto recommendationDto, Double reductionPotential) {
        super(recommendationDto);
        this.reductionPotential = reductionPotential;
    }
    public LightingRecommendationDto(String currentCategoryType, Double gridSaving, Double co2KgReduction, Double reductionPotential) {
        super(currentCategoryType,gridSaving,co2KgReduction);
        this.reductionPotential = reductionPotential;
    }
}
