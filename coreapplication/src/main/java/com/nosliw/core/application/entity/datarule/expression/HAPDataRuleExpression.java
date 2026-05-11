package com.nosliw.core.application.entity.datarule.expression;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.dataexpression.definition.HAPDefinitionDataExpression;
import com.nosliw.core.application.common.dataexpression.definition.HAPParserDataExpression;
import com.nosliw.core.application.entity.datarule.HAPDataRule;
import com.nosliw.core.application.entity.datarule.HAPParserDataRule;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPDataRuleExpression extends HAPDataRule{

	@HAPAttribute
	public static String EXPRESSION = "expression";

	
	private HAPDefinitionDataExpression m_expressionDef;

	public HAPDataRuleExpression() {
		super(HAPConstantShared.DATARULE_TYPE_EXPRESSION);
	}

	public HAPDataRuleExpression(HAPDefinitionDataExpression expression) {
		this();
		this.m_expressionDef = expression;
	}
	
	public HAPDefinitionDataExpression getExpressionDefinition() {   return this.m_expressionDef;   }
	public void setExpressionDefinition(HAPDefinitionDataExpression expression) {   this.m_expressionDef = expression;    }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(EXPRESSION, HAPUtilityJson.buildJson(this.getExpressionDefinition(), HAPSerializationFormat.JSON));
	}

	@Override
	public HAPDataRule cloneDataRule() {
		HAPDataRuleExpression out = new HAPDataRuleExpression();
		this.cloneToDataRule(out);
		out.m_expressionDef = this.m_expressionDef; 
		return out;
	}
	
}

@Component
class HAPDataRuleExpression_parser extends HAPParserDataRule{

	private HAPParserDataExpression m_dataExpressionParser;

	public HAPDataRuleExpression_parser(HAPParserDataExpression dataExpressionParser) {
		this.m_dataExpressionParser = dataExpressionParser;
	}
	
	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		JSONObject jsonObj = (JSONObject)obj;
		HAPDataRuleExpression out = new HAPDataRuleExpression();
		out.setExpressionDefinition(this.m_dataExpressionParser.parseExpression(jsonObj.getString(HAPDataRuleExpression.EXPRESSION)));
		return out;
	}

	@Override
	public String getSubName() {   return HAPConstantShared.DATARULE_TYPE_EXPRESSION;   }

}
