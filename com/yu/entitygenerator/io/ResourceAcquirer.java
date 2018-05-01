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
 * 在自动生成器开始任务前，由该类实现配置文件的读取，以及初始化一些公共的静态的常量。
 * @author yl 
 * @version 1.0 2017年8月31日
 * 
 */
public class ResourceAcquirer {
	
	//这些是对应于配置文件的条目
	public static String dbms;
	public static String driver;
	public static String url;
	public static String param;
	public static String username;
	public static String password;
	public static String projectRoot;
	public static String entityPackage;
	public static String dictPackage;
	public static String dictStartWith;
	public static String dictEndWith;
	public static String rootEntityPackage;
	public static String rootDictPackage;
	public static boolean auto;
	public static boolean cover;
	public static boolean fieldPreview;
	public static boolean ByteORbyte;
	public static String scan;
	
	//从datatype.xml中转换并的到的类型集合
	public static Map<String,String> dataType = new HashMap<String,String>();
	
	
	/**
	 * 初始化所有配置
	 * @throws Exception
	 */
	public static void initialization() throws Exception{
		
			ResourceBundle rb = ResourceBundle.getBundle("entitygenerator");
			TimeLogger.info("解析配置项");
			
			dbms = rb.getString("dbms");
			driver = rb.getString("driver");
			url = rb.getString("url");
			param = rb.getString("param");
			username = rb.getString("username");
			password = rb.getString("password");
			entityPackage = rb.getString("entityPackage");
			dictPackage = rb.getString("dictPackage");
			projectRoot = "/"+rb.getString("projectRoot")+"/"; 
			rootEntityPackage = "/"+projectRoot+"/"+entityPackage+"/";
			rootDictPackage = "/"+projectRoot+"/"+dictPackage+"/";
			dictStartWith = rb.getString("dictStartWith");
			dictEndWith = rb.getString("dictEndWith");
			auto = Boolean.valueOf(rb.getString("auto"));
			cover = Boolean.valueOf(rb.getString("cover"));
			fieldPreview = Boolean.valueOf(rb.getString("fieldPreview"));
			ByteORbyte = Boolean.valueOf(rb.getString("ByteORbyte"));
			scan = rb.getString("scan");
			
			
			TimeLogger.info("初始化数据类型");
			
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
			TimeLogger.info("-------------初始化完成-------------");
	}
	
	
}
