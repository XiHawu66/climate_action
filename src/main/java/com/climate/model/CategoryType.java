package com.climate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_category_type")
public class CategoryType {
    @Id
    @Column(name = "category_type_id")
    private Integer tid;

    @Column(name = "category_id")
    private Integer cid;

    @Column(name = "category_type")
    private String typeName;

    @Column(name = "market_share")
    private Double marketShare;

    @Column(name = "relative_efficency")
    private Double relativeEfficiency;

    @Column(name = "category_type_weight")
    private Double typeWeight;

    @Column(name = "fuel")
    private String fuel;

    @Column(name = "reduction_potential")
    private Double reductionPotential;

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Double getMarketShare() {
        return marketShare;
    }

    public void setMarketShare(Double marketShare) {
        this.marketShare = marketShare;
    }

    public Double getRelativeEfficiency() {
        return relativeEfficiency;
    }

    public void setRelativeEfficiency(Double relativeEfficiency) {
        this.relativeEfficiency = relativeEfficiency;
    }

    public Double getTypeWeight() {
        return typeWeight;
    }

    public void setTypeWeight(Double typeWeight) {
        this.typeWeight = typeWeight;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public Double getReductionPotential() {
        return reductionPotential;
    }

    public void setReductionPotential(Double reductionPotential) {
        this.reductionPotential = reductionPotential;
    }

    @Override
    public String toString() {
        return "CategoryType{" +
                "tid='" + tid + '\'' +
                ", cid='" + cid + '\'' +
                ", typeName='" + typeName + '\'' +
                ", marketShare='" + marketShare + '\'' +
                ", relativeEfficiency='" + relativeEfficiency + '\'' +
                ", typeWeight='" + typeWeight + '\'' +
                '}';
    }
}
