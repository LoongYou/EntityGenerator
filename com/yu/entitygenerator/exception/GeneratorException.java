package com.yu.entitygenerator.exception;

import com.yu.entitygenerator.datasource.DBInfoHandler;

public class GeneratorException extends DBInfoHandler{

	public GeneratorException() throws Exception {
		throw new Exception();
	}
	
}
