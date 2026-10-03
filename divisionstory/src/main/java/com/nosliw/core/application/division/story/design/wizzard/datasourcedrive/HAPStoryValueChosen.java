package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.data.expression.definition.HAPDefinitionRawDataExpression;

@HAPEntityWithAttribute
public class HAPStoryValueChosen extends HAPSerializableImp{

	@HAPAttribute
	public static final String EXPRESSIONTYPE = "expressionType";
	
	@HAPAttribute
	public static final String CONSTANTEXPRESSION = "constantExpression";
	
	@HAPAttribute
	public static final String VARIABLEEXPRESSION = "variableExpression";
	
	private String m_expressionType;
	
	private HAPDefinitionRawDataExpression m_constantExpression;
	
	private HAPDefinitionRawDataExpression m_variableExpression;

	public void setExpressionType(String expressionType) {     this.m_expressionType = expressionType;           }

	public HAPDefinitionRawDataExpression getConstantExpression() {    return this.m_constantExpression;     }
	public void setConstantExpression(HAPDefinitionRawDataExpression data) {    this.m_constantExpression = data;       }
	
	public HAPDefinitionRawDataExpression getVariableExpression() {     return this.m_variableExpression;     }
	public void setVariableExpression(HAPDefinitionRawDataExpression expression) {     this.m_variableExpression = expression;        }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		
		jsonMap.put(EXPRESSIONTYPE, this.m_expressionType);
		
		if(this.m_constantExpression!=null) {
			jsonMap.put(CONSTANTEXPRESSION, HAPManagerSerialize.getInstance().toStringValue(m_constantExpression, HAPSerializationFormat.JSON));
		}
		
		if(this.m_variableExpression!=null) {
			jsonMap.put(CONSTANTEXPRESSION, HAPManagerSerialize.getInstance().toStringValue(m_variableExpression, HAPSerializationFormat.JSON));
		}
	}

	public static HAPStoryValueChosen buildStoryValueChosen(JSONObject jsonObj, HAPServiceParseEntity parseService) {
		HAPStoryValueChosen out = new HAPStoryValueChosen();
		
		out.setExpressionType(jsonObj.getString(HAPStoryValueChosen.EXPRESSIONTYPE));
		
		Object constantExpressionObj = jsonObj.opt(HAPStoryValueChosen.CONSTANTEXPRESSION);
		if(constantExpressionObj!=null) {
			HAPDefinitionRawDataExpression constantExpression = HAPDefinitionRawDataExpression.buildRawDataExpression((JSONObject)constantExpressionObj, parseService);
			out.setConstantExpression(constantExpression);
		}
		
		Object varExpressionObj = jsonObj.opt(HAPStoryValueChosen.VARIABLEEXPRESSION);
		if(varExpressionObj!=null) {
			HAPDefinitionRawDataExpression varExpression = HAPDefinitionRawDataExpression.buildRawDataExpression((JSONObject)varExpressionObj, parseService);
			out.setVariableExpression(varExpression);
		}
		
		return out;
	}
	
}
