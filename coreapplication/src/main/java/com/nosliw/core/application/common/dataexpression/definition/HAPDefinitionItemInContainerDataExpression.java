package com.nosliw.core.application.common.dataexpression.definition;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.container.HAPItemWrapper;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPParserEntity;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.data.expression.definition.HAPDefinitionDataExpression;
import com.nosliw.core.data.expression.definition.HAPParserDataExpression;

public class HAPDefinitionItemInContainerDataExpression  extends HAPItemWrapper implements HAPEntityParsable{

	public HAPDefinitionItemInContainerDataExpression() {}
	
	public HAPDefinitionItemInContainerDataExpression(HAPDefinitionDataExpression dataExpressionDef) {
		super(dataExpressionDef);
	}

	public HAPDefinitionDataExpression getDataExpressionDefinition() {    return (HAPDefinitionDataExpression)this.getValue();     }
	public void setDataExpressionDefinition(HAPDefinitionDataExpression expression) {    this.setValue(expression);      }


}

@Component
class HAPDefinitionItemInContainerDataExpression_parser implements HAPParserEntity{

	@Autowired
	private HAPParserDataExpression m_dataExpressionParser;
	
	@Override
	public String getEntityType() {    return HAPDefinitionItemInContainerDataExpression.class.getName();   }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPDefinitionItemInContainerDataExpression out = new HAPDefinitionItemInContainerDataExpression();
		JSONObject jsonObj = (JSONObject)obj;
		out.buildEntityInfoByJson(jsonObj);
		
		String dataExpressionStr = jsonObj.getString(HAPItemWrapper.VALUE);
		HAPDefinitionDataExpression dataExpressionDef = this.m_dataExpressionParser.parseExpression(dataExpressionStr);
		out.setValue(dataExpressionDef);
		
		return out;
	}
	
}
