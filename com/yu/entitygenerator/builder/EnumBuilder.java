package com.yu.entitygenerator.builder;

import java.io.File;
import java.lang.reflect.Field;

import com.yu.entitygenerator.io.ResourceAcquirer;
import com.yu.entitygenerator.util.TimeLogger;

public class EnumBuilder {
	private String rootDictPackage = ResourceAcquirer.rootDictPackage;
	private String dictPackage = ResourceAcquirer.dictPackage;
	private String entityPackage = ResourceAcquirer.entityPackage;
	/**
	 * 创建枚举字典的包
	 * @return
	 */
	public File buildEntityPackage(){
		String packagePath = System.getProperty("user.dir")+rootDictPackage;
		File file = new File(packagePath);
		System.out.println(packagePath);
		if(!file.exists()){
			TimeLogger.info("创建包路径"+packagePath);
			file.mkdirs();
		}
		return file;
	}
	
	/**
	 * 根据类名获取类型
	 * @param fileName
	 * @return
	 */
	public Class<?> getType(String fileName) {
		String path = new String(entityPackage.replaceAll("/", "."));
		try {
			return Class.forName(path+"."+fileName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据给定的类型构建完整的属性枚举
	 * @param c
	 * @return
	 */
	public String buildEnum(Class<?> c){
		StringBuilder enumContent = new StringBuilder();
		enumContent.append("public enum ").append(c.getSimpleName()+ResourceAcquirer.dictEndWith).append("{\n");
		enumContent.append("    \n");
		String split = c.getSimpleName()+"\n    ,";
		for(Field field:c.getDeclaredFields()) {
			enumContent.append("    ").append(split).append(field.getName()).append("\n");
			split=",";
		}
		enumContent.append("}");
		enumContent.insert(0, "package "+dictPackage.replaceAll("/", ".")+";\n\n");
		enumContent.insert(enumContent.indexOf("public enum")-1,"\n\n/**\n * @author entity-generator\n * \n */\n");
		TimeLogger.info("类型构建完成："+c.getSimpleName());
		return enumContent.toString();
	}
	
}
