package com.yu.entitygenerator.executor;

import java.util.List;

import com.yu.entitygenerator.builder.BuilderMateHandler;
import com.yu.entitygenerator.io.ResourceAcquirer;
import com.yu.entitygenerator.io.TypeSourceWriter;
import com.yu.entitygenerator.util.TimeLogger;

/**
 * 由MainExecutor创建并启动的子线程，每个子线程负责对一个表和类型转换并生成文件
* @author yl  
* @version 1.0 2017年8月30日
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
		TimeLogger.info("启动线程"+this.getName());
		BuilderMateHandler bmh = new BuilderMateHandler(table_Name, column_List);
		TypeSourceWriter writer = new TypeSourceWriter();
		writer.writeType(bmh.build(ResourceAcquirer.fieldPreview),bmh.getTypeName(),ResourceAcquirer.cover,false,".java");
		bmh = null;
		writer = null;
	}
	
}
