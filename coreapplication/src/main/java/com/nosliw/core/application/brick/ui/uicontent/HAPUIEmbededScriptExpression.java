package com.nosliw.core.application.brick.ui.uicontent;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;

@HAPEntityWithAttribute
public class HAPUIEmbededScriptExpression extends HAPSerializableImp implements HAPWithUIId{

	@HAPAttribute
	static final public String SCRIPTID = "scriptId";  
	
	private String m_uiId;
	
	private String m_scriptId;

	public HAPUIEmbededScriptExpression() {}

	public HAPUIEmbededScriptExpression(String uiId, String scriptId) {
		this.m_uiId = uiId;
		this.m_scriptId = scriptId;
	}
	
	@Override
	public String getUIId() {   return this.m_uiId;  }
	public void setUIId(String uiId) {     this.m_uiId = uiId;      }
	
	public String getScriptId() {    return this.m_scriptId;     }
	public void setScriptId(String scriptId) {     this.m_scriptId = scriptId;       }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(UIID, this.m_uiId);
		jsonMap.put(SCRIPTID, this.m_scriptId);
	}

	@Override
	protected boolean buildObjectByJson(Object json){
		super.buildObjectByJson(json);
		JSONObject jsonObj = (JSONObject)json;
		
		this.setUIId((String)jsonObj.opt(UIID));
		this.setScriptId((String)jsonObj.opt(SCRIPTID));
		
		return true;  
	}

}
