package com.nosliw.common.variable;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPParserEntity;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPVariableInfo extends HAPSerializableImp implements HAPEntityParsable{

	@HAPAttribute
	public static String VARIABLEKEY = "variableKey";

	@HAPAttribute
	public static String VARIABLEID = "variableId";

	private String m_variableKey;
	
	private HAPIdVariable m_variableId;

	public HAPVariableInfo() {}

	public HAPVariableInfo(String variableKey, HAPIdVariable variableId) {
		this.m_variableKey = variableKey;
		this.m_variableId = variableId;
	}
	
	public String getVariableKey() {    return this.m_variableKey;      }
	public void setVariableKey(String varKey) {     this.m_variableKey = varKey;      }
	
	public HAPIdVariable getVariableId() {    return this.m_variableId;     }
	public void setVariableId(HAPIdVariable varId) {     this.m_variableId = varId;        }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(VARIABLEKEY, m_variableKey);
		jsonMap.put(VARIABLEID, this.m_variableId.toStringValue(HAPSerializationFormat.JSON));
	}
}

@Component
class HAPVariableInfo_parser implements HAPParserEntity{

	@Override
	public String getEntityType() {   return HAPVariableInfo.class.getName();   }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPVariableInfo out = new HAPVariableInfo();
		
		JSONObject jsonObj = (JSONObject)obj;
		
		out.setVariableKey((String)jsonObj.opt(HAPVariableInfo.VARIABLEKEY));
		
		JSONObject varIdJsonObj = jsonObj.optJSONObject(HAPVariableInfo.VARIABLEID);
		if(varIdJsonObj!=null) {
			out.setVariableId(HAPIdVariable.parseVariableIdJson(varIdJsonObj, parseService));
		}
		
		return out;
	}
	
}
