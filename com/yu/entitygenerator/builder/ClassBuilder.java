package com.yu.entitygenerator.builder;

import java.io.File;
import java.util.Map;

import com.yu.entitygenerator.io.ResourceAcquirer;
import com.yu.entitygenerator.type.TypeHandler;
import com.yu.entitygenerator.util.TimeLogger;

/**
 * 此类提供构建实体类属性和方法的功能，但不包括写入文件功能，
 * 建议在调用其buildEntity方法后，释放该类对象并重新创建，如需复用。
 * 在多线程下是不安全的，应当避免多个线程同时操作该对象
 * @author yl 
 * @version 1.0 2017年8月27日
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
	 * 如果表名是SYS_user形式的，则会取得user并且首字母大写User
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
	 * 完成一个实体类中属性、getter、setter和包的定义，在最初它会创建对应的包
	 * @param table_Name
	 * @return
	 */
	public String buildEntity(String table_Name){
		buildEntityPackage();
		type_Name = convertTypeName(table_Name);
		TimeLogger.info("开始构建类型："+type_Name);
		typeContent.append("public class ").append(type_Name).append("{\n");
		typeContent.append("    \n");
		typeContent.append(fieldContent+"\n");
		typeContent.append(methodContent+"\n");
		typeContent.append("}");
		typeContent.insert(0, "package "+entityPackage.replaceAll("/", ".")+";\n\n");
		typeContent.insert(typeContent.indexOf("public class")-1,"\n\n/**\n * @author entity-generator\n * \n */\n");
		TimeLogger.info("类型构建完成："+type_Name);
		return typeContent.toString();
	}
	
	/**
	 * 构建并返回一个属性行及其对应的setter和getter方法,可通过重复调用，自动拼接到上一条属性后面
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
	 * 构建并返回一对setter、getter,可通过重复调用，自动拼接到上一对方法后面
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
	 * 根据表字段类型转化为java类型，如果在xml文件中没有设置对应的java类型，返回空字符串，该方法会同时将lang包以外的类型导入到类的头部
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
			if(qName.contains("java.lang.")||qName.contains("com.yu.entitygenerator.type")){	
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
	 * 创建实体类的包
	 * @return
	 */
	public void buildEntityPackage(){
		String packagePath = System.getProperty("user.dir")+rootEntityPackage;
		File file = new File(packagePath);
		if(!file.exists()){
			TimeLogger.info("创建包路径"+packagePath);
			file.mkdirs();
		}
	}
	
	

	
	
}
