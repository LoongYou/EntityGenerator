package com.yu.entitygenerator.builder;

import java.util.List;

/**
 * �����������ͨ��handler��ʽ�ṩ�ı���Ϣ��������
* @author yl  
* @version 1.0 2017��8��27��
*    
*/
public class BuilderMateHandler {
	private String table_Name;
	private List<String[]> table_Columns;
	private ClassBuilder builder;
	/**
	 * @param table_Name
	 * @param table_Columns
	 * @see com.yu.basegenerator.datasource.MySqlHandler#getTableInfo()
	 */
	public BuilderMateHandler(String table_Name, List<String[]> table_Columns) {
		super();
		this.table_Name = table_Name;
		this.table_Columns = table_Columns;
	}



	/**
	 * ���ø÷����ṹ�����һ���������������
	 * @param isUsePreview �Ƿ��������Ԥ��
	 */
	public String build(boolean isUsePreview) {
		builder = new ClassBuilder();
		for(String[] s:table_Columns){
			builder.bulidField(s[0],s[1],s[2]);	
		}
		String classContent = builder.buildEntity(table_Name);
		
		if(isUsePreview){
			System.out.println(table_Name+":\n"+builder.getFieldContent());			
		}
		return classContent;
	}
	
	public String getTypeName(){
		return builder.getType_Name();
	}
	
}
