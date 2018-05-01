package com.yu.entitygenerator.executor;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import com.yu.entitygenerator.builder.BuilderMateHandler;
import com.yu.entitygenerator.builder.EnumBuilder;
import com.yu.entitygenerator.datasource.DBInfoHandler;
import com.yu.entitygenerator.datasource.DBMS;
import com.yu.entitygenerator.datasource.InfoHandlerFactory;
import com.yu.entitygenerator.io.ResourceAcquirer;
import com.yu.entitygenerator.io.TypeSourceWriter;
import com.yu.entitygenerator.util.TimeLogger;

/**
 * 自动生成器的程序执行入口
 * @author yl 
 * @version 1.0 2017年8月30日
 * 
 */
public class MainExecutor {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		
		ResourceAcquirer.initialization();
		Scanner sc = new Scanner(System.in);
		System.out.println("生成实体类型：A  生成实体字典：B 输错退出");
		String option = sc.nextLine();
		if(option.equals("A")) {
			
			//=============================生成实体============================
			DBInfoHandler dbInfoHandler =  InfoHandlerFactory.getHandler(DBMS.MYSQL.value);
			Objects.requireNonNull(dbInfoHandler, "DBInfoHandler创建失败，请检查数据库类型是否正确");
			List<String> table_List = (List<String>) dbInfoHandler.getTableList();
			if(!ResourceAcquirer.auto){
				TimeLogger.info("正在执行手动模式......");
				//手动执行会在生成java文件前询问
				a:
				for(String table_Name:table_List){
					BuilderMateHandler buidler = new BuilderMateHandler(table_Name, (List<String[]>) dbInfoHandler.getTableInfo(table_Name));
					TypeSourceWriter writer = new TypeSourceWriter();
					String type_Body = buidler.build(ResourceAcquirer.fieldPreview);
					TimeLogger.info("请核对表和预览。是否生成"+buidler.getTypeName()+".java?(Y/N,E:终止任务。不区分大小写)");
					boolean b = true;
					while(b){
						option = sc.nextLine();
						switch(option){
						case "y":
						case "Y":writer.writeType(type_Body,buidler.getTypeName(),ResourceAcquirer.cover,false,".java");b=false;break;
						case "n":
						case "N":TimeLogger.info("取消生成"+buidler.getTypeName()+".java");b=false;break;
						case "e":
						case "E":TimeLogger.info("终止任务");b=false;break a;
						default:TimeLogger.info("输入命令不正确：Y/N");
						}
					}
				}
			}else{
				//自动执行是多个线程进行的
				Thread[] pool = new Thread[table_List.size()];
				int i = 0;
				for(String table_Name:table_List){
					List<String[]> column_List =  (List<String[]>) dbInfoHandler.getTableInfo(table_Name);
					SubExecutor executor = new SubExecutor(table_Name,column_List);
					pool[i] = executor;
					executor.start();
					i++;
				}
				boolean isAlive = true;
				while(isAlive){
					for(Thread t:pool){
						if(t.isAlive()){
							isAlive = true;
							break;
						}
						isAlive = false;
					}
					Thread.sleep(2000);
					TimeLogger.info("等待所有线程结束");	
				}
				TimeLogger.info("线程已结束");
				pool = null;
				TimeLogger.info("类已创建好，请刷新项目");			
			}
			dbInfoHandler.close();
			dbInfoHandler = null;
		
			
			//============================生成字典=============================
		}else if(option.equals("B")){
			
			File file = new File(System.getProperty("user.dir")+ResourceAcquirer.rootEntityPackage);
			System.out.println("获取实体："+System.getProperty("user.dir")+ResourceAcquirer.rootEntityPackage);
			HashMap<String, String> map = new HashMap<>();
			try {
				for(String fileName:file.list()) {
					if(fileName.endsWith("java")) {
						String className = new String(fileName.substring(0, fileName.lastIndexOf(".")));
						System.out.println(fileName);
						map.put(className, className);
					}	
				}
				
				System.out.println("单独生成某个字典？Y/N,默认N");
				EnumBuilder eb = new EnumBuilder();
				TypeSourceWriter ts = new TypeSourceWriter();
				if(sc.nextLine().equals("Y")) {
					eb.buildEntityPackage();
					System.out.println("请输入一个类型名");
					option = sc.nextLine();
					for(int i = 1;map.get(option)==null;i++) {
						System.out.println("输入类型错误，请重新输入,连续5次退出");
						if(i==5) {
							System.out.println("你是在逗我？");
							break;
						}
					}
					if(option!=null) {
						System.out.println("确认覆盖？Y/N，默认N");
						boolean isCover = false;
						if(sc.nextLine().equals("Y"))
							isCover = true;
						ts.writeType(eb.buildEnum(eb.getType(option)),ResourceAcquirer.dictStartWith+option+ResourceAcquirer.dictEndWith, isCover, true, ".java");						
					}
					
				}else {
					System.out.println("执行自动生成字典并覆盖");
					eb.buildEntityPackage();
					for(String key:map.keySet()) {
						//System.out.println(key);
						ts.writeType(eb.buildEnum(eb.getType(key)), ResourceAcquirer.dictStartWith+key+ResourceAcquirer.dictEndWith, true, true, ".java");	
					}
				}
			}catch(Exception e) {
				
			}
		}
		
	}
}
