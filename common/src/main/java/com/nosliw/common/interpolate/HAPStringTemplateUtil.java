package com.nosliw.common.interpolate;

import java.io.InputStream;
import java.util.Map;

import com.nosliw.common.utils.HAPUtilityBasic;
import com.nosliw.common.utils.HAPUtilityFile;

public class HAPStringTemplateUtil {

	public static final String SEPERATOR = "||";

	public static String getStringValue(InputStream templateStream, Map<String, String> parms){
		return getStringValue(templateStream, parms, true);
	}
	
	public static String getStringValue(InputStream templateStream, Map<String, String> parms, boolean clean){
		String template = HAPUtilityFile.readFile(templateStream);
		return getStringValue(template, parms, clean);
	}
	
	public static String getStringValue(String template, Map<String, String> parms){
		return getStringValue(template, parms, true);
	}

	public static String getStringValue(String template, Map<String, String> parms, boolean clean){
		if(HAPUtilityBasic.isStringNotEmpty(template)){
			for(String key : parms.keySet()){
				String fill = parms.get(key);
				if(fill==null) {
					fill = "";
				}
				template = template.replace(SEPERATOR+key+SEPERATOR, fill);
			}
		}
		if(clean==true) {
			template = clearPlaceholder(template);
		}
		return template;
	}

	
	private static String clearPlaceholder(String template) {
		StringBuffer out = new StringBuffer();
		
		int index1 = -1;
		int start = 0;
		do {
			index1 = template.indexOf(SEPERATOR, start);
			if(index1!=-1) {
				out.append(template.substring(start, index1));
				start = template.indexOf(SEPERATOR, index1+SEPERATOR.length())+SEPERATOR.length();
			}
		}while(index1!=-1);
		
		out.append(template.substring(start));
		return out.toString();
	}


}
