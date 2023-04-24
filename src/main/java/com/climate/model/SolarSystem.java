package com.climate.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Data
@Entity
@Table(name = "tbl_solar_system")
public class SolarSystem {
    @Id
    @Column(name = "kw")
    private Integer kw;

    @Column(name = "Initial_investment")
    private Integer initialInvestment;
}
