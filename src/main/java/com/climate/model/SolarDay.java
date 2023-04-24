package com.climate.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "tbl_solar_day")
public class SolarDay {
    @Id
    @Column(name = "hour")
    private Integer hour;

    @Column(name = "hourly_usage")
    private Double hourlyUsage;

    @Column(name = "hourly_generation")
    private Double hourlyGeneration;
}
