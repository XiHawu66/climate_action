package com.climate.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class RecommendationDto implements Serializable {
    private String currentCategoryType;
    private Double gridSaving;
    private Double co2KgReduction;

    public RecommendationDto() {
    }

    public RecommendationDto(RecommendationDto recommendationDto) {
        this.currentCategoryType = recommendationDto.getCurrentCategoryType();
        this.gridSaving = recommendationDto.getGridSaving();
        this.co2KgReduction = recommendationDto.getCo2KgReduction();
    }

    public RecommendationDto(String currentCategoryType, Double gridSaving, Double co2KgReduction) {
        this.currentCategoryType = currentCategoryType;
        this.gridSaving = gridSaving;
        this.co2KgReduction = co2KgReduction;
    }
}
