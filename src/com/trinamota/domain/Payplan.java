package com.trinamota.domain;

import java.io.Serializable;

public class Payplan  {

    private String id;//主键
    private String stime;//更新电价时间
    private String exectype;//执行类型
    private String execbtime;//执行开始时间
    private String execetime;//执行结束时间
    private String price;//一口价
    private String firstprice;//第一阶梯电价
    private String firstarea;//第一阶梯阈值
    private String secondprice;//第二阶梯电价
    private String secondarea;//第二阶梯阈值
    private String thridprice;//第三阶梯电价
    private String fengprice;//峰电价
    private String fengtime;//峰电价开始时间
    private String jianprice;//尖电价
    private String jiantime;//尖电价开始时间
    private String guprice;//谷电价
    private String gutime;//谷电价开始时间
    private String pingprice;//平电价
    private String pingtime;//平电价开始时间
    private String execusertype; //执行用户类型

    public String getId() {
        return id;
    }

    public String getExecusertype() {
		return execusertype;
	}

	public void setExecusertype(String execusertype) {
		this.execusertype = execusertype;
	}

	public void setId(String id) {
        this.id = id;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getExectype() {
        return exectype;
    }

    public void setExectype(String exectype) {
        this.exectype = exectype;
    }

    public String getExecbtime() {
        return execbtime;
    }

    public void setExecbtime(String execbtime) {
        this.execbtime = execbtime;
    }

    public String getExecetime() {
        return execetime;
    }

    public void setExecetime(String execetime) {
        this.execetime = execetime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFirstprice() {
        return firstprice;
    }

    public void setFirstprice(String firstprice) {
        this.firstprice = firstprice;
    }

    public String getFirstarea() {
        return firstarea;
    }

    public void setFirstarea(String firstarea) {
        this.firstarea = firstarea;
    }

    public String getSecondprice() {
        return secondprice;
    }

    public void setSecondprice(String secondprice) {
        this.secondprice = secondprice;
    }

    public String getSecondarea() {
        return secondarea;
    }

    public void setSecondarea(String secondarea) {
        this.secondarea = secondarea;
    }

    public String getThridprice() {
        return thridprice;
    }

    public void setThridprice(String thridprice) {
        this.thridprice = thridprice;
    }

    public String getFengprice() {
        return fengprice;
    }

    public void setFengprice(String fengprice) {
        this.fengprice = fengprice;
    }

    public String getFengtime() {
        return fengtime;
    }

    public void setFengtime(String fengtime) {
        this.fengtime = fengtime;
    }

    public String getJianprice() {
        return jianprice;
    }

    public void setJianprice(String jianprice) {
        this.jianprice = jianprice;
    }

    public String getJiantime() {
        return jiantime;
    }

    public void setJiantime(String jiantime) {
        this.jiantime = jiantime;
    }

    public String getGuprice() {
        return guprice;
    }

    public void setGuprice(String guprice) {
        this.guprice = guprice;
    }

    public String getGutime() {
        return gutime;
    }

    public void setGutime(String gutime) {
        this.gutime = gutime;
    }

    public String getPingprice() {
        return pingprice;
    }

    public void setPingprice(String pingprice) {
        this.pingprice = pingprice;
    }

    public String getPingtime() {
        return pingtime;
    }

    public void setPingtime(String pingtime) {
        this.pingtime = pingtime;
    }

    @Override
	public String toString() {
		return "PayPlan [id=" + id + ", stime=" + stime + ", exectype=" + exectype + ", execbtime=" + execbtime
				+ ", execetime=" + execetime + ", price=" + price + ", firstprice=" + firstprice + ", firstarea="
				+ firstarea + ", secondprice=" + secondprice + ", secondarea=" + secondarea + ", thridprice="
				+ thridprice + ", fengprice=" + fengprice + ", fengtime=" + fengtime + ", jianprice=" + jianprice
				+ ", jiantime=" + jiantime + ", guprice=" + guprice + ", gutime=" + gutime + ", pingprice=" + pingprice
				+ ", pingtime=" + pingtime + ", execusertype=" + execusertype + ", getId()=" + getId()
				+ ", getExecusertype()=" + getExecusertype() + ", getStime()=" + getStime() + ", getExectype()="
				+ getExectype() + ", getExecbtime()=" + getExecbtime() + ", getExecetime()=" + getExecetime()
				+ ", getPrice()=" + getPrice() + ", getFirstprice()=" + getFirstprice() + ", getFirstarea()="
				+ getFirstarea() + ", getSecondprice()=" + getSecondprice() + ", getSecondarea()=" + getSecondarea()
				+ ", getThridprice()=" + getThridprice() + ", getFengprice()=" + getFengprice() + ", getFengtime()="
				+ getFengtime() + ", getJianprice()=" + getJianprice() + ", getJiantime()=" + getJiantime()
				+ ", getGuprice()=" + getGuprice() + ", getGutime()=" + getGutime() + ", getPingprice()="
				+ getPingprice() + ", getPingtime()=" + getPingtime() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
}
