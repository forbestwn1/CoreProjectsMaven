package com.nosliw.core.application.entity.app.databuild;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPDataBuild extends HAPSerializableImp{

	@HAPAttribute
	public static final String EXPRESSION = "expression";
	
	@HAPAttribute
	public static final String EXPRESSIONCHOSEN = "expressionChosen";
	
	private String m_expressionChosen;

	private Map<String, HAPDataBuildExpression> m_expressions;

	public HAPDataBuild() {
		this.m_expressions = new LinkedHashMap<String, HAPDataBuildExpression>();
	}

	public void setExpressionChosen(String expressionType) {     this.m_expressionChosen = expressionType;           }
	public String getExpressionChosen() {      return this.m_expressionChosen;         }

	
	
	public Map<String, HAPDataBuildExpression> getExpressions() {    return this.m_expressions;     }
	public void addExpression(String type, HAPDataBuildExpression expression) {    this.m_expressions.put(type, expression);   }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(EXPRESSIONCHOSEN, this.m_expressionChosen);
		jsonMap.put(EXPRESSION, HAPManagerSerialize.getInstance().toStringValue(this.m_expressions, HAPSerializationFormat.JSON));
	}

	public static HAPDataBuild buildDataBuild(JSONObject jsonObj, HAPServiceParseEntity parseService) {
		if(jsonObj==null) {
			return null;
		}
		
		HAPDataBuild out = new HAPDataBuild();
		
		out.setExpressionChosen((String)jsonObj.opt(HAPDataBuild.EXPRESSIONCHOSEN));

		JSONObject expressionJsonObj = jsonObj.optJSONObject(EXPRESSION);
		for(Object key : expressionJsonObj.keySet()) {
			String name = (String)key;
			out.addExpression(name, HAPDataBuildExpression.buildDataBuildExpression(expressionJsonObj.getJSONObject(name), parseService));
		}
		return out;
	}
}
