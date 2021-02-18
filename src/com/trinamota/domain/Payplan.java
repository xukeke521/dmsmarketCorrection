package com.trinamota.domain;

import java.io.Serializable;

public class Payplan  {

    private String id;//����
    private String stime;//���µ��ʱ��
    private String exectype;//ִ������
    private String execbtime;//ִ�п�ʼʱ��
    private String execetime;//ִ�н���ʱ��
    private String price;//һ�ڼ�
    private String firstprice;//��һ���ݵ��
    private String firstarea;//��һ������ֵ
    private String secondprice;//�ڶ����ݵ��
    private String secondarea;//�ڶ�������ֵ
    private String thridprice;//�������ݵ��
    private String fengprice;//����
    private String fengtime;//���ۿ�ʼʱ��
    private String jianprice;//����
    private String jiantime;//���ۿ�ʼʱ��
    private String guprice;//�ȵ��
    private String gutime;//�ȵ�ۿ�ʼʱ��
    private String pingprice;//ƽ���
    private String pingtime;//ƽ��ۿ�ʼʱ��
    private String execusertype; //ִ���û�����

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
