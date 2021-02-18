package com.trinamota.domain;

public class payinfo {
	private String stime;
	private String money;
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	@Override
	public String toString() {
		return "payinfo [stime=" + stime + ", money=" + money + "]";
	}
	
	
}
