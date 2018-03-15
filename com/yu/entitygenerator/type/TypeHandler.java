package com.yu.entitygenerator.type;

import com.yu.entitygenerator.io.ResourceAcquirer;

/**
 * 实现类型到字段类型定义的转换
* @author yl  
* @version 1.0 2017年8月28日
*    
*/
public class TypeHandler {
	private boolean ByteORbyte = ResourceAcquirer.ByteORbyte;
	
	/**
	 * 根据类型的全名返回定义该类型的字符串，如果为包装类，则自动获取并返回TYPE实例，否则返回SimpleName(包含"[]"，如果有)，
	 * 如果处理发生意外，返回空白字符
	 * @param qName
	 * @return
	 */
	public String convert(String qName){
		String tail = "";
		if(qName.contains("[]")){
			qName = qName.substring(0,qName.lastIndexOf("["));
			tail = "[]";
		}
		Class<?> tempClass = null;
		try {
			tempClass = Class.forName(qName);
			if(ByteORbyte){
				return tempClass.getSimpleName()+tail;
			}
			return tempClass.getDeclaredField("TYPE").get(tempClass).toString()+tail;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			return tempClass.getSimpleName()+tail;
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return "";
	}
}
