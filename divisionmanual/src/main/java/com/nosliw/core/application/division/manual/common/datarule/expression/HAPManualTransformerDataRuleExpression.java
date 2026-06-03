package com.nosliw.core.application.division.manual.common.datarule.expression;

import java.util.List;

import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.common.datadefinition.HAPDataDefinitionReadonly;
import com.nosliw.core.application.common.datadefinition.HAPDefinitionParmRequest;
import com.nosliw.core.application.common.datadefinition.HAPDefinitionParmResponse;
import com.nosliw.core.application.common.dataexpression.definition.HAPDefinitionDataExpressionStandAlone;
import com.nosliw.core.application.common.interactive.HAPInteractiveExpression;
import com.nosliw.core.application.division.manual.brick.expression.dataexpression.standalone.HAPManualDefinitionBlockDataExpressionStandAlone;
import com.nosliw.core.application.division.manual.common.datarule.HAPManualTransformerDataRule;
import com.nosliw.core.application.division.manual.core.HAPManualManagerBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionBrick;
import com.nosliw.core.application.entity.datarule.HAPDataRule;
import com.nosliw.core.application.entity.datarule.expression.HAPDataRuleExpression;
import com.nosliw.core.data.HAPDataTypeId;
import com.nosliw.core.data.criteria.HAPDataTypeCriteriaId;

public class HAPManualTransformerDataRuleExpression implements HAPManualTransformerDataRule{

	private HAPManualManagerBrick m_manualBrickMan;
	
	public HAPManualTransformerDataRuleExpression(HAPManualManagerBrick manualBrickMan) {
		this.m_manualBrickMan = manualBrickMan;
	}
	
	@Override
	public HAPManualDefinitionBrick transformDataRule(HAPDataRule dataRule) {
		HAPManualDefinitionBlockDataExpressionStandAlone block = (HAPManualDefinitionBlockDataExpressionStandAlone)this.m_manualBrickMan.newBrickDefinition(HAPEnumBrickType.DATAEXPRESSIONSTANDALONE_100);
		
		HAPDefinitionDataExpressionStandAlone dataExpressionStandAlone = new HAPDefinitionDataExpressionStandAlone(); 
		HAPDataRuleExpression expressionDataRule = (HAPDataRuleExpression)dataRule;
		dataExpressionStandAlone.setExpression(expressionDataRule.getExpressionDefinition());		

		HAPDefinitionParmRequest parm = new HAPDefinitionParmRequest();
		parm.getDataDefinition().setCriteria(expressionDataRule.getDataCriteria());
		parm.setName("data");
		
		HAPDefinitionParmResponse result = new HAPDefinitionParmResponse();
		result.setDataDefinition(new HAPDataDefinitionReadonly());
		result.getDataDefinition().setCriteria(new HAPDataTypeCriteriaId(new HAPDataTypeId("boolean", "1.0.0"), null));

		HAPInteractiveExpression interactive = new HAPInteractiveExpression(List.of(parm), result);
		
		dataExpressionStandAlone.setExpressionInteractive(interactive);
		
		block.setValue(dataExpressionStandAlone);
		return block;
	}

}
