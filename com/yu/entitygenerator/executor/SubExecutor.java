package com.yu.entitygenerator.executor;

import java.util.List;

import com.yu.entitygenerator.builder.BuilderMateHandler;
import com.yu.entitygenerator.io.ResourceAcquirer;
import com.yu.entitygenerator.io.TypeSourceWriter;
import com.yu.entitygenerator.util.TimeLogger;

/**
 * ��MainExecutor���������������̣߳�ÿ�����̸߳����һ���������ת���������ļ�
* @author yl  
* @version 1.0 2017��8��30��
*    
*/
public class SubExecutor extends Thread{
	private String table_Name;
	private List<String[]> column_List;
	
	public SubExecutor(String table_Name,List<String[]> column_List) {
		super();
		this.setName(table_Name);
		this.table_Name = table_Name;
		this.column_List = column_List;
	}


	@Override
	public void run() {
		TimeLogger.info("�����߳�"+this.getName());
		BuilderMateHandler bmh = new BuilderMateHandler(table_Name, column_List);
		TypeSourceWriter writer = new TypeSourceWriter();
		writer.writeType(bmh.build(ResourceAcquirer.fieldPreview),bmh.getTypeName(),ResourceAcquirer.cover);
		bmh = null;
		writer = null;
	}
	
}
