package com.nosliw.core.application.entity.app.databuild;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.common.datadefinition.HAPDataDefinition;
import com.nosliw.core.data.expression.definition.HAPDefinitionRawDataExpression;

@HAPEntityWithAttribute
public class HAPDataBuild extends HAPSerializableImp{

	@HAPAttribute
	public static final String DATADEFINITION = "dataDefinition";

	@HAPAttribute
	public static final String EXPRESSIONTYPE = "expressionType";
	
	@HAPAttribute
	public static final String EXPRESSION = "expression";
	
	private HAPDataDefinition m_dataDefinition;
	
	private String m_expressionType;

	private Map<String, HAPDataBuildExpression> m_expressions;
	
	public void setExpressionType(String expressionType) {     this.m_expressionType = expressionType;           }

	public Map<String, HAPDataBuildExpression> getExpressions() {    return this.m_expressions;     }
	public void addExpression(String type, HAPDataBuildExpression expression) {    this.m_expressions.put(type, expression);   }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		
		jsonMap.put(EXPRESSIONTYPE, this.m_expressionType);

		jsonMap.put(EXPRESSION, HAPManagerSerialize.getInstance().toStringValue(this.m_expressions, HAPSerializationFormat.JSON));

		if(this.m_dataDefinition!=null) {
			jsonMap.put(DATADEFINITION, HAPManagerSerialize.getInstance().toStringValue(this.m_dataDefinition, HAPSerializationFormat.JSON));
		}
		
	}

	public static HAPDataBuild buildStoryValueChosen(JSONObject jsonObj, HAPServiceParseEntity parseService) {
		HAPDataBuild out = new HAPDataBuild();
		
		out.setExpressionType(jsonObj.getString(HAPDataBuild.EXPRESSIONTYPE));
		
		Object constantExpressionObj = jsonObj.opt(HAPDataBuild.CONSTANTEXPRESSION);
		if(constantExpressionObj!=null) {
			HAPDefinitionRawDataExpression constantExpression = HAPDefinitionRawDataExpression.buildRawDataExpression((JSONObject)constantExpressionObj, parseService);
			out.setConstantExpression(constantExpression);
		}
		
		Object varExpressionObj = jsonObj.opt(HAPDataBuild.VARIABLEEXPRESSION);
		if(varExpressionObj!=null) {
			HAPDefinitionRawDataExpression varExpression = HAPDefinitionRawDataExpression.buildRawDataExpression((JSONObject)varExpressionObj, parseService);
			out.setVariableExpression(varExpression);
		}
		
		return out;
	}
	
}
