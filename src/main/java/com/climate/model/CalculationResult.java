package com.climate.model;

public class CalculationResult {

    private Integer CategoryId;

    private Double co2InKg;

    private Double co2Saving;

    private Double moneySaving;

    public CalculationResult(Integer categoryId, Double co2InKg, Double co2Saving, Double moneySaving) {
        CategoryId = categoryId;
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

    public Integer getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(Integer categoryId) {
        CategoryId = categoryId;
    }

    @Override
    public String toString() {
        return "CalculationResult{" +
                "CategoryId=" + CategoryId +
                ", co2InKg=" + co2InKg +
                ", co2Saving=" + co2Saving +
                ", moneySaving=" + moneySaving +
                '}';
    }
}
