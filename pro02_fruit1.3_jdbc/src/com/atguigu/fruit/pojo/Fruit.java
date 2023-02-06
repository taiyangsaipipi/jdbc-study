package com.atguigu.fruit.pojo;

public class Fruit {
    private Integer fid;
    private String fname;
    private Integer price;
    private  Integer fcount;
    private String remark;

    public Fruit() {
    }

    public Fruit(Integer fid, String fname, Integer price, Integer fcount, String remark) {
        this.fid = fid;
        this.fname = fname;
        this.price = price;
        this.fcount = fcount;
        this.remark = remark;
    }

    public Integer getFid() {
        return fid;
    }

    public String getFname() {
        return fname;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getFcount() {
        return fcount;
    }

    public String getRemark() {
        return remark;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setFcount(Integer fcount) {
        this.fcount = fcount;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return fid+"\t\t"+fname+"\t\t"+price+"\t\t"+fcount+"\t\t"+remark;
    }
}
