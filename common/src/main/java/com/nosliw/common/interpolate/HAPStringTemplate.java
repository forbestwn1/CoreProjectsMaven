package com.nosliw.common.interpolate;

import java.io.File;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.utils.HAPUtilityFile;

public class HAPStringTemplate {

	private String m_template;
	
	private Map<String, String> m_parms = new LinkedHashMap<String, String>();
	
	public HAPStringTemplate(String template){
		this.m_template = template;
	}

	public HAPStringTemplate(InputStream stream){
		this.m_template = HAPUtilityFile.readFile(stream);
	}
	
	public HAPStringTemplate(File file){
		this.m_template = HAPUtilityFile.readFile(file);
	}

	public HAPStringTemplate setParm(String name, String value) {
		this.m_parms.put(name, value);
		return this;
	}
	
	public String getContent() {
		return HAPStringTemplateUtil.getStringValue(m_template, m_parms);
	}
	
}
