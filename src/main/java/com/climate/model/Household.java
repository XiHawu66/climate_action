package com.climate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getHouseholdLabel() {
        return householdLabel;
    }

    public void setHouseholdLabel(String householdLabel) {
        this.householdLabel = householdLabel;
    }

    public Double getKwhPerYear() {
        return KwhPerYear;
    }

    public void setKwhPerYear(Double kwhPerYear) {
        KwhPerYear = kwhPerYear;
    }

    @Override
    public String toString() {
        return "Household{" +
                "pid=" + pid +
                ", householdLabel='" + householdLabel + '\'' +
                ", KwhPerYear=" + KwhPerYear +
                '}';
    }
}
