package com.climate.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Data
@Entity
@Table(name = "tbl_bedrooms")
public class Bedroom {

    @Id
    @Column(name = "bedrooms")
    private Integer bid;

    @Column(name = "bedroom_label")
    private String bedroomLabel;

    @Column(name = "area_square_metres")
    private Integer areaSquareMetres;

    @Override
    public String toString() {
        return "Bedroom{" +
                "bid=" + bid +
                ", bedroomLabel='" + bedroomLabel + '\'' +
                ", areaSquareMetres=" + areaSquareMetres +
                '}';
    }
}
