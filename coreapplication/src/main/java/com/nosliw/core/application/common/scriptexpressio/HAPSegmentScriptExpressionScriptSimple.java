package com.nosliw.core.application.common.scriptexpressio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.common.utils.HAPConstantShared;

public class HAPSegmentScriptExpressionScriptSimple extends HAPSegmentScriptExpressionScript{

	@HAPAttribute
	public static String PART = "part";

	//string, constant or variable
	private List<Object> m_parts;

	public HAPSegmentScriptExpressionScriptSimple() {}
	
	public HAPSegmentScriptExpressionScriptSimple(String id) {
		super(id);
		this.m_parts = new ArrayList<Object>();
	}
	
	@Override
	public String getType() {  return HAPConstantShared.EXPRESSION_SEG_TYPE_SCRIPTSIMPLE;  }

	public List<Object> getParts(){   return this.m_parts;    }
	
	public void addPart(Object part) {    this.m_parts.add(part);    }
	
	public Set<String> getVariableKeys(){
		Set<String> out = new HashSet<String>();
		for(Object part : this.m_parts) {
			if(part instanceof HAPVariableInScript) {
				out.add(((HAPVariableInScript)part).getVariableKey());
			}
		}
		return out;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		List<String> partJsonArray = new ArrayList<String>();
		for(Object part : this.m_parts) {
			partJsonArray.add(HAPManagerSerialize.getInstance().toStringValue(part, HAPSerializationFormat.JSON));
		}
		jsonMap.put(PART, HAPUtilityJson.buildArrayJson(partJsonArray.toArray(new String[0])));
	}

	public void updateConstantValue(Map<String, Object> constantsValue) {
		List<Object> newEles = new ArrayList<Object>();
		for(Object ele : this.m_parts) {
			if(ele instanceof HAPVariableInScript) {
				HAPVariableInScript varEle = (HAPVariableInScript)ele;
				Object constantValue = constantsValue.get(varEle.getVariableName());
				if(constantValue!=null) {
					HAPConstantInScript constantEle = new HAPConstantInScript(varEle.getVariableName());
					constantEle.setValue(constantValue);
				}
				else {
					newEles.add(ele);
				}
			}
			else if(ele instanceof HAPConstantInScript) {
				HAPConstantInScript constantEle = (HAPConstantInScript)ele;
				constantEle.setValue(constantsValue.get(constantEle.getConstantName()));
				newEles.add(constantEle);
			}
			else {
				newEles.add(ele);
			}
		}
		this.m_parts = newEles;
	}
	
}

@Component
class HAPSegmentScriptExpressionScriptSimple_parser extends HAPSegmentScriptExpression_parser{

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPSegmentScriptExpressionScriptSimple out = new HAPSegmentScriptExpressionScriptSimple();
		JSONObject jsonObj = (JSONObject)obj;
		
		this.parseToSegmentJson(jsonObj, out, parseService);
		
		JSONArray partsJsonArray = jsonObj.optJSONArray(HAPSegmentScriptExpressionScriptSimple.PART);
		for(int i=0; i<partsJsonArray.length(); i++) {
			Object partObj = partsJsonArray.get(i);
			if(partObj instanceof JSONObject) {
				JSONObject partJsonObj = (JSONObject)partObj;
				if(partJsonObj.opt(HAPConstantInScript.CONSTANTNAME)!=null) {
					//constant
					HAPConstantInScript constant = new HAPConstantInScript();
					constant.buildObject(constant, HAPSerializationFormat.JSON);
					out.addPart(constant);
				}
				else {
					//variable
					HAPVariableInScript variable = new HAPVariableInScript();
					variable.buildObject(variable, HAPSerializationFormat.JSON);
					out.addPart(variable);
				}
			}
			else {
				out.addPart(partObj);
			}
		}
		
		return out;
	}

	@Override
	public String getSubName() {   return HAPConstantShared.EXPRESSION_SEG_TYPE_SCRIPTSIMPLE;   }
	
}
