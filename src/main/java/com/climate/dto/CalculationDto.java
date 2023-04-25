package com.climate.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class CalculationDto implements Serializable {

    private Integer CategoryId;

    private Double co2InKg;

    private Double co2Saving;

    private Double moneySaving;

    private Double kwUsage;

    public CalculationDto(Integer categoryId, Double co2InKg, Double co2Saving, Double moneySaving, Double kwUsage) {
        CategoryId = categoryId;
        this.co2InKg = co2InKg;
        this.co2Saving = co2Saving;
        this.moneySaving = moneySaving;
        this.kwUsage = kwUsage;
    }

    @Override
    public String toString() {
        return "CalculationResult{" +
                "CategoryId=" + CategoryId +
                ", co2InKg=" + co2InKg +
                ", co2Saving=" + co2Saving +
                ", moneySaving=" + moneySaving +
                ", kwUsage=" + kwUsage +
                '}';
    }
}
