package com.yu.entitygenerator.datasource;

import java.util.List;

public interface DataSourceInfo {
	
	/** 获取数据库信息 */
	void getDataBaseInfo();
	/** 获取表信息 */
	void getTableInfo();
	/** 获取表队列 */
	List<?> getTableList();
	/** 获取表信息 返回字段集合 */
	List<?> getTableInfo(String table_Name);
	/** 关闭数据库连接 */
	void close();
}
