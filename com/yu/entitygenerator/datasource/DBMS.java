package com.yu.entitygenerator.datasource;

/**
 * 该枚举定义了数据库类型和对应的操作sql
 * @author yl 
 * @version 1.0 2017年8月31日
 * 
 */
public enum DBMS {
	MYSQL("mysql",1),
	Oracle("oracle",2),
	TABLE("table",3),
	VIEW("view",3),
	BOTH("both",3),
	
/**       Mysql        */
	MYSQL_SHOWTABLE("show tables",1),
	MYSQL_GET_TABLES("select "
			+ "table_name "
			+ "from information_schema.TABLES "
			+ "where "
			+ "table_type=''BASE TABLE'' AND table_schema=''{0}''",1),
	MYSQL_GET_VIEWS("select "
			+ "table_name "
			+ "from information_schema.views "
			+ "where "
			+ "table_schema=''{0}''",1),
	MYSQL_GET_TABLECOLUMNS("select "
			+ "column_name,"
			+ "column_comment,"
			+ "column_type "
			+ "from information_schema.COLUMNS "
			+ "where "
			+ "table_schema=''{0}'' AND table_name=''{1}''",1);
	
	
	
	
	public String value;
	public int id;
	DBMS(String value,int id){
		this.value = value;
		this.id = id;
	}
}
