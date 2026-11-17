package com.nosliw.core.application.entity.jslibrary;

public class HAPUtilityJSLibrary {

	public static String getBrowserScriptPath(String fileName){
		String keyword = "webapp";
		return fileName.substring(fileName.indexOf(keyword)+keyword.length()+1);
	}
	
}
