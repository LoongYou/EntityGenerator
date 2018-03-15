package com.yu.entitygenerator.type;

import com.yu.entitygenerator.io.ResourceAcquirer;

/**
 * ʵ�����͵��ֶ����Ͷ����ת��
* @author yl  
* @version 1.0 2017��8��28��
*    
*/
public class TypeHandler {
	private boolean ByteORbyte = ResourceAcquirer.ByteORbyte;
	
	/**
	 * �������͵�ȫ�����ض�������͵��ַ��������Ϊ��װ�࣬���Զ���ȡ������TYPEʵ�������򷵻�SimpleName(����"[]"�������)��
	 * ������������⣬���ؿհ��ַ�
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
