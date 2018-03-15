package com.yu.entitygenerator.builder;

import java.io.File;
import java.util.Map;

import com.yu.entitygenerator.io.ResourceAcquirer;
import com.yu.entitygenerator.type.TypeHandler;
import com.yu.entitygenerator.util.TimeLogger;

/**
 * �����ṩ����ʵ�������Ժͷ����Ĺ��ܣ���������д���ļ����ܣ�
 * �����ڵ�����buildEntity�������ͷŸ���������´��������踴�á�
 * �ڶ��߳����ǲ���ȫ�ģ�Ӧ���������߳�ͬʱ�����ö���
 * @author yl 
 * @version 1.0 2017��8��27��
 * 
 */
public class ClassBuilder {
	
	private String type_Name;
	
	public String getType_Name() {
		return type_Name;
	}

	public void setType_Name(String type_Name) {
		this.type_Name = type_Name;
	}
    
	public StringBuilder getFieldContent() {
		return fieldContent;
	}

	public void setFieldContent(StringBuilder fieldContent) {
		this.fieldContent = fieldContent;
	}

	private StringBuilder typeContent = new StringBuilder();
	private StringBuilder fieldContent = new StringBuilder();
	private StringBuilder methodContent = new StringBuilder();
	private Map<String,String> dataType = ResourceAcquirer.dataType;
	private TypeHandler typeHandler = new TypeHandler();
	private String rootEntityPackage = ResourceAcquirer.rootEntityPackage;
	private String entityPackage = ResourceAcquirer.entityPackage;
	
	
	/**
	 * ���������SYS_user��ʽ�ģ����ȡ��user��������ĸ��дUser
	 * @param table_Name
	 * @return
	 */
	public String convertTypeName(String table_Name){
		char[] c = table_Name.substring(table_Name.indexOf("_")+1, table_Name.length()).toCharArray();
		if(c[0]>='a'&&c[0]<='z'){//65 97
			c[0]-=32;
		}
		for(int i = 0;i<c.length;i++){
			if(c[i]>='A'&&c[i]<='Z'&&i>0){
				c[i]+=0x20;
			}
			
		}
		return new String(c);
	}
	
	/**
	 * ���һ��ʵ���������ԡ�getter��setter�Ͱ��Ķ��壬��������ᴴ����Ӧ�İ�
	 * @param table_Name
	 * @return
	 */
	public String buildEntity(String table_Name){
		buildEntityPackage();
		type_Name = convertTypeName(table_Name);
		TimeLogger.info("��ʼ�������ͣ�"+type_Name);
		typeContent.append("public class ").append(type_Name).append("{\n");
		typeContent.append("    \n");
		typeContent.append(fieldContent+"\n");
		typeContent.append(methodContent+"\n");
		typeContent.append("}");
		typeContent.insert(0, "package "+entityPackage.replaceAll("/", ".")+";\n\n");
		typeContent.insert(typeContent.indexOf("public class")-1,"\n\n/**\n * @author auto-generator\n * \n */\n");
		TimeLogger.info("���͹�����ɣ�"+type_Name);
		return typeContent.toString();
	}
	
	/**
	 * ����������һ�������м����Ӧ��setter��getter����,��ͨ���ظ����ã��Զ�ƴ�ӵ���һ�����Ժ���
	 * @param column_Name
	 * @param Column_comment
	 * @param Column_Type
	 * @return
	 */
	public String bulidField(String column_Name,String column_Comment,String column_Type){
		String before = "    ";
		String after = "\n";
		String field_Type = buildFieldType(column_Type);
		fieldContent.append(before+"private "+field_Type+" "+column_Name+";//"+column_Comment+after);
		buildSetterAndGetter(column_Name,field_Type);
		return fieldContent.toString();
	}
	
	/**
	 * ����������һ��setter��getter,��ͨ���ظ����ã��Զ�ƴ�ӵ���һ�Է�������
	 * @param column_Name
	 * @param field_Type
	 * @return
	 */
	public String buildSetterAndGetter(String column_Name,String field_Type){
		String before = "    ";
		char[] c = column_Name.toCharArray();
		if(c[0]>='a'&&c[0]<='z'){
			c[0]-=32;
		}
		methodContent.append(before+"public "+field_Type+" get").append(c).append("(){\n");
		methodContent.append(before+before+"return "+column_Name+";\n"+before+"}\n");
		methodContent.append(before+"public void "+"set").append(c).append("("+field_Type+" "+column_Name+"){\n");
		methodContent.append(before+before+"this."+column_Name+" = "+column_Name+";\n"+before+"}\n");
		return methodContent.toString();
	}
	
	/**
	 * ���ݱ��ֶ�����ת��Ϊjava���ͣ������xml�ļ���û�����ö�Ӧ��java���ͣ����ؿ��ַ������÷�����ͬʱ��lang����������͵��뵽���ͷ��
	 * @param Column_Type
	 * @return
	 */
	public String buildFieldType(String Column_Type){
		String temp = Column_Type;
		if(Column_Type.contains("(")){
			temp = Column_Type.substring(0,Column_Type.indexOf('('));
		}
		String type = temp.toUpperCase();
		if(dataType.containsKey(type)){
			String qName = dataType.get(type);
			if(qName.contains("java.lang.")||qName.contains("com.yu.autogenerator.type")){	
				return typeHandler.convert(qName);
			}else{
				String importTag = "import "+qName+";\n";
				if(typeContent.indexOf(importTag)==-1){
					typeContent.append(importTag);								
				}
				return typeHandler.convert(qName);
			}								
		}else{
			return "";			
		}
	}
	
	/**
	 * ����ʵ����İ�
	 * @return
	 */
	public void buildEntityPackage(){
		String packagePath = System.getProperty("user.dir")+rootEntityPackage;
		File file = new File(packagePath);
		if(!file.exists()){
			TimeLogger.info("������·��"+packagePath);
			file.mkdirs();
		}
	}
	
	

	
	
}
