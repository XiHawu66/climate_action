package com.climate.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class HotWaterRecommendationDto implements Serializable {
    private String currentCategoryType;

    private String currentFuel;

    private String suggestedTankSize;

    private Double co2KgReduction;

    private Double gridSaving;

    private Double exportPrice;

    private Double solarSaving;

    public HotWaterRecommendationDto(String currentCategoryType, String currentFuel, String suggestedTankSize, Double co2KgReduction, Double gridSaving, Double exportPrice, Double solarSaving) {
        this.currentCategoryType = currentCategoryType;
        this.currentFuel = currentFuel;
        this.suggestedTankSize = suggestedTankSize;
        this.co2KgReduction = co2KgReduction;
        this.gridSaving = gridSaving;
        this.exportPrice = exportPrice;
        this.solarSaving = solarSaving;
    }
}
