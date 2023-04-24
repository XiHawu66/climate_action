package com.climate.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "tbl_solar_const")
public class SolarConst {
    @Id
    @Column(name = "sun_hours_per_day")
    private Double sunHoursPerDay;
}
