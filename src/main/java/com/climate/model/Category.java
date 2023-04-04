package com.climate.model;

import javax.persistence.*;

@Entity
@Table(name = "tbl_category")
public class Category {
    @Id
    @Column(name = "category_id")
    private Integer cid;

    @Column(name = "category")
    private String categoryName;

    @Column(name = "category_weight")
    private Double categoryWeight;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Double getCategoryWeight() {
        return categoryWeight;
    }

    public void setCategoryWeight(Double categoryWeight) {
        this.categoryWeight = categoryWeight;
    }

    @Override
    public String toString() {
        return "Category{" +
                "cid='" + cid + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", categoryWeight='" + categoryWeight + '\'' +
                '}';
    }
}
