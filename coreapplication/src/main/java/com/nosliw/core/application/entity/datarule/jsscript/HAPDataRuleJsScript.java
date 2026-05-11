package com.nosliw.core.application.entity.datarule.jsscript;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.entity.datarule.HAPDataRule;
import com.nosliw.core.application.entity.datarule.HAPParserDataRule;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPDataRuleJsScript extends HAPDataRule{

	@HAPAttribute
	public static String SCRIPT = "script";

	private String m_script;
	
	public HAPDataRuleJsScript() {
		super(HAPConstantShared.DATARULE_TYPE_JSSCRIPT);
	}

	public HAPDataRuleJsScript(String script) {
		this();
		this.m_script = script;
	}

	public String getScript() {    return this.m_script;    }
	public void setScript(String script) {    this.m_script = script;     }
	
	@Override
	public HAPDataRule cloneDataRule() {
		HAPDataRuleJsScript out = new HAPDataRuleJsScript();
		this.cloneToDataRule(out);
		out.m_script = this.m_script;
		return out;
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(SCRIPT, this.getScript());
	}
	
	@Override
	public boolean buildObjectByJson(Object value) {
		JSONObject valueObj = (JSONObject)value;
		super.buildObjectByJson(valueObj);
		this.m_script = valueObj.getString(SCRIPT);
		return true;
	}

}

@Component
class HAPDataRuleJsScript_Parser extends HAPParserDataRule{

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		JSONObject jsonObj = (JSONObject)obj;
		HAPDataRuleJsScript out = new HAPDataRuleJsScript();
		out.setScript(jsonObj.getString(HAPDataRuleJsScript.SCRIPT));
		return out;
	}

	@Override
	public String getSubName() {   return HAPConstantShared.DATARULE_TYPE_JSSCRIPT;  }

}

