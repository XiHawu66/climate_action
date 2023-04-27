package com.climate.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class HotWaterRecommendationDto extends RecommendationDto implements Serializable {
    private String currentFuel;
    private String suggestedTankSize;
    private Double exportPrice;
    private Double solarSaving;

    public HotWaterRecommendationDto(RecommendationDto recommendationDto, String currentFuel, String suggestedTankSize, Double exportPrice, Double solarSaving) {
        super(recommendationDto);
        this.currentFuel = currentFuel;
        this.suggestedTankSize = suggestedTankSize;
        this.exportPrice = exportPrice;
        this.solarSaving = solarSaving;
    }

    public HotWaterRecommendationDto(String currentCategoryType, String currentFuel, String suggestedTankSize, Double co2KgReduction, Double gridSaving, Double exportPrice, Double solarSaving) {
        super(currentCategoryType,gridSaving,co2KgReduction);
        this.currentFuel = currentFuel;
        this.suggestedTankSize = suggestedTankSize;
        this.exportPrice = exportPrice;
        this.solarSaving = solarSaving;
    }
}
