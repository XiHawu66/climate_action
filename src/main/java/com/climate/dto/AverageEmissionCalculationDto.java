package com.climate.dto;

import lombok.Data;

@Data
public class AverageEmissionCalculationDto {
    private Double emission;

    public AverageEmissionCalculationDto(Double emission) {
        this.emission = emission;
    }
}
