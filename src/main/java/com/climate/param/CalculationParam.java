package com.climate.param;

public class CalculationParam {
    private String cid;
    private String tid;
    private String pid;
    private String bid;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    @Override
    public String toString() {
        return "CalculationRequest{" +
                "cid='" + cid + '\'' +
                ", tid='" + tid + '\'' +
                ", pid='" + pid + '\'' +
                ", bid='" + bid + '\'' +
                '}';
    }
}
