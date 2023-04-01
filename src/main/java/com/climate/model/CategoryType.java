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
    private String marketShare;

    @Column(name = "relative_efficency")
    private String relativeEfficiency;

    @Column(name = "category_type_weight")
    private String typeWeight;

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

    public String getMarketShare() {
        return marketShare;
    }

    public void setMarketShare(String marketShare) {
        this.marketShare = marketShare;
    }

    public String getRelativeEfficiency() {
        return relativeEfficiency;
    }

    public void setRelativeEfficiency(String relativeEfficiency) {
        this.relativeEfficiency = relativeEfficiency;
    }

    public String getTypeWeight() {
        return typeWeight;
    }

    public void setTypeWeight(String typeWeight) {
        this.typeWeight = typeWeight;
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
