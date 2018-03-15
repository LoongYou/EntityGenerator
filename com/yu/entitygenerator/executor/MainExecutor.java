package com.yu.entitygenerator.executor;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import com.yu.entitygenerator.builder.BuilderMateHandler;
import com.yu.entitygenerator.datasource.DBInfoHandler;
import com.yu.entitygenerator.datasource.DBMS;
import com.yu.entitygenerator.datasource.InfoHandlerFactory;
import com.yu.entitygenerator.io.ResourceAcquirer;
import com.yu.entitygenerator.io.TypeSourceWriter;
import com.yu.entitygenerator.util.TimeLogger;

/**
 * �Զ��������ĳ���ִ�����
 * @author yl 
 * @version 1.0 2017��8��30��
 * 
 */
public class MainExecutor {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		
		ResourceAcquirer.initialization();
		DBInfoHandler dbInfoHandler =  InfoHandlerFactory.getHandler(DBMS.MYSQL.value);
		Objects.requireNonNull(dbInfoHandler, "DBInfoHandler����ʧ�ܣ��������ݿ������Ƿ���ȷ");
		List<String> table_List = (List<String>) dbInfoHandler.getTableList();
		if(!ResourceAcquirer.auto){
			TimeLogger.info("����ִ���ֶ�ģʽ......");
			//�ֶ�ִ�л�������java�ļ�ǰѯ��
			Scanner sc = new Scanner(System.in);
			a:
			for(String table_Name:table_List){
				BuilderMateHandler buidler = new BuilderMateHandler(table_Name, (List<String[]>) dbInfoHandler.getTableInfo(table_Name));
				TypeSourceWriter writer = new TypeSourceWriter();
				String type_Body = buidler.build(ResourceAcquirer.fieldPreview);
				TimeLogger.info("��˶Ա��Ԥ�����Ƿ�����"+buidler.getTypeName()+".java?(Y/N,E:��ֹ���񡣲����ִ�Сд)");
				boolean b = true;
				while(b){
					String option = sc.nextLine();
					switch(option){
					case "y":
					case "Y":writer.writeType(type_Body,buidler.getTypeName(),ResourceAcquirer.cover);b=false;break;
					case "n":
					case "N":TimeLogger.info("ȡ������"+buidler.getTypeName()+".java");b=false;break;
					case "e":
					case "E":TimeLogger.info("��ֹ����");b=false;break a;
					default:TimeLogger.info("���������ȷ��Y/N");
					}
				}
			}
		}else{
			//�Զ�ִ���Ƕ���߳̽��е�
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
				TimeLogger.info("�ȴ������߳̽���");	
			}
			TimeLogger.info("�߳��ѽ���");
			pool = null;
			TimeLogger.info("���Ѵ����ã���ˢ����Ŀ");			
		}
		dbInfoHandler.close();
		dbInfoHandler = null;
	}
}
