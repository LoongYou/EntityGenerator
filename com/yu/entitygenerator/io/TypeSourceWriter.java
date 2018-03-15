package com.yu.entitygenerator.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.yu.entitygenerator.util.TimeLogger;

/**
 * ʵ��java�����ļ���д��
* @author yl  
* @version 1.0 2017��8��29��
*    
*/
public class TypeSourceWriter {
	
	private String rootEntityPackage = ResourceAcquirer.rootEntityPackage;
	
	/**
	 * ������д���ļ�������������ƹ������׳�ArrayIndexOutOfBoundsException���÷�����ʹ�õĻ����СΪ4k
	 * @param type_Body
	 * @param type_Name
	 * @param isCover �Ƿ�ִ�и��������ļ�
	 * @exception ArrayIndexOutOfBoundsException
	 */
	public void writeType(String type_Body,String type_Name,boolean isCover){
		if(type_Name.length()>100){
			throw new ArrayIndexOutOfBoundsException();
		}
		OutputStream out;
		BufferedOutputStream buffer = null;
			//System.out.print(System.getProperty("user.dir")+rootEntityPackage+type_Name+".java");
			File file = new File(System.getProperty("user.dir")+rootEntityPackage+type_Name+".java");
			if(file.exists()&&!isCover){
				TimeLogger.info(type_Name+".java�Ѵ���,�����и���");				
			}else if((file.exists()&&isCover)||!file.exists()){
				if(file.exists()&&isCover){
					TimeLogger.info(type_Name+".java�Ѵ���,���и���");						
				}
				try {
					out = new FileOutputStream(file);
					buffer = new BufferedOutputStream(out,4096);
					byte[] b = type_Body.getBytes();
					buffer.write(b);
					TimeLogger.info(type_Name+".java������");	
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}catch (IOException e) {
					e.printStackTrace();
				}finally{
					try {
						buffer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}			
			}
	}
}
