package com.trinamota.domain;

public class PropertiesData {
	private String anlh;
	private String initBalance;
	private String elecnumPayinfo;
	private String elecnumDoor;
	private String billelecnum;
	@Override
	public String toString() {
		return "PropertiesData [anlh=" + anlh + ", initBalance=" + initBalance + ", elecnumPayinfo=" + elecnumPayinfo
				+ ", elecnumDoor=" + elecnumDoor + ", billelecnum=" + billelecnum + "]";
	}
	public String getBillelecnum() {
		return billelecnum;
	}
	public void setBillelecnum(String billelecnum) {
		this.billelecnum = billelecnum;
	}
	public String getElecnumDoor() {
		return elecnumDoor;
	}
	public void setElecnumDoor(String elecnumDoor) {
		this.elecnumDoor = elecnumDoor;
	}
	public String getAnlh() {
		return anlh;
	}
	public void setAnlh(String anlh) {
		this.anlh = anlh;
	}
	public String getInitBalance() {
		return initBalance;
	}
	public void setInitBalance(String initBalance) {
		this.initBalance = initBalance;
	}
	public String getElecnumPayinfo() {
		return elecnumPayinfo;
	}
	public void setElecnumPayinfo(String elecnumPayinfo) {
		this.elecnumPayinfo = elecnumPayinfo;
	}
	
}
