package com.yu.entitygenerator.datasource;

/**
 * ����һ����̬����ģʽ���ù�������ݸ��������ݿ����ʹ���handler
 * @author yl 
 * @version 1.0 2017��8��31��
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
