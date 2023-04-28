package com.climate.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class HeatingRecommendationDto extends RecommendationDto implements Serializable {
    private String currentFuel;

    public HeatingRecommendationDto(RecommendationDto recommendationDto, String currentFuel) {
        super(recommendationDto);
        this.currentFuel = currentFuel;
    }

    public HeatingRecommendationDto(String currentCategoryType, Double gridSaving, Double co2KgReduction, String currentFuel) {
        super(currentCategoryType, gridSaving, co2KgReduction);
        this.currentFuel = currentFuel;
    }
}
