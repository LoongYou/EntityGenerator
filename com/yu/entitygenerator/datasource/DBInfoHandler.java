package com.yu.entitygenerator.datasource;

import java.util.List;

/**
 * 这个是所有handler的抽象父类，实现了DataSourceInfo接口的方法但都是空的
 * @author yl 
 * @version 1.0 2017年8月31日
 * 
 */
public abstract class DBInfoHandler implements DataSourceInfo{
	public static String dbVersion;
	@Override
	public void getDataBaseInfo() {
		// TODO Auto-generated method stub
		
	}
	
	public synchronized List<?> getTableList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getTableInfo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized List<?> getTableInfo(String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
