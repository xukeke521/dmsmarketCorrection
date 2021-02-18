package com.trinamota.main;

import java.io.FileInputStream;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.trinamota.domain.HisData;
import com.trinamota.domain.Payplan;
import com.trinamota.domain.PropertiesData;
import com.trinamota.domain.UserInfo;
import com.trinamota.domain.payinfo;
import com.trinamota.util.sqlutil;
import com.trinamota.util.stringUtil;
import com.trinamota.util.timeutil;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;




public class dcmain {
	
	
	List<Object> payplans = null;
	
	/**
	 * ��ѯ��ͨ�û���Ϣ
	 * @param elecuser
	 * @return
	 */
	public UserInfo getCommonUser(String elecuser){
		
		UserInfo ui = new UserInfo();
		sqlutil squ = new sqlutil(sqlutil.MODEL);
		
		List<Object> hisd = squ.querySQL(UserInfo.class,"select enum elecnum,balance,bill,paytype from commonuser where enum="+elecuser+"");
		
		if(hisd.size()==1)
		{
			ui = (UserInfo) hisd.get(0);
		}
		else
		{
			return null;
		}
		
		return ui;
	}
	
	
	/**
	 * ��ѯ�����û���Ϣ
	 * @param elecuser
	 * @return
	 */
	public UserInfo getElecUser(String elecuser){
		
		UserInfo ui = new UserInfo();
		sqlutil squ = new sqlutil(sqlutil.MODEL);
		
		List<Object> hisd = squ.querySQL(UserInfo.class,"select eunum elecnum,balance,bill,paytype,ecat from elecuser where eunum="+elecuser+"");
		
		if(hisd.size()==1)
		{
			ui = (UserInfo) hisd.get(0);
		}
		else
		{
			return null;
		}
		
		return ui;
	}
	
	
	/**
	 * ��ѯ��ʷ����
	 * @param tablename
	 * @return
	 */
	public List<Object> getBillHis(String tablename){
		
		sqlutil squ = new sqlutil(sqlutil.HIS);
			
		List<Object> hisd = squ.querySQL(HisData.class,"select * from his_"+tablename+" order by stime asc");
			
		
		squ.close();

		return hisd;
	}
	
	/**
	 * ������ʷ����
	 * @param tbname
	 * @param beforebalance
	 * @param behindbalance
	 * @param stime
	 * @param account
	 * @return
	 */
	public boolean updateHis(String tbname,String beforebalance,String behindbalance,String stime,String account){
		
		sqlutil squ = new sqlutil(sqlutil.HIS);
		
		
		String sql = "update his_"+tbname+" set beforebalance="+beforebalance+",behindbalance="+behindbalance+" where stime="+stime+";";
		
		//System.out.println(sql);
		boolean ret = squ.update("update his_"+tbname+" set beforebalance="+beforebalance+",behindbalance="+behindbalance+" where stime="+stime+ " and account="+account+";");
		
		squ.close();
		
		return true;
	}
	
	/**
	 * ��ȡ�����ļ�
	 * @return
	 */
	public List<PropertiesData> getPropertiesData(){
		Properties prop =new Properties();
		
		List<PropertiesData> pdl = new ArrayList<PropertiesData>();
		InputStream inputStream =null;
		
		try{
			 prop.load(new FileInputStream("dcmain.properties"));
			 
			 
			 
			 String anlh = prop.getProperty("anlh");
			 String [] anlhl = anlh.split(",");
			 
			 String elecnumPayinfo = prop.getProperty("elecnumPayinfo");
			 String [] elecnumPayinfol = elecnumPayinfo.split(",");
			 
			 String initBalance = prop.getProperty("initBalance");
			 String [] initBalancel = initBalance.split(",");
			 
			 String elecnum = prop.getProperty("elecnum");
			 String [] elecnuml = elecnum.split(",");
			 
			 String billelecnum = prop.getProperty("billelecnum");
			 String [] billelecnuml = billelecnum.split(",");
			 
			 if(anlhl.length != elecnumPayinfol.length && anlhl.length != initBalancel.length && anlhl.length != elecnuml.length && anlhl.length != billelecnum.length()){
				 
				 System.out.println("���������ļ��ֶθ���������ͬ������','�ָ��");
				 return null;
			 }
			 
			 for(int i = 0; i < anlhl.length; i++){
				 PropertiesData pd = new PropertiesData();
				 pd.setAnlh(anlhl[i]);
				 pd.setElecnumPayinfo(elecnumPayinfol[i]);
				 pd.setInitBalance(initBalancel[i]);
				 pd.setElecnumDoor(elecnuml[i]);
				 pd.setBillelecnum(billelecnuml[i]);
				 pdl.add(pd);
			 }
			 
		}catch(Exception e){
			 e.printStackTrace();
			 
			 return null;
		}

		return pdl;
	}
	
	
	/**
	 * ��ѯ�ɷѼ�¼
	 * @param elecnum
	 * @param stime
	 * @return
	 */
	public payinfo getPayinfo(String elecnum,String stime){
		
		payinfo pi = new payinfo();
		sqlutil squ = new sqlutil(sqlutil.MODEL);
		String etime = stringUtil.objectToString(Long.valueOf(stime)+86400000);
		
		String sql = "select sum(money) money from payinfo where paystate = 2 and enum='"+elecnum+"' and stime >="+stime+" and stime <"+etime+";";
		List<Object> pil = squ.querySQL(payinfo.class,sql);
			
		
		if(pil.size() < 1)
		{
			return null;
		}
		else
		{
			pi = (payinfo)pil.get(0);
		}
		
		squ.close();
		return pi;
	}
	
	
	/**
	 * ������
	 * @param pd
	 * @return
	 */
	public boolean domain(PropertiesData pd){
		
		
		
		boolean iscommon = true;
		//��ȡ��ͨ�û�
		UserInfo comui = getCommonUser(pd.getElecnumDoor());
		
		if(comui != null){
			iscommon = true;
			System.out.println("������ͨ�û�:"+comui.toString());
		}
		
		//��ȡ�����û�
		UserInfo elecui = getCommonUser(pd.getElecnumDoor());
		
		if(elecui != null){
			iscommon = false;
			System.out.println("��������û�:"+elecui.toString());
		}
		
		
		
		
		
		
		
		payinfo pi = null;
		List<Object> hisl = getBillHis(pd.getAnlh());
		
		String beforebalance = pd.getInitBalance();
		String behindbalance = "0";
		
		for(int i = 0; i < hisl.size(); i++)
		{
			HisData hisd = (HisData) hisl.get(i);
			
			
			//��ȡ�û��Ľɷ���Ϣ
			pi = getPayinfo(pd.getElecnumPayinfo(), hisd.getStime());
						
			
			behindbalance = stringUtil.DoubleToString(Double.valueOf(beforebalance)-Double.valueOf(hisd.getAccount()));
			
			if(pi != null && pi.getMoney() != null)
			{
				behindbalance = stringUtil.DoubleToString(Double.valueOf(behindbalance)+Double.valueOf(pi.getMoney()));
			}
			
			
			updateHis(pd.getAnlh(), beforebalance, behindbalance, hisd.getStime(),hisd.getAccount());
			
			
			beforebalance = behindbalance;
			
			pi = null;
		}
		
		
		
		return true;
	}
	
	
	/**
	 * ��ȡ��Ѽƻ���
	 * @return
	 */
	public boolean getPayPlan(){
		sqlutil squ = new sqlutil(sqlutil.MODEL);
		
		payplans = squ.querySQL(Payplan.class,"select * from payplan where showflag = 1 and execbtime <="+String.valueOf(timeutil.currMSecs())+" order by execbtime desc");
		
		
		squ.close();
		
		if(payplans.size() > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
		
	
	
	public static void main(String[] args){
		dcmain dc = new dcmain();	
		
		UserInfo comui = null;
		UserInfo elecui = null;
		List<PropertiesData> pdl = dc.getPropertiesData();
		
		PropertiesData pd = null;
		
		
		
		if(pdl==null){
			System.out.println("�����ļ�dcmain.properties����������,�޸ĺ�����ִ��");
			return;
		}
		
		
		if(dc.getPayPlan() == false){
			System.out.println("��ȡִ�е�ۼƻ���ʧ��");
			return;
		}
		
		
		
		
		
		
		for(int i= 0; i < pdl.size(); i++)
		{
			pd = pdl.get(i);
			
			
			System.out.println("------------���������ļ��е�"+stringUtil.objectToString(i+1)+"������-----------------");
			System.out.println(pd.toString());
			
			dc.domain(pd);
			
		}		
	}
	

}
