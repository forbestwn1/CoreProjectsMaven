package com.nosliw.core.application.brick.ui.uicontent;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;

public class HAPUIEmbededScriptExpressionInAttribute extends HAPUIEmbededScriptExpression{

	@HAPAttribute
	static final public String ATTRIBUTE = "attribute";  

	//attribute name
	private String m_attribute;

	public HAPUIEmbededScriptExpressionInAttribute() {}

	public HAPUIEmbededScriptExpressionInAttribute(String attribute, String uiId, String scriptId) {
		super(uiId, scriptId);
		this.m_attribute = attribute;
	}

	public String getAttribute() {		return this.m_attribute;	}
	public void setAttribute(String attr) {    this.m_attribute = attr;        }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(ATTRIBUTE, this.m_attribute);
	}

	@Override
	protected boolean buildObjectByJson(Object json){
		super.buildObjectByJson(json);
		JSONObject jsonObj = (JSONObject)json;
		
		this.setAttribute((String)jsonObj.opt(ATTRIBUTE));
		
		return true;  
	}
}
