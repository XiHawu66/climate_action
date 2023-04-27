package com.climate.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "tbl_bedrooms_category")
public class BedroomCategory {
    @Id
    @Column(name = "bedrooms")
    private Integer bid;

    @Column(name = "category_id")
    private String cid;

    @Column(name = "kwh_per_year")
    private Double kwhPerYear;
}
