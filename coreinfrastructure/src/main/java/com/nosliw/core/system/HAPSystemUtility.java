package com.nosliw.core.system;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class HAPSystemUtility {
	
	static private Properties prop;
	
	static {
		InputStream input;
		try {
			input = new FileInputStream("C:/nosliw_configure/configure.properties");
	        prop = new Properties();
	        prop.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//
	public static boolean getConsolidateLib() {   return Boolean.valueOf(prop.getProperty("ConsolidateLib", "false"));   }

}
