package com.trinamota.domain;

public class HisData {
	private String oid;   //���OID
	private String stime;   //���ʱ��
	private String totalvalue; //����ֵ
	private String value; //��ֵ
	private String prince;//����
	private String account;//�ܼ�
	private String beforebalance;//�ɷ�ǰ�˻����
	private String behindbalance;//�ɷѺ��˻����
	private String beforebill; //�ɷ�ǰ���ɽ��
	private String behindbill; //�ɷѺ���ɽ��
	private String monvalue; //���ۼƵ���
	public String getMonvalue() {
		return monvalue;
	}
	public void setMonvalue(String monvalue) {
		this.monvalue = monvalue;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getTotalvalue() {
		return totalvalue;
	}
	public void setTotalvalue(String totalvalue) {
		this.totalvalue = totalvalue;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getPrince() {
		return prince;
	}
	public void setPrince(String prince) {
		this.prince = prince;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getBeforebalance() {
		return beforebalance;
	}
	public void setBeforebalance(String beforebalance) {
		this.beforebalance = beforebalance;
	}
	public String getBehindbalance() {
		return behindbalance;
	}
	public void setBehindbalance(String behindbalance) {
		this.behindbalance = behindbalance;
	}
	public String getBeforebill() {
		return beforebill;
	}
	public void setBeforebill(String beforebill) {
		this.beforebill = beforebill;
	}
	public String getBehindbill() {
		return behindbill;
	}
	public void setBehindbill(String behindbill) {
		this.behindbill = behindbill;
	}
	@Override
	public String toString() {
		return "HisTable [ oid=" + oid + ", stime=" + stime + ", totalvalue=" + totalvalue
				+ ", value=" + value + ", prince=" + prince + ", account=" + account + ", beforebalance="
				+ beforebalance + ", behindbalance=" + behindbalance + ", beforebill=" + beforebill + ", behindbill="
				+ behindbill + ", monvalue=" + monvalue + "]";
	}
	
	public String valueToString(){
		return  oid + "," + stime + "," + totalvalue
				+ "," + value + "," + prince + "," + account + ","
				+ beforebalance + "," + behindbalance + "," + beforebill + ","
				+ behindbill + "," + monvalue;
	}
	
	public String colToString(){
		return "oid,stime,totalvalue,value,prince,account,beforebalance,behindbalance,beforebill,behindbill,monvalue";
	}
}

