package com.trinamota.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;


public class timeutil {
	public static int toInt(String s){
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
		}

		return 0;
	}

	public static int toInt(Long s){
		try {
			return s.intValue();
		} catch (Exception e) {
		}

		return 0;
	}

	public static Long toLong(String s){
		try {
			return Long.parseLong(s);
		} catch (Exception e) {
		}

		return 0L;
	}

	public static Double toDouble(String s){
		try {
			return Double.parseDouble(s);
		} catch (Exception e) {
		}

		return 0.0;
	}
	
	public static String toDoubleNotSafly1(String s){
		if(s == null)
			return null;
		try {
			DecimalFormat df = new DecimalFormat("0.00");  
			df.setRoundingMode(RoundingMode.HALF_UP);
			Double d1 = Double.parseDouble(s);
			String s1 = df.format(d1);
			return s1;
		} catch (Exception e) {
		}

		return null;
	}
	
	public static Double toDoubleNotSafly2(String s){
		if(s == null)
			return Double.NaN;

		try {
			DecimalFormat df = new DecimalFormat("0.00");  
			df.setRoundingMode(RoundingMode.HALF_UP);
			Double d1 = Double.parseDouble(s);
			String s1 = df.format(d1);
			return Double.parseDouble(s1);
		} catch (Exception e) {
		}

		return Double.NaN;
	}
	
	public static Double toDoubleRound2(String s, DecimalFormat df){
		try {
			Double d1 = Double.parseDouble(s);
			String s1 = df.format(d1);
			return Double.parseDouble(s1);
		} catch (Exception e) {
		}

		return 0.0;
	}
	
	public static String toDoubleRound1(String s, DecimalFormat df){
		try {
			Double d1 = Double.parseDouble(s);
			String s1 = df.format(d1);
			return s1;
		} catch (Exception e) {
		}

		return "0.0";
	}

	@SuppressWarnings("static-access")
	public static String objToString(Map<String, Object> map, String key, Object obj){
		if(obj == null) {
			if(!map.containsKey(key)) {
				Thread.currentThread().dumpStack();
				System.out.println("error: undefined filed!");
			}
			return "";
		}

		return obj.toString();
	}
	
	public static String objToString(Map<String, Object> map, String key){
		Object obj = map.get(key);
		if(obj ==null) {
			System.out.println("error: undefined filed!");
			return "";
		}

		return obj.toString();
	}
	
	public static String objToString(Object obj){
		if(obj ==null || obj.toString().equals("null")) {
			return "";
		}

		return obj.toString();
	}
	
	public static String objToString(JSONObject jdata, String key){
		Object obj = null;
		try {
			obj = jdata.get(key);
		} catch ( JSONException e) {
			return "";
		}
		
		if(obj ==null || obj.toString().equals("null")) {
			return "";
		}

		return obj.toString();
	}
	
	public static int objToInt(Object obj){
		if(obj ==null || obj.toString().equals("null")) {
			return 0;
		}

		return Integer.parseInt(obj.toString());
	}
	
	public static Long objToLong(Object obj){
		if(obj ==null || obj.toString().equals("null")) {
			return 0L;
		}

		return Long.parseLong(obj.toString());
	}
	
	public static Double objToDouble(Object obj){
		if(obj ==null || obj.toString().equals("null")) {
			return 0.0;
		}

		return Double.parseDouble(obj.toString());
	}

	//TODO 是否是本地测试
	public static boolean isLocal(int version){
		if(version < 3) {
			return true;
		}

		return false;
	}

	public static boolean transform(){
		return true;
	}

	public static long currMSecs(){
		Date curDate = new Date();
		return curDate.getTime();
	}
	
	
	public static long currWholeMSecs(){
		Date curDate = new Date();
		return curDate.getTime()/1000*1000;
	}

	public static long timeMSecs(String datatime, String fmt){
		//System.out.println(datatime+"######"+fmt);
		SimpleDateFormat dateFormat = new SimpleDateFormat(fmt);
		try {
			Date curDate = dateFormat.parse(datatime);
			//System.out.println(curDate);
			return curDate.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return -1;
	}

	public static long timeSecs(String datatime, String fmt){
		SimpleDateFormat dateFormat = new SimpleDateFormat(fmt);
		try {
			Date curDate = dateFormat.parse(datatime);
			return (curDate.getTime()/1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static String timeFormat(long msecs, String fmt) {
		Date date = new Date();
		date.setTime(msecs);
		SimpleDateFormat dateFormat = new SimpleDateFormat(fmt);
		String sdate  = dateFormat.format(date);
		return sdate;
	}
	
	public static long timeToMoonTime(long msecs)
	{
		Date date = new Date();
		date.setTime(msecs);
		date.setDate(1);
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		
		
		return (date.getTime()/1000)*1000;
		
	}
	
	//起始时间到结束时间中有几个月份
	public List<String> MoonsInBandE(String B,String E)
	{
		List<String> moons = new ArrayList<String>();;
		
		long lb = Long.parseLong(B);
		long le = Long.parseLong(E);
		long stime = lb;
		
		while(stime <= le)
		{
			moons.add(stime+"");
			stime = this.addMoon(stime);
		}
		
		return moons;
	}

	//	public static void toTree(tsmysql mod, List<Object> lst) {
	//		try {
	//			List<treenode> lstAll = new ArrayList<treenode>();;
	//			while (mod.rs.next()) {
	//				treenode node = new treenode(mod.rs.getString("hierarchy_id"), mod.rs.getString("description"), null);
	//				node.parid = mod.rs.getString("parent_id");
	//				lstAll.add(node);
	//			}
	//
	//			for (pos = 0; pos < lstAll.size();) {
	//				treenode node = lstAll.get(pos);
	//				lst.add(node);
	//				pos++;
	//				tutil.addNode(mod, lstAll, node, 1);
	//			}
	//		} catch (SQLException e) {
	//			e.printStackTrace();
	//		}
	//	}
	//
	//	private static void addNode(tsmysql mod, List<treenode> lstAll, treenode par, int deep) throws SQLException {
	//		List<Object> lst = null;
	//		for (; pos < lstAll.size();) {
	//			treenode node = lstAll.get(pos);
	//			if(node.parid.equals(par.getId())) {
	//				//System.out.println(node.parid + ":" + par.getId());
	//				if(lst == null) {
	//					lst = new ArrayList<Object>();
	//				}
	//
	//				par.setChildren(lst);
	//				lst.add(node);
	//				pos++;
	//				tutil.addNode(mod, lstAll, node, deep +1);
	//				
	//				if(node.getChildren() == null) {
	//					String pre = node.getId();
	//					pre = pre.substring(0, (deep+1)*2);
	//					String max = "";
	//					for(int i = pre.length(); i < node.getId().length(); i++) {
	//						max += "9";
	//					}
	//					//System.out.println("m:"+node.getId() + ":" + pre+max);
	//					String sql = "select * from equipment where equipment_id > " + node.getId()
	//						+ " and equipment_id <= " + pre + max;
	//					//System.out.println(sql);
	//					mod.query(sql);
	//					while (mod.rs.next()) {
	//						if(node.getChildren() == null)
	//							node.setChildren(new ArrayList<Object>());
	//						node.getChildren().add(new treenode(mod.rs.getString("equipment_id"), mod.rs.getString("equipment_name"), null));
	//	
	//					}
	//				}
	//				
	//			} else {
	//				//System.out.println("b:"+node.parid + ":" + par.getId());
	//				break;
	//			}
	//		}
	//	}

	 public static long getDayMillis(long curtime)
	 {
	    	return curtime-getDayStart(curtime);
	 }
	 
	public static void copyFileUsingFileStreams(File source, File dest)
			throws IOException { 

		if(!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}

		InputStream input = null;    
		OutputStream output = null;    
		try {
			input = new FileInputStream(source);
			output = new FileOutputStream(dest);        
			byte[] buf = new byte[1024];        
			int bytesRead;        
			while ((bytesRead = input.read(buf)) > 0) {
				output.write(buf, 0, bytesRead);
			}
		} finally {
			input.close();
			output.close();
		}
	}


	public static long addMoon(long t) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(t);
		int moon = c.get(Calendar.MONTH);
		long time = 0;
		if(moon==11) {
			c.set(Calendar.YEAR, c.get(Calendar.YEAR)+1);
			c.set(Calendar.MONTH, 0);
		} else {
			c.set(Calendar.MONTH, c.get(Calendar.MONTH)+1);
		}
		time = c.getTimeInMillis();
		return time;
	}
	
	public static long jianMoon(long t) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(t);
		int moon = c.get(Calendar.MONTH);
		long time = 0;
		if(moon==0) {
			c.set(Calendar.YEAR, c.get(Calendar.YEAR)-1);
			c.set(Calendar.MONTH, 11);
		} else {
			c.set(Calendar.MONTH, c.get(Calendar.MONTH)-1);
		}
		time = c.getTimeInMillis();
		return time;
	}
	
	public static long addYear(long t, int years) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(t);

		long time = 0;
		c.set(Calendar.YEAR, c.get(Calendar.YEAR)+years);
	
		time = c.getTimeInMillis();
		return time;
	}

	@SuppressWarnings("deprecation")
	public static Double getRandom(double max, double min) {
		Double d =  (Math.random()*(max-min)+min);
		BigDecimal bd = new BigDecimal(d);
		Double tem = bd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		return tem; 
	}
	
	
	public static long getHourStart(long curtime) {
		Calendar cmoonstatr = Calendar.getInstance();
		cmoonstatr.setTimeInMillis(curtime);
		cmoonstatr.set(Calendar.MINUTE, 0);  
		cmoonstatr.set(Calendar.SECOND,0);  
		cmoonstatr.set(Calendar.MILLISECOND, 0); 
		return cmoonstatr.getTimeInMillis();

	}
	
	public static long getDayStart(long curtime) {
		Calendar cmoonstatr = Calendar.getInstance();
		cmoonstatr.setTimeInMillis(curtime);
		cmoonstatr.set(Calendar.HOUR_OF_DAY, 0);  
		cmoonstatr.set(Calendar.MINUTE, 0);  
		cmoonstatr.set(Calendar.SECOND,0);  
		cmoonstatr.set(Calendar.MILLISECOND, 0); 
		return cmoonstatr.getTimeInMillis();

	}
	
	public static long getDayEnd(long curtime) {
		Calendar cmoonend = Calendar.getInstance();
		cmoonend.setTimeInMillis(curtime);
		cmoonend.set(Calendar.HOUR_OF_DAY, 23);  
		cmoonend.set(Calendar.MINUTE, 59);  
		cmoonend.set(Calendar.SECOND,59);  
		cmoonend.set(Calendar.MILLISECOND, 999);  
		return cmoonend.getTimeInMillis();
	}
	
	
	
	public static long getMoonStart(long curtime) {
		Calendar cmoonstatr = Calendar.getInstance();
		cmoonstatr.setTimeInMillis(curtime);
		cmoonstatr.set(Calendar.DAY_OF_MONTH, 1);
		cmoonstatr.set(Calendar.HOUR_OF_DAY, 0);  
		cmoonstatr.set(Calendar.MINUTE, 0);  
		cmoonstatr.set(Calendar.SECOND,0);  
		cmoonstatr.set(Calendar.MILLISECOND, 0); 
		return cmoonstatr.getTimeInMillis();

	}
	
	public static long getMoonEnd(long curtime) {
		Calendar cmoonend = Calendar.getInstance();
		cmoonend.setTimeInMillis(curtime);
		cmoonend.set(Calendar.DAY_OF_MONTH, cmoonend.getActualMaximum(Calendar.DAY_OF_MONTH));  
		cmoonend.set(Calendar.HOUR_OF_DAY, 23);  
		cmoonend.set(Calendar.MINUTE, 59);  
		cmoonend.set(Calendar.SECOND,59);  
		cmoonend.set(Calendar.MILLISECOND, 999);  
		return cmoonend.getTimeInMillis();
	}
	
	public static long getYearStart(long curtime) {
		Calendar cmoonstatr = Calendar.getInstance();
		cmoonstatr.setTimeInMillis(curtime);
		cmoonstatr.set(Calendar.MONTH, 0);
		cmoonstatr.set(Calendar.DAY_OF_MONTH, 1);
		cmoonstatr.set(Calendar.HOUR_OF_DAY, 0);  
		cmoonstatr.set(Calendar.MINUTE, 0);  
		cmoonstatr.set(Calendar.SECOND,0);  
		cmoonstatr.set(Calendar.MILLISECOND, 0); 
		return cmoonstatr.getTimeInMillis();

	}
	
	public static long getYearEnd(long curtime) {
		Calendar cmoonend = Calendar.getInstance();
		cmoonend.setTimeInMillis(curtime);
		cmoonend.set(Calendar.MONTH, 11);
		cmoonend.set(Calendar.DAY_OF_MONTH, cmoonend.getActualMaximum(Calendar.DAY_OF_MONTH));  
		cmoonend.set(Calendar.HOUR_OF_DAY, 23);  
		cmoonend.set(Calendar.MINUTE, 59);  
		cmoonend.set(Calendar.SECOND,59);  
		cmoonend.set(Calendar.MILLISECOND, 999);  
		return cmoonend.getTimeInMillis();
	}
	
	public static String checkMoonSingleton(String moonDatas) {
		if(moonDatas == null || moonDatas.equals(""))
			return "";
		
		String[] moons =  moonDatas.split(";");
		List<Long> lst = new ArrayList<Long>();
		
		for(int i = 0; i < moons.length; i++) {
			String[] datas = moons[i].split(":");
			if(datas.length<1)
				return "月度记录输入错误";
			
			try {
				Long time = Long.parseLong(datas[0]);
				time = timeutil.getMoonStart(time);
				for(int it = 0; it < lst.size(); it++) {
					if(lst.get(it).equals(time)) {
						return "存在重复月度记录";
					}
				}
				lst.add(time);
				
			} catch (NumberFormatException e) {
				return "月度记录输入错误";
			}
		}
		return "";
	}
	
	public static String getBodyString(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {

    		System.out.println("body---------read----");
            inputStream = request.getInputStream();
            reader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));

            char[] bodyCharBuffer = new char[1024];
            int len = 0;
            len = reader.read(bodyCharBuffer);
    		System.out.println("body---------read111----"+len);
            while (len != -1) {
                sb.append(new String(bodyCharBuffer, 0, len));
                len = reader.read(bodyCharBuffer);
        		System.out.println("body---------read111----"+len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
