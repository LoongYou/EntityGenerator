package com.yu.entitygenerator.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.yu.entitygenerator.util.TimeLogger;

/**
 * 实现java类型文件的写入
* @author yl  
* @version 1.0 2017年8月29日
*    
*/
public class TypeSourceWriter {
	
	private String rootEntityPackage = ResourceAcquirer.rootEntityPackage;
	
	/**
	 * 将类型写到文件，如果类型名称过长会抛出ArrayIndexOutOfBoundsException，该方法中使用的缓冲大小为4k
	 * @param type_Body
	 * @param type_Name
	 * @param isCover 是否执行覆盖已有文件
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
				TimeLogger.info(type_Name+".java已存在,不进行覆盖");				
			}else if((file.exists()&&isCover)||!file.exists()){
				if(file.exists()&&isCover){
					TimeLogger.info(type_Name+".java已存在,进行覆盖");						
				}
				try {
					out = new FileOutputStream(file);
					buffer = new BufferedOutputStream(out,4096);
					byte[] b = type_Body.getBytes();
					buffer.write(b);
					TimeLogger.info(type_Name+".java已生成");	
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
