package com.trinamota.domain;

public class UserInfo {
	private String elecnum;
	private String balance;
	private String bill;
	private String paytype;
	private String ecat;
	public String getElecnum() {
		return elecnum;
	}
	public void setElecnum(String elecnum) {
		this.elecnum = elecnum;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getBill() {
		return bill;
	}
	public void setBill(String bill) {
		this.bill = bill;
	}
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	public String getEcat() {
		return ecat;
	}
	public void setEcat(String ecat) {
		this.ecat = ecat;
	}
	@Override
	public String toString() {
		return "UserInfo [elecnum=" + elecnum + ", balance=" + balance + ", bill=" + bill + ", paytype=" + paytype
				+ ", ecat=" + ecat + "]";
	}
	
}
