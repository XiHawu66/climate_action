package com.climate.model;

public class CalculationResult {

    private Double co2InKg;

    private Double co2Saving;

    private Double moneySaving;

    public CalculationResult(Double co2InKg, Double co2Saving, Double moneySaving) {
        this.co2InKg = co2InKg;
        this.co2Saving = co2Saving;
        this.moneySaving = moneySaving;
    }

    public Double getCo2InKg() {
        return co2InKg;
    }

    public void setCo2InKg(Double co2InKg) {
        this.co2InKg = co2InKg;
    }

    public Double getCo2Saving() {
        return co2Saving;
    }

    public void setCo2Saving(Double co2Saving) {
        this.co2Saving = co2Saving;
    }

    public Double getMoneySaving() {
        return moneySaving;
    }

    public void setMoneySaving(Double moneySaving) {
        this.moneySaving = moneySaving;
    }

    @Override
    public String toString() {
        return "calculationResult{" +
                "co2InKg='" + co2InKg + '\'' +
                ", co2Saving='" + co2Saving + '\'' +
                ", moneySaving='" + moneySaving + '\'' +
                '}';
    }
}
