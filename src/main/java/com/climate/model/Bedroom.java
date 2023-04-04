package com.climate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_bedrooms")
public class Bedroom {

    @Id
    @Column(name = "bedrooms")
    private Integer bid;

    @Column(name = "bedroom_label")
    private String bedroomLabel;

    @Column(name = "area_weight")
    private Integer areaWeight;

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getBedroomLabel() {
        return bedroomLabel;
    }

    public void setBedroomLabel(String bedroomLabel) {
        this.bedroomLabel = bedroomLabel;
    }

    public Integer getAreaWeight() {
        return areaWeight;
    }

    public void setAreaWeight(Integer areaWeight) {
        this.areaWeight = areaWeight;
    }

    @Override
    public String toString() {
        return "Bedroom{" +
                "bid=" + bid +
                ", bedroomLabel='" + bedroomLabel + '\'' +
                ", areaWeight=" + areaWeight +
                '}';
    }
}
