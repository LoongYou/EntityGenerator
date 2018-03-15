package com.yu.entitygenerator.io;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.yu.entitygenerator.util.TimeLogger;

/**
 * ���Զ���������ʼ����ǰ���ɸ���ʵ�������ļ��Ķ�ȡ���Լ���ʼ��һЩ�����ľ�̬�ĳ�����
 * @author yl 
 * @version 1.0 2017��8��31��
 * 
 */
public class ResourceAcquirer {
	
	//��Щ�Ƕ�Ӧ�������ļ�����Ŀ
	public static String dbms;
	public static String driver;
	public static String url;
	public static String param;
	public static String username;
	public static String password;
	public static String projectRoot;
	public static String entityPackage;
	public static String rootEntityPackage;
	public static boolean auto;
	public static boolean cover;
	public static boolean fieldPreview;
	public static boolean ByteORbyte;
	public static String scan;
	
	//��datatype.xml��ת�����ĵ������ͼ���
	public static Map<String,String> dataType = new HashMap<String,String>();
	
	
	/**
	 * ��ʼ����������
	 * @throws Exception
	 */
	public static void initialization() throws Exception{
		
			ResourceBundle rb = ResourceBundle.getBundle("entitygenerator");
			TimeLogger.info("����������");
			
			dbms = rb.getString("dbms");
			driver = rb.getString("driver");
			url = rb.getString("url");
			param = rb.getString("param");
			username = rb.getString("username");
			password = rb.getString("password");
			entityPackage = rb.getString("entityPackage");
			projectRoot = "\\"+rb.getString("projectRoot")+"\\"; 
			rootEntityPackage = "\\"+projectRoot+"\\"+entityPackage+"\\";
			auto = Boolean.valueOf(rb.getString("auto"));
			cover = Boolean.valueOf(rb.getString("cover"));
			fieldPreview = Boolean.valueOf(rb.getString("fieldPreview"));
			ByteORbyte = Boolean.valueOf(rb.getString("ByteORbyte"));
			scan = rb.getString("scan");
			
			
			TimeLogger.info("��ʼ����������");
			
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(System.getProperty("user.dir")+projectRoot+"datatype.xml"));
			Element dataMapper = document.getRootElement();
			Iterator<Element> i = dataMapper.elementIterator();
			while(i.hasNext()){
				Iterator<Attribute> atts = i.next().attributeIterator();
				while(atts.hasNext()){
					String first = atts.next().getValue();
					String second = atts.next().getValue();
					dataType.put(first, second);
					//System.out.println(first+second);
				}				
			}
			TimeLogger.info("-------------��ʼ�����-------------");
	}
	
	
}
