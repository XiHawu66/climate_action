package com.climate.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SolarRecommendationDto implements Serializable {
    private Integer kw;

    private Integer initialInvestment;

    private Double payBackPeriod;

    private Double dollarReduction;

    private Double co2Reduction;

    public SolarRecommendationDto(Integer kw, Integer initialInvestment, Double payBackPeriod, Double dollarReduction, Double co2Reduction) {
        this.kw = kw;
        this.initialInvestment = initialInvestment;
        this.payBackPeriod = payBackPeriod;
        this.dollarReduction = dollarReduction;
        this.co2Reduction = co2Reduction;
    }
}
