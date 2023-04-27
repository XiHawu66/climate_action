package com.climate.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class DryerRecommendationDto extends RecommendationDto implements Serializable {
    private Double reductionPotential;

    public DryerRecommendationDto(RecommendationDto recommendationDto, Double reductionPotential) {
        super(recommendationDto);
        this.reductionPotential = reductionPotential;
    }
    public DryerRecommendationDto(String currentCategoryType, Double gridSaving, Double co2KgReduction, Double reductionPotential) {
        super(currentCategoryType,gridSaving,co2KgReduction);
        this.reductionPotential = reductionPotential;
    }
}
