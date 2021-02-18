package com.trinamota.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.trinamota.domain.condition;

public class sqlutil {
	
	private static Logger logger = Logger.getLogger(sqlutil.class);  
	public static final int MODEL = 1;//模型库
	public static final int HIS = 2;//模型库


	public Connection conn = null;
	public Statement st = null;
	public ResultSet rs = null;
	public long id = 0;

	public sqlutil(int type) {
		init(type);
	}  

	private void init(int type) {

		try {
			if(type == MODEL) {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
				String sconn= "jdbc:mysql://127.0.0.1:3306/dmsmarket2?useSSL=FALSE&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true&characterEncoding=utf-8";
				this.conn = DriverManager.getConnection(sconn,"root", "theta1234");
				this.st = this.conn.createStatement();
			}
			else if(type == HIS){
				Class.forName("com.mysql.cj.jdbc.Driver");
				String sconn= "jdbc:mysql://127.0.0.1:3306/dmsmarkethis?useSSL=FALSE&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true&characterEncoding=utf-8";
				this.conn = DriverManager.getConnection(sconn,"root", "theta1234");
				this.st = this.conn.createStatement();
			}
			
			


		} catch (Exception e) {
			this.conn = null;
			this.st = null;
			e.printStackTrace();
			System.out.println("open db failed, db type:" + type);
			return;
		}  	
	}

	public boolean colComment(){

		try {
			DatabaseMetaData metadata = this.conn.getMetaData();

			rs = metadata.getTables(this.conn.getCatalog(), "root", null, new String[]{"TABLE"});
			while(rs.next()) {
				String tablename = rs.getString("TABLE_NAME");
				ResultSet colRet = metadata.getColumns(null,"%", tablename,"%");

				String str = "";
				String str2 = "";
				while(colRet.next()) { 
					String columnName = colRet.getString("COLUMN_NAME"); 
					String columnType = colRet.getString("REMARKS"); 

					if(columnType == null || columnType.equals(""))
						continue;

					str2 += columnName+",";
					str += columnType+"="+columnName+";";
				}
				System.out.println(tablename+":   "+str); 
				System.out.println(tablename+":   "+str2); 
			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean getGetColNameType(String tablename,Map<String,String> m_column){

		try {
			//			System.out.println("**********************"); 
			//			System.out.println(tablename); 
			DatabaseMetaData metadata = this.conn.getMetaData();
			ResultSet colRet = metadata.getColumns(null,"%", tablename,"%"); 
			//			System.out.println(colRet); 
			while(colRet.next()) { 
				String columnName = colRet.getString("COLUMN_NAME"); 
				String columnType = colRet.getString("TYPE_NAME"); 
				m_column.put(columnName, columnType);

				//								System.out.println(columnName+" "+columnType); 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String combineCondition(List<condition> lstConds, Map<String, String> mapColTypes){
		String scond = "";
		if(lstConds != null && lstConds.size() > 0) {
			int first = getFirstNotEmpty(lstConds);
			if(first==-1)
				return "";
			condition con = lstConds.get(first);
			scond += " WHERE `" + con.getName() +"` "+ con.getOpt()+" ";
			String dataType = mapColTypes.get(con.getName());
			System.out.println(dataType);
			if(dataType == null) {
				return "";
			} else if(dataType.equals("TEXT") || dataType.equals("VARCHAR")) {
				scond += "'" + con.getValue() + "' ";
			} else {
				scond += con.getValue() + " ";
			}

			for(int i = first+1; i < lstConds.size(); i++) {
				con = lstConds.get(i);
				if(con.getValue()== null)
					continue;

				if(con.getOpt().equals("like") && con.getValue().equals("'%%'"))
					continue;

				if(con.getValue().equals(""))
					continue;

				scond += con.getType()+ " `" + con.getName() +"` " +con.getOpt() + " ";
				dataType = mapColTypes.get(con.getName());
				System.out.println(dataType);
				if(dataType == null) {
					return "";
				} else if(dataType.equals("TEXT") || dataType.equals("VARCHAR")) {
					scond += "'" + con.getValue() + "' ";
				} else {
					scond += con.getValue() + " ";
				}
			}
		}

		return scond;
	}

	//多表查询，人工判断字段类型和名称
	public static String combineCondition(List<condition> lstConds){
		String scond = "";
		if(lstConds != null && lstConds.size() > 0) {
			int first = getFirstNotEmpty(lstConds);
			if(first==-1)
				return "";
			condition con = lstConds.get(first);
			scond += " " + con.getName() +" "+ con.getOpt()+" ";
			scond += con.getValue() + " ";

			for(int i = first+1; i < lstConds.size(); i++) {
				con = lstConds.get(i);
				if(con.getValue()== null)
					continue;

				if(con.getOpt().equals("like") && con.getValue().equals("'%%'"))
					continue;

				if(con.getValue().equals("")||con.getValue().equals("''"))
					continue;

				scond += con.getType()+ " " + con.getName() +" " +con.getOpt() + " ";
				scond += con.getValue() + " ";

			}
		}

		return scond;
	}

	public static int getFirstNotEmpty(List<condition> lstConds){

		for(int i = 0; i < lstConds.size(); i++) {
			condition con = lstConds.get(i);
			if(con.getValue()== null)
				continue;

			if(con.getOpt().equals("like") && con.getValue().equals("'%%'"))
				continue;

			if(con.getValue().equals("")||con.getValue().equals("''"))
				continue;

			return i;
		}

		return -1;
	}

	public boolean query(String sql) {
		if (this.st == null) {
			return false;
		}

		try {
			if(this.rs != null) {
				this.rs.close();
			}

			//System.out.println(sql);
			this.rs = this.st.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean update(String sql) {
		if (this.st == null || sql.equals("")) {
			return false;
		}

		try {
			if(this.rs != null) {
				this.rs.close();
			}
			
			logger.debug(sql);
			int count=this.st.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
			
			if(count > 0)
			{

				this.rs=this.st.getGeneratedKeys();
				if(this.rs.next())
					this.id = this.rs.getLong(1);
			}
			else
			{
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean close() {
		try {
			if(this.rs != null) {
				this.rs.close();
			}

			if(this.st!=null)
				this.st.close();
			if(this.conn!=null)
				this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@SuppressWarnings({ "rawtypes" })
	public List<Object> query(Class objClass, List<condition> lstConds, String orderby) {
		return this.query(objClass, objClass.getSimpleName(), lstConds, orderby);
	}

	@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
	public List<Object> query(Class objClass, String tblname, List<condition> lstConds, String orderby) {

		//1
		String sql = "SELECT * FROM `" + tblname + "`";
		Map<String, String> mapColTypes = new HashMap<String, String>();
		getGetColNameType(tblname, mapColTypes);
		List<Object> objs = new ArrayList<Object>();

		if(orderby == null || orderby.equals("")) {
			sql += this.combineCondition(lstConds, mapColTypes);
		}  else {
			sql += this.combineCondition(lstConds, mapColTypes) + " order by " + orderby;
		}

		if(!this.query(sql)) {
			return objs;
		}

		//2
		Field fields[] = objClass.getDeclaredFields();
		try {
			int pi = 0;
			while(rs.next()) {
				pi++;
				Object obj1 = objClass.newInstance();
				for(Field field : fields) {
					//System.out.println(Modifier.toString(field.getModifiers()) + field.getName());
					String fName = field.getName();
					if(fName.equals("ser")) {
						Method method = objClass.getMethod("set" + fName.substring(0, 1).toUpperCase() + fName.substring(1), String.class);
						method.invoke(obj1, String.valueOf(pi));

					}

					if(!isPrivate(field)) {
						//field.set(obj1, rs.getString(fName));
						continue;
					} else {
						Method method = objClass.getMethod("set" + fName.substring(0, 1).toUpperCase() + fName.substring(1), String.class);
						//System.out.println(method.toString());
						method.invoke(obj1, rs.getString(fName));
					}
				}
				objs.add(obj1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objs;
	}

	@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
	public List<Object> queryToCombo(Class objClass, String tblname, List<condition> lstConds, String orderby, String ids) {
		String aryid[] = ids.split(",");
		if(aryid.length != 2)
			return null;


		//1
		String sql = "SELECT "+aryid[0]+" id,"+aryid[1]+" name"+" FROM `" + tblname + "`";
		Map<String, String> mapColTypes = new HashMap<String, String>();
		getGetColNameType(tblname, mapColTypes);
		List<Object> objs = new ArrayList<Object>();

		if(orderby == null || orderby.equals("")) {
			sql += this.combineCondition(lstConds, mapColTypes);
		}  else {
			sql += this.combineCondition(lstConds, mapColTypes) + " order by " + orderby;
		}

		if(!this.query(sql)) {
			return objs;
		}

		//2
		Field fields[] = objClass.getDeclaredFields();
		try {
			int pi = 0;
			while(rs.next()) {
				pi++;
				Object obj1 = objClass.newInstance();
				for(Field field : fields) {
					//System.out.println(Modifier.toString(field.getModifiers()) + field.getName());
					String fName = field.getName();
					if(fName.equals("ser")) {
						Method method = objClass.getMethod("set" + fName.substring(0, 1).toUpperCase() + fName.substring(1), String.class);
						method.invoke(obj1, String.valueOf(pi));

					}

					if(!isPrivate(field)) {
						//field.set(obj1, rs.getString(fName));
						continue;
					} else {
						Method method = objClass.getMethod("set" + fName.substring(0, 1).toUpperCase() + fName.substring(1), String.class);
						//System.out.println(method.toString());
						method.invoke(obj1, rs.getString(fName));
					}
				}
				objs.add(obj1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objs;
	}

	@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
	public List<Object> querySQL(Class objClass, String sql) {

		List<Object> objs = new ArrayList<Object>();
		if(!this.query(sql)) {
			return objs;
		}

		//2
		Field fields[] = objClass.getDeclaredFields();
		try {
			int pi = 0;
			while(rs.next()) {
				pi++;
				Object obj1 = objClass.newInstance();
				for(Field field : fields) {
					//System.out.println(Modifier.toString(field.getModifiers()) + field.getName());
					String fName = field.getName();
					if(fName.equals("ser")) {
						Method method = objClass.getMethod("set" + fName.substring(0, 1).toUpperCase() + fName.substring(1), String.class);
						method.invoke(obj1, String.valueOf(pi));

					}

					if(!isPrivate(field)) {
						//field.set(obj1, rs.getString(fName));
						continue;
					} else {
						Method method = objClass.getMethod("set" + fName.substring(0, 1).toUpperCase() + fName.substring(1), String.class);
						//System.out.println(method.toString());
						
						//System.out.println();
						try
						{
							method.invoke(obj1, rs.getString(fName));
						}
						catch(SQLException e)
						{
							continue;
						}
					}
				}
				objs.add(obj1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objs;
	}

	@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
	public List<Object> querySQL(Class objClass, String sql, String pageIndex, String pageSize) {

		List<Object> objs = new ArrayList<Object>();

		int iIndex = Integer.parseInt(pageIndex);
		int iSize = Integer.parseInt(pageSize);
		int iStart = iSize*(iIndex-1);

		if(!this.query(sql)) {
			return objs;
		}

		//2
		Field fields[] = objClass.getDeclaredFields();
		try {
			int pi = iStart;
			while(rs.next()) {
				pi++;

				Object obj1 = objClass.newInstance();
				for(Field field : fields) {
					//System.out.println(Modifier.toString(field.getModifiers()) + field.getName());
					String fName = field.getName();
					if(fName.equals("ser")) {
						Method method = objClass.getMethod("set" + fName.substring(0, 1).toUpperCase() + fName.substring(1), String.class);
						method.invoke(obj1, String.valueOf(pi));

					}
					if(!isPrivate(field)) {
						//field.set(obj1, rs.getString(fName));
						continue;
					} else {
						Method method = objClass.getMethod("set" + fName.substring(0, 1).toUpperCase() + fName.substring(1), String.class);
						//System.out.println(method.toString());
						method.invoke(obj1, rs.getString(fName));
					}
				}
				objs.add(obj1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objs;
	}

	public List<Object> querySQL(int size, String sql) {

		List<Object> objs = new ArrayList<Object>();

		if(!this.query(sql)) {
			return objs;
		}

		//2
		try {
			while(rs.next()) {
				String obj[] = new String[size];
				for(int i = 0; i < size; i++) {
					obj[i] = rs.getString(i+1);
				}
				objs.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objs;
	}

	public List<Object> querySQL(String ids, int size, String sql) {

		List<Object> objs = new ArrayList<Object>();
		String fields[] = ids.split(",");

		if(!this.query(sql)) {
			return objs;
		}
		//		System.out.println(ids);
		//		System.out.println(fields.length);
		//2
		try {
			while(rs.next()) {
				Map<String,String> obj = new HashMap<String,String>();
				for(int i = 0; i < size; i++) {
					obj.put(fields[i], rs.getString(i+1));
					//System.out.println(fields[i]+" "+ rs.getString(i+1));
				}
				objs.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objs;
	}

	public void querySQL(String ids, String sql, Map<String, Object> obj) {

		String fields[] = ids.split(",");

		if(!this.query(sql)) {
			return;
		}

		try {
			if(rs.next()) {
				for(int i = 0; i < fields.length; i++) {
					obj.put(fields[i], rs.getString(i+1));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void querySQLs(String ids, String sql, Map<String, Object> obj, String key) {

		String fields[] = ids.split(",");
		List<Object> lstObjs = new ArrayList<Object>();
		if(!this.query(sql)) {
			return;
		}

		try {
			while(rs.next()) {
				Map<String, Object> objs = new HashMap<String, Object>();
				for(int i = 0; i < fields.length; i++) {
					objs.put(fields[i], rs.getString(i+1));
				}
				lstObjs.add(objs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		obj.put(key, lstObjs);
	}

	public List<Object> querySQLs(String ids, String sql) {

		String fields[] = ids.split(",");
		List<Object> lstObjs = new ArrayList<Object>();
		if(!this.query(sql)) {
			return lstObjs;
		}

		try {
			while(rs.next()) {
				Map<String, Object> objs = new HashMap<String, Object>();
				for(int i = 0; i < fields.length; i++) {
					objs.put(fields[i], rs.getString(i+1));
				}
				lstObjs.add(objs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstObjs;
	}

	public List<Object> querySQLs(String ids, String sql, String pageIndex, String pageSize) {
		int iIndex = Integer.parseInt(pageIndex);
		int iSize = Integer.parseInt(pageSize);
		int iStart = iSize*(iIndex-1);

		String lim = " limit " + iStart + "," + iSize;

		String fields[] = ids.split(",");
		List<Object> lstObjs = new ArrayList<Object>();
		if(!this.query(sql+lim)) {
			return lstObjs;
		}

		try {

			int pi = iStart;
			while(rs.next()) {
				pi++;
				Map<String, Object> objs = new HashMap<String, Object>();
				objs.put("ser", pi+"");
				for(int i = 0; i < fields.length; i++) {
					objs.put(fields[i], rs.getString(i+1));
				}
				lstObjs.add(objs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstObjs;
	}

	public List<Object> query(String ids, String tblname, List<condition> lstConds) {

		//1
		String sql = "SELECT * FROM `" + tblname + "`";
		Map<String, String> mapColTypes = new HashMap<String, String>();
		getGetColNameType(tblname, mapColTypes);
		List<Object> objs = new ArrayList<Object>();

		sql += this.combineCondition(lstConds, mapColTypes);


		if(!this.query(sql)) {
			return objs;
		}

		//2
		String fields[] = ids.split(",");
		try {
			while(rs.next()) {
				String obj[] = new String[fields.length];
				for(int i = 0; i < fields.length; i++) {
					String fName = fields[i];
					obj[i] = rs.getString(fName);
				}
				objs.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objs;
	}
	
	
	public Map<String,Object> querymap(String ids, String tblname, List<condition> lstConds) {

		//1
		String sql = "SELECT * FROM `" + tblname + "`";
		Map<String, String> mapColTypes = new HashMap<String, String>();
		getGetColNameType(tblname, mapColTypes);
		Map<String,Object> maps = new HashMap<String,Object>();
		Map<String,String> mapvalues = new HashMap<String,String>();
		sql += this.combineCondition(lstConds, mapColTypes);


		if(!this.query(sql)) {
			return maps;
		}

		//2
		String fields[] = ids.split(",");
		
		if(fields.length !=2)
		{
			return maps;
		}
		
		
		try {
			while(rs.next()) {
				//Map<String,String> onerow = new HashMap<String,String>();
//				for(int i = 0; i < fields.length; i++) {
//					String kName1 = fields[i];
//					
//				}
				
				String kName1 = fields[0];
				String kName2 = fields[1];
				
				String vName1 = rs.getString(kName1);
				String vName2 = rs.getString(kName2);
				
				
				maps.put(vName1,vName2);
				//System.out.println(vName1+"============="+vName2);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return maps;
	}
	
	public List<Object> queryobj(String ids, String tblname, List<condition> lstConds) {

		//1
		String sql = "SELECT * FROM `" + tblname + "`";
		Map<String, String> mapColTypes = new HashMap<String, String>();
		getGetColNameType(tblname, mapColTypes);
		List<Object> objs = new ArrayList<Object>();

		sql += this.combineCondition(lstConds, mapColTypes);


		if(!this.query(sql)) {
			return objs;
		}

		//2
		String fields[] = ids.split(",");
		
		
		try {
			while(rs.next()) {
				Map<String,String> onerow = new HashMap<String,String>();
				for(int i = 0; i < fields.length; i++) {
					String fName = fields[i];
					onerow.put(fName, rs.getString(fName));
				}
				objs.add(onerow);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objs;
	}



	@SuppressWarnings({ "rawtypes" })
	public int tblcount(Class objClass, List<condition> lstConds) {
		return this.tblcount(objClass, objClass.getSimpleName(), lstConds);
	}

	@SuppressWarnings({ "rawtypes" })
	public int tblcount(Class objClass, String tblname, List<condition> lstConds) {

		String sql = "SELECT COUNT(*) FROM `" + tblname + "`";
		Map<String, String> mapColTypes = new HashMap<String, String>();
		getGetColNameType(tblname, mapColTypes);

		sql += this.combineCondition(lstConds, mapColTypes);

		if(!this.query(sql)) {
			return 0;
		}

		try {
			if(this.rs.next()) {
				return this.rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public int tblcountSQL(String sql) {

		if(!this.query(sql)) {
			return 0;
		}

		try {
			if(this.rs.next()) {
				return this.rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public int tblcountSQLFZ(String sql) {

		String ss = "select count(*) from (" +sql+") as retcc";
		if(!this.query(ss)) {
			return 0;
		}

		try {
			if(this.rs.next()) {
				return this.rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public int tblcountTable(String tablename) {

		String sql = "select count(1) from "+tablename+";";


		return tblcountSQL(sql);
	}

	public int tblcountTable(String tablename,String wheres) {

		if(wheres.equals(""))
		{
			return tblcountTable(tablename);
		}


		String sql = "select count(1) from "+tablename+"  where "+ wheres+";";


		return tblcountSQL(sql);
	}


	@SuppressWarnings({ "rawtypes" })
	public List<Object> query(Class objClass, List<condition> lstConds, String pageIndex, String pageSize, String orderby) {
		return this.query(objClass, objClass.getSimpleName(), lstConds, pageIndex, pageSize, orderby);
	}

	@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
	public List<Object> query(Class objClass, String tblname, List<condition> lstConds, String pageIndex, String pageSize, String orderby) {

		//1
		String sql = "SELECT * FROM `" + tblname + "`";
		Map<String, String> mapColTypes = new HashMap<String, String>();
		getGetColNameType(tblname, mapColTypes);
		List<Object> objs = new ArrayList<Object>();

		int iIndex = Integer.parseInt(pageIndex);
		int iSize = Integer.parseInt(pageSize);
		int iStart = iSize*(iIndex-1);

		String lim = " limit " + iStart + "," + iSize;
		if(orderby == null || orderby.equals("")) {
			sql += this.combineCondition(lstConds, mapColTypes) + lim;
		} else {
			sql += this.combineCondition(lstConds, mapColTypes) + " order by " + orderby + lim;
		}

		if(!this.query(sql)) {
			return objs;
		}

		//2
		Field fields[] = objClass.getDeclaredFields();
		try {
			int pi = iStart;
			while(rs.next()) {
				pi++;

				Object obj1 = objClass.newInstance();
				for(Field field : fields) {
					//System.out.println(Modifier.toString(field.getModifiers()) + field.getName());
					String fName = field.getName();
					if(fName.equals("ser")) {
						Method method = objClass.getMethod("set" + fName.substring(0, 1).toUpperCase() + fName.substring(1), String.class);
						method.invoke(obj1, String.valueOf(pi));

					}
					if(!isPrivate(field)) {
						//field.set(obj1, rs.getString(fName));
						continue;
					} else {
						Method method = objClass.getMethod("set" + fName.substring(0, 1).toUpperCase() + fName.substring(1), String.class);
						//System.out.println(method.toString());
						method.invoke(obj1, rs.getString(fName));
					}
				}
				objs.add(obj1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objs;
	}

	@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
	public List<Object> querySQL(Class objClass, String tblname, String sql, List<condition> lstConds, String pageIndex, String pageSize, String orderby) {

		//1
		Map<String, String> mapColTypes = new HashMap<String, String>();
		getGetColNameType(tblname, mapColTypes);
		List<Object> objs = new ArrayList<Object>();

		int iIndex = Integer.parseInt(pageIndex);
		int iSize = Integer.parseInt(pageSize);
		int iStart = iSize*(iIndex-1);

		String lim = " limit " + iStart + "," + iSize;
		if(orderby == null || orderby.equals("")) {
			sql += this.combineCondition(lstConds, mapColTypes) + lim;
		} else {
			sql += this.combineCondition(lstConds, mapColTypes) + " order by " + orderby + lim;
		}

		if(!this.query(sql)) {
			return objs;
		}

		//2
		Field fields[] = objClass.getDeclaredFields();
		try {
			int pi = iStart;
			while(rs.next()) {
				pi++;

				Object obj1 = objClass.newInstance();
				for(Field field : fields) {
					//System.out.println(Modifier.toString(field.getModifiers()) + field.getName());
					String fName = field.getName();
					if(fName.equals("ser")) {
						Method method = objClass.getMethod("set" + fName.substring(0, 1).toUpperCase() + fName.substring(1), String.class);
						method.invoke(obj1, String.valueOf(pi));

					}
					if(!isPrivate(field)) {
						//field.set(obj1, rs.getString(fName));
						continue;
					} else {
						Method method = objClass.getMethod("set" + fName.substring(0, 1).toUpperCase() + fName.substring(1), String.class);
						//System.out.println(method.toString());
						method.invoke(obj1, rs.getString(fName));
					}
				}
				objs.add(obj1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objs;
	}


	@SuppressWarnings({ "rawtypes" })
	public boolean modify(Object obj, List<condition> lstConds) {
		Class objClass= obj.getClass();
		return this.modify(obj, objClass.getSimpleName(), lstConds);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean modify(Object obj, String tblname, List<condition> lstConds) {

		Class objClass= obj.getClass();

		//1
		//UPDATE `sdpt`.`euser` SET `status`='01' WHERE  `id`=2;
		String sql = "UPDATE `" + tblname + "` SET";
		Map<String, String> mapColTypes = new HashMap<String, String>();
		getGetColNameType(tblname, mapColTypes);
		String scond = this.combineCondition(lstConds, mapColTypes);

		//2
		Field fields[] = objClass.getDeclaredFields();
		String supdate = "";
		try {
			if(fields != null && fields.length > 0) {
				int iStart = getFirstField(fields);
				Field field = fields[iStart];
				String fName = field.getName();
				String val = "";
				if(!isPrivate(field)) {
					//val = (String)field.get(obj);
				} else {
					Method method = objClass.getMethod("get" + fName.substring(0, 1).toUpperCase() + fName.substring(1));
					//System.out.println(method.toString());
					val = (String)method.invoke(obj);
				}
				supdate = " `" + fName + "`=";
				String dataType = mapColTypes.get(fName);
				if(dataType == null) {
					return false;
				} else if(dataType.equals("TEXT") || dataType.equals("VARCHAR")) {
					supdate += "'" + val + "' ";
				} else {
					supdate += val + " ";
				}

				for(int i = iStart+1; i < fields.length; i++) {
					field = fields[i];
					fName = field.getName();
					if(!isPrivate(field)) {
						//val = (String)field.get(obj);
						continue;
					} else {
						Method method = objClass.getMethod("get" + fName.substring(0, 1).toUpperCase() + fName.substring(1));
						//System.out.println(method.toString());
						val = (String)method.invoke(obj);
					}
					supdate += ", `" + fName + "`=";
					dataType = mapColTypes.get(fName);
					if(dataType == null) {
						return false;
					} else if(dataType.equals("TEXT") || dataType.equals("VARCHAR")) {
						supdate += "'" + val + "' ";
					} else {
						supdate += val + " ";
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		sql = sql + supdate + scond;

		if(!this.update(sql)) {
			return false;
		}

		return true;
	}

	public boolean modify(String tblname, List<condition> lstConds, String ids, String... vals) {
		String sql = "UPDATE `" + tblname + "` SET";
		Map<String, String> mapColTypes = new HashMap<String, String>();
		getGetColNameType(tblname, mapColTypes);
		String scond = this.combineCondition(lstConds, mapColTypes);
		//2
		String fields[] = ids.split(",");
		String supdate = "";
		try {

			if(fields != null && fields.length > 0) {
				String fName = fields[0];
				String val = vals[0];

				supdate = " `" + fName + "`=";
				String dataType = mapColTypes.get(fName);
				//System.out.println(fName);
				if(dataType == null) {
					return false;
				} else if(dataType.equals("TEXT") || dataType.equals("VARCHAR")) {
					supdate += "'" + val + "' ";
				} else {
					if(val.equals(""))
						val = "0";
					supdate += val + " ";
				}

				for(int i = 1; i < fields.length; i++) {
					fName = fields[i];
					val = vals[i];

					supdate += ", `" + fName + "`=";
					dataType = mapColTypes.get(fName);
					//System.out.println(fName);
					if(dataType == null) {
						return false;
					} else if(dataType.equals("TEXT") || dataType.equals("VARCHAR")) {
						supdate += "'" + val + "' ";
					} else {
						if(val.equals(""))
							val = "0";
						supdate += val + " ";
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		sql = sql + supdate + scond;

		if(!this.update(sql)) {
			return false;
		}

		return true;
	}

	public boolean modifyAll(String tblname, String ids, String... vals) {

		String sql = "UPDATE `" + tblname + "` SET";
		Map<String, String> mapColTypes = new HashMap<String, String>();
		getGetColNameType(tblname, mapColTypes);

		//2
		String fields[] = ids.split(",");
		String supdate = "";
		try {
			if(fields != null && fields.length > 0) {
				String fName = fields[0];
				String val = vals[0];

				supdate = " `" + fName + "`=";
				String dataType = mapColTypes.get(fName);
				if(dataType == null) {
					return false;
				} else if(dataType.equals("TEXT") || dataType.equals("VARCHAR")) {
					supdate += "'" + val + "' ";
				} else {
					if(val.equals(""))
						val = "0";
					supdate += val + " ";
				}

				for(int i = 1; i < fields.length; i++) {
					fName = fields[i];
					val = vals[i];

					supdate += ", `" + fName + "`=";
					dataType = mapColTypes.get(fName);
					if(dataType == null) {
						return false;
					} else if(dataType.equals("TEXT") || dataType.equals("VARCHAR")) {
						supdate += "'" + val + "' ";
					} else {
						if(val.equals(""))
							val = "0";
						supdate += val + " ";
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		sql = sql + supdate;

		if(!this.update(sql)) {
			return false;
		}

		return true;
	}

	@SuppressWarnings({ "rawtypes" })
	public boolean delete(Class objClass, List<condition> lstConds) {
		return this.delete(objClass.getSimpleName(), lstConds);
	}

	public boolean delete(String tblname, List<condition> lstConds) {
		String sql = "DELETE FROM `" + tblname + "` ";
		Map<String, String> mapColTypes = new HashMap<String, String>();
		getGetColNameType(tblname, mapColTypes);
		String scond = this.combineCondition(lstConds, mapColTypes);

		sql = sql + scond;

		if(!this.update(sql)) {
			return false;
		}

		return true;
	}

	@SuppressWarnings({ "rawtypes" })
	public boolean insert(Object obj) {
		Class objClass= obj.getClass();
		return this.insert(obj, objClass.getSimpleName());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean insert(Object obj, String tblname) {
		Class objClass= obj.getClass();

		//INSERT INTO `sdpt`.`euser` (`groupid`, `cat`, `status`, `usertype`) VALUES ('321', '312', '312', '1');
		String sql = "INSERT INTO `" + tblname + "` ";
		Map<String, String> mapColTypes = new HashMap<String, String>();
		getGetColNameType(tblname, mapColTypes);

		//2
		Field fields[] = objClass.getDeclaredFields();
		String svalues = "";
		try {
			if(fields != null && fields.length > 0) {
				//2.1
				int iStart = getFirstField(fields);
				Field field = fields[iStart];
				String fName = field.getName();

				svalues = " (`" + fName + "`";
				for(int i = iStart+1; i < fields.length; i++) {
					field = fields[i];
					if(!isPrivate(field)) {
						continue;
					}
					fName = field.getName();
					svalues += ", `" + fName + "`";
				}
				svalues += ") VALUES (";

				field = fields[iStart];
				fName = field.getName();

				String val = "";
				if(!isPrivate(field)) {
					//val = (String)field.get(obj);
				} else {
					Method method = objClass.getMethod("get" + fName.substring(0, 1).toUpperCase() + fName.substring(1));
					//System.out.println(method.toString());
					val = (String)method.invoke(obj);
				}
				svalues += "'" + val + "'";

				for(int i = iStart+1; i < fields.length; i++) {
					field = fields[i];
					fName = field.getName();
					if(!isPrivate(field)) {
						//val = (String)field.get(obj);
						continue;
					} else {
						Method method = objClass.getMethod("get" + fName.substring(0, 1).toUpperCase() + fName.substring(1));
						//System.out.println(method.toString());
						val = (String)method.invoke(obj);
					}
					svalues += ",'" + val + "'";
				}
				svalues += ")";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		if(svalues.equals("")) {
			return false;
		}

		sql = sql + svalues;

		if(!this.update(sql)) {
			return false;
		}

		return true;
	}

	public boolean insert(String tblname, String ids, String valss) {

		String sql = "INSERT INTO `" + tblname + "` ";
		Map<String, String> mapColTypes = new HashMap<String, String>();
		getGetColNameType(tblname, mapColTypes);

		//2
		String fields[] = ids.split(",");
		String vals [] = valss.split(",");
		String svalues = "";
		try {
			int pos = this.getFirstNotEmpty(vals);


			if(fields != null && fields.length > 0 && pos>=0) {


				String fName = fields[pos];

				svalues = " (`" + fName + "`";
				for(int i = pos+1; i < fields.length; i++) {
					fName = fields[i];
					if(vals[i] == null || vals[i].equals("")) {
						continue;
					}
					svalues += ", `" + fName + "`";
				}
				svalues += ") VALUES (";

				fName = fields[pos];

				String val = vals[pos];
				svalues += "'" + val + "'";

				for(int i = pos+1; i < fields.length; i++) {
					if(vals[i] == null || vals[i].equals("")) {
						continue;
					}
					fName = fields[i];
					val = vals[i];
					svalues += ",'" + val + "'";
				}
				svalues += ")";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		if(svalues.equals("")) {
			return false;
		}

		sql = sql + svalues;

		if(!this.update(sql)) {
			return false;
		}

		return true;
	}

	@SuppressWarnings({ "rawtypes" })
	public boolean insertM(List<Object> objs) {
		if(objs.size() < 1) {
			return false;
		}
		Class objClass= objs.get(0).getClass();
		return this.insertM(objs, objClass.getSimpleName());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean insertM(List<Object> objs, String tblname) {
		if(objs.size() < 1) {
			return false;
		}

		Object obj = objs.get(0).getClass();

		Class objClass= obj.getClass();

		String sql = "INSERT INTO `" + tblname + "` ";
		Map<String, String> mapColTypes = new HashMap<String, String>();
		getGetColNameType(tblname, mapColTypes);

		//2
		Field fields[] = objClass.getDeclaredFields();
		String svalues = "";
		try {
			if(fields != null && fields.length > 0) {
				//2.1
				int iStart = getFirstField(fields);
				Field field = fields[iStart];
				String fName = field.getName();

				svalues = " (`" + fName + "`";
				for(int i = iStart+1; i < fields.length; i++) {
					field = fields[i];
					if(!isPrivate(field)) {
						continue;
					}
					fName = field.getName();
					svalues += ", `" + fName + "`";
				}
				svalues += ") VALUES (";

				//2.2
				field = fields[iStart];
				fName = field.getName();

				String val = "";
				if(!isPrivate(field)) {
					//val = (String)field.get(obj);
				} else {
					Method method = objClass.getMethod("get" + fName.substring(0, 1).toUpperCase() + fName.substring(1));
					//System.out.println(method.toString());
					val = (String)method.invoke(obj);
				}
				svalues += "'" + val + "'";

				for(int i = iStart+1; i < fields.length; i++) {
					field = fields[i];
					fName = field.getName();
					if(!isPrivate(field)) {
						//val = (String)field.get(obj);
						continue;
					} else {
						Method method = objClass.getMethod("get" + fName.substring(0, 1).toUpperCase() + fName.substring(1));
						//System.out.println(method.toString());
						val = (String)method.invoke(obj);
					}
					svalues += ",'" + val + "'";
				}
				svalues += ")";

				//2.3
				for(int inObj = 1; inObj < objs.size(); inObj++) {

					obj = objs.get(inObj).getClass();

					svalues += ", (";

					field = fields[iStart];
					fName = field.getName();

					if(!isPrivate(field)) {
						//val = (String)field.get(obj);
					} else {
						Method method = objClass.getMethod("get" + fName.substring(0, 1).toUpperCase() + fName.substring(1));
						//System.out.println(method.toString());
						val = (String)method.invoke(obj);
					}
					svalues += "'" + val + "'";

					for(int i = iStart+1; i < fields.length; i++) {
						field = fields[i];
						fName = field.getName();
						if(!isPrivate(field)) {
							//val = (String)field.get(obj);
							continue;
						} else {
							Method method = objClass.getMethod("get" + fName.substring(0, 1).toUpperCase() + fName.substring(1));
							//System.out.println(method.toString());
							val = (String)method.invoke(obj);
						}
						svalues += ",'" + val + "'";
					}
					svalues += ")";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		if(svalues.equals("")) {
			return false;
		}

		sql = sql + svalues;

		if(!this.update(sql)) {
			return false;
		}

		return true;
	}

	public Long lastInsertID() {

		String sql = "select @@IDENTITY";

		if(!this.query(sql)) {
			return 0L;
		}

		try {
			if(this.rs.next()) {
				return this.rs.getLong(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0L;
	}

	public int getFirstField(Field fields[]) {
		Field field = null;

		for(int i = 0; i < fields.length; i++) {
			field = fields[i];

			String smod = Modifier.toString(field.getModifiers());

			if(smod.equals("private")) {
				System.out.println(i);
				return i;
			} 
		}

		System.out.println("0");
		return 0;
	}

	public int getFirstNotEmpty(String values[]) {

		for(int i = 0; i < values.length; i++) {

			if(values[i]!=null && !values[i].equals("")) {
				return i;
			} 
		}

		return -1;
	}

	public boolean isPrivate(Field field) {
		String smod = Modifier.toString(field.getModifiers());

		if(smod.equals("private")) {
			return true;
		} 

		return false;
	}
}
