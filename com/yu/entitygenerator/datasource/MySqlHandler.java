package com.yu.entitygenerator.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.yu.entitygenerator.io.ResourceAcquirer;
import com.yu.entitygenerator.util.TimeLogger;

public class MySqlHandler extends DBInfoHandler{
	private static Connection conn;
	private static Statement stm;
	private static String dbName = ResourceAcquirer.url.substring(ResourceAcquirer.url.lastIndexOf("/")+1,ResourceAcquirer.url.length());
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(ResourceAcquirer.url+ResourceAcquirer.param,ResourceAcquirer.username,ResourceAcquirer.password);
			stm = conn.createStatement();
			TimeLogger.info("已连接到数据库"+ResourceAcquirer.url);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			TimeLogger.info("找不到数据库驱动");
		} catch (SQLException e) {
			e.printStackTrace();
			TimeLogger.info("无法连接到数据库");
		}
	}
	
	
	@Override
	public synchronized List<String> getTableList() {
		List<String> table_List = new ArrayList<String>();
		try {
			Object[] params = {dbName};
			String sql = null;
			if(ResourceAcquirer.scan.equals(DBMS.BOTH.value)){
				TimeLogger.info("扫描表和视图");
				sql = DBMS.MYSQL_SHOWTABLE.value;
			}
			if(ResourceAcquirer.scan.equals(DBMS.TABLE.value)){
				TimeLogger.info("仅扫描表");
				sql = MessageFormat.format(DBMS.MYSQL_GET_TABLES.value, params);
				System.out.println(sql);
			}
			if(ResourceAcquirer.scan.equals(DBMS.VIEW.value)){
				TimeLogger.info("仅扫描视图");
				sql = MessageFormat.format(DBMS.MYSQL_GET_VIEWS.value, params);
			}
			ResultSet rs =  stm.executeQuery(sql);
			while(rs.next()){
				String temp = rs.getString(1);
				table_List.add(temp);
				TimeLogger.info(temp);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return table_List;
	}
 
	@Override
	public synchronized List<String[]> getTableInfo(String table_Name) {
		TimeLogger.info("解析表："+table_Name);
		String [] tempRow = null;
		List<String[]> table_Columns = new ArrayList<String[]>();
		try {
			Object[] params = {dbName,table_Name};
			String sql = MessageFormat.format(DBMS.MYSQL_GET_TABLECOLUMNS.value, params);
			//System.out.println(sql);
			ResultSet rs =  stm.executeQuery(sql);
			while(rs.next()){
				tempRow = new String[3];
				tempRow[0]=rs.getString(1);
				tempRow[1]=rs.getString(2);
				tempRow[2]=rs.getString(3);
				table_Columns.add(tempRow);
			}
			tempRow = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//TimeLogger.info("解析完成："+table_Name);
		return table_Columns;
	}

	@Override
	public void close() {
		if(stm!=null){
			try {
				stm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		TimeLogger.info("statement和connection已关闭");
	}
	
	
}
