package com.yu.entitygenerator.datasource;

import java.util.List;

public interface DataSourceInfo {
	
	/** ��ȡ���ݿ���Ϣ */
	void getDataBaseInfo();
	/** ��ȡ����Ϣ */
	void getTableInfo();
	/** ��ȡ����� */
	List<?> getTableList();
	/** ��ȡ����Ϣ �����ֶμ��� */
	List<?> getTableInfo(String table_Name);
	/** �ر����ݿ����� */
	void close();
}
