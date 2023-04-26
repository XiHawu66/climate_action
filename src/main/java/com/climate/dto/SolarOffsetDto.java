package com.climate.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class SolarOffsetDto implements Serializable {
    private Double annualGeneration;

    public SolarOffsetDto(Double annualGeneration) {
        this.annualGeneration = annualGeneration;
    }
}
