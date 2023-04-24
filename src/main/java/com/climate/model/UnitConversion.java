package com.climate.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Data
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

    @Column(name = "unit_sell")
    private Double unitSell;

    @Override
    public String toString() {
        return "UnitConversion{" +
                "fuel='" + fuel + '\'' +
                ", units='" + units + '\'' +
                ", co2Kg=" + co2Kg +
                ", unitCost=" + unitCost +
                ", unitSell=" + unitSell +
                '}';
    }
}
