package com.nosliw.core.application.entity.app.databuild;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.datadefinition.HAPDataDefinition;
import com.nosliw.core.application.common.datadefinition.HAPUtilityDataDefinition;
import com.nosliw.core.data.HAPData;

public class HAPDataBuildUtility {

	public static HAPDataBuild buildDataBuildFromDataDefinition(HAPDataDefinition dataDefinition) {
		return HAPUtilityDataDefinition.getInitDataBuild(dataDefinition);
	}

	public static HAPData getData(HAPDataBuild dataBuild) {
		HAPData out = null;
		if(dataBuild!=null && HAPConstantShared.DATABUILD_CHOSEN_CONSTANT.equals(dataBuild.getExpressionChosen())) {
			HAPDataBuildExpression expression = dataBuild.getExpressions().get(HAPConstantShared.DATABUILD_CHOSEN_CONSTANT);
			if(expression!=null) {
				HAPDataBuildOperandConstant constantOperand = (HAPDataBuildOperandConstant)expression.getOperand();
				if(constantOperand!=null) {
					out = constantOperand.getData();
				}
			}
		}
		return out;
	}

	public static HAPDataBuild buildDataBuildByConstant(HAPData data) {
		HAPDataBuild out = new HAPDataBuild();
		out.setExpressionChosen(HAPConstantShared.DATABUILD_CHOSEN_CONSTANT);
		
		HAPDataBuildOperandConstant constantOperand = new HAPDataBuildOperandConstant();
		constantOperand.setData(data);
		
		HAPDataBuildExpression expression = new HAPDataBuildExpression();
		expression.setOperand(constantOperand);
		
		out.addExpression(HAPConstantShared.DATABUILD_CHOSEN_CONSTANT, expression);
		
		return out;
	}
	
}
