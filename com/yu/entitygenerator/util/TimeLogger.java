package com.yu.entitygenerator.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 一个简单包装进度信息输出控制台的工具，因暂时没有需要写入日志文件，仅输出控制台
 * @author yl 
 * 
 */
public class TimeLogger {
	private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	public static void info(String str){
		Date date = new Date();
		System.out.println("INFO:"+sdf.format(date)+"---"+str);
	}
}
