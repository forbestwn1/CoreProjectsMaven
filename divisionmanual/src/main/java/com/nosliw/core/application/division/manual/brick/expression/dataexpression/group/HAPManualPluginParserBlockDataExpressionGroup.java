package com.nosliw.core.application.division.manual.brick.expression.dataexpression.group;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.info.HAPUtilityEntityInfo;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.common.dataexpression.HAPContainerDataExpression;
import com.nosliw.core.application.common.dataexpression.HAPItemInContainerDataExpression;
import com.nosliw.core.application.division.manual.core.HAPManualManagerBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionContextParse;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionPluginParserBrickImpComplex;
import com.nosliw.core.application.entity.brick.HAPManagerApplicationBrick;
import com.nosliw.core.data.expression.definition.HAPDefinitionDataExpression;
import com.nosliw.core.data.expression.definition.HAPParserDataExpression;

public class HAPManualPluginParserBlockDataExpressionGroup extends HAPManualDefinitionPluginParserBrickImpComplex{

	private HAPParserDataExpression m_dataExpressionParser;
	
	public HAPManualPluginParserBlockDataExpressionGroup(HAPManualManagerBrick manualDivisionEntityMan, HAPManagerApplicationBrick brickMan, HAPParserDataExpression dataExpressionParser) {
		super(HAPEnumBrickType.DATAEXPRESSIONGROUP_100, HAPManualDefinitionBlockDataExpressionGroup.class, manualDivisionEntityMan, brickMan);
		this.m_dataExpressionParser = dataExpressionParser;
	}

	@Override
	protected void parseComplexDefinitionContentJson(HAPManualDefinitionBrick brickDef, JSONObject jsonObj, HAPManualDefinitionContextParse parseContext) {
		HAPManualDefinitionBlockDataExpressionGroup groupBlock = (HAPManualDefinitionBlockDataExpressionGroup)brickDef;

		JSONArray dataExpressionArray = jsonObj.getJSONArray(HAPContainerDataExpression.ITEM);
		for(int i=0; i<dataExpressionArray.length(); i++) {
			JSONObject elementObj = dataExpressionArray.getJSONObject(i);
			if(HAPUtilityEntityInfo.isEnabled(elementObj)) {
				String expressionStr = elementObj.getString(HAPItemInContainerDataExpression.DATAEXPRESSION);
				HAPDefinitionDataExpression dataExpression =  this.m_dataExpressionParser.parseExpression(expressionStr);
				HAPManualDefinitionDataExpressionItemInContainer item = new HAPManualDefinitionDataExpressionItemInContainer(dataExpression);
				item.buildEntityInfoByJson(elementObj);
				groupBlock.getValue().addItem(item);
			}
		}
	}
}
