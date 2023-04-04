package com.climate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_unit_conversion")
public class UnitConversion {
    @Id
    @Column(name = "fuel")
    private String fuel;

    @Column(name = "units")
    private String units;

    @Column(name = "co2_kg")
    private Double co2Kg;

    @Column(name = "unit_cost")
    private Double unitCost;

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public Double getCo2Kg() {
        return co2Kg;
    }

    public void setCo2Kg(Double co2Kg) {
        this.co2Kg = co2Kg;
    }

    public Double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }

    @Override
    public String toString() {
        return "UnitConversion{" +
                "fuel='" + fuel + '\'' +
                ", units='" + units + '\'' +
                ", co2Kg=" + co2Kg +
                ", unitCost=" + unitCost +
                '}';
    }
}
