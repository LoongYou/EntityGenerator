package com.yu.entitygenerator.datasource;

/**
 * 这是一个静态工厂模式，该工厂类根据给定的数据库类型创建handler
 * @author yl 
 * @version 1.0 2017年8月31日
 * 
 */
public class InfoHandlerFactory {

	public static DBInfoHandler getHandler(String DBType) throws Exception{
		if(DBType.equals(DBMS.MYSQL.value)){
			 return new MySqlHandler();			
		}
		if(DBType.equals(DBMS.Oracle.value)){
			return new OracleHandler();
		}
		return null;
		
	}
	
	
}
