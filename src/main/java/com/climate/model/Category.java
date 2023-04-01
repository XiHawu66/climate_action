package com.climate.model;

import javax.persistence.*;

@Entity
@Table(name = "tbl_category")
public class Category {
    @Id
    @Column(name = "category_id")
    private String cid;

    @Column(name = "category")
    private String categoryName;

    @Column(name = "category_weight")
    private String categoryWeight;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryWeight() {
        return categoryWeight;
    }

    public void setCategoryWeight(String categoryWeight) {
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
