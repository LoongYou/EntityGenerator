package com.yu.entitygenerator.exception;

public class NoSuchDBTypeException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSuchDBTypeException() {
		System.out.println("找不到此数据库类型");
	}
}
