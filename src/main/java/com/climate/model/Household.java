package com.climate.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Data
@Entity
@Table(name = "tbl_household")
public class Household {
    @Id
    @Column(name = "people")
    private Integer pid;

    @Column(name = "house_hold_size_label")
    private String householdLabel;

    @Column(name = "kwh_per_year")
    private Double KwhPerYear;

    @Column(name = "sugested_tank_size")
    private String suggestedTankSize;

    @Column(name = "sugested_fridge_size")
    private String suggestedFridgeSize;


}
