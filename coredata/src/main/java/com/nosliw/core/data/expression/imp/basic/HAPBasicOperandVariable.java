package com.nosliw.core.data.expression.imp.basic;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPProcessTracker;
import com.nosliw.core.data.HAPDataTypeHelper;
import com.nosliw.core.data.criteria.HAPDataTypeCriteria;
import com.nosliw.core.data.criteria.HAPInfoCriteria;
import com.nosliw.core.data.criteria.HAPUtilityCriteria;
import com.nosliw.core.data.expression.HAPOperandVariable;
import com.nosliw.core.data.expression.definition.HAPDefinitionOperandVariable;
import com.nosliw.core.data.matcher.HAPMatchers;

public class HAPBasicOperandVariable extends HAPBasicOperand implements HAPOperandVariable{

	private String m_variableKey;
	
	private String m_variableName;

	public HAPBasicOperandVariable() {}

	public HAPBasicOperandVariable(HAPDefinitionOperandVariable operandDefinition) {
		super(HAPConstantShared.EXPRESSION_OPERAND_VARIABLE, operandDefinition);
		this.m_variableName = operandDefinition.getVariableName();
	}

	@Override
	public String getVariableKey() {    return this.m_variableKey;    }
	public void setVariableKey(String varKey) {    this.m_variableKey = varKey;      }

	@Override
	public String getVariableName() {   return this.m_variableName;   }
	public void setVariableName(String varName) {     this.m_variableName = varName;        }

	@Override
	public HAPMatchers discover(
			HAPBasicContainerVariable variablesContainer,
			HAPDataTypeCriteria expectCriteria, 
			HAPProcessTracker processTracker,
			HAPDataTypeHelper dataTypeHelper) {
		
		HAPInfoCriteria variableInfo = variablesContainer.getVaraibleCriteriaInfo(this.getVariableKey());
		
		HAPMatchers matchers = HAPUtilityCriteria.mergeVariableInfo(variableInfo, expectCriteria, dataTypeHelper);
		
		//set output criteria
		this.setOutputCriteria(variableInfo.getCriteria());

		//cal converter
		return matchers;
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(VARIABLENAME, m_variableName);
		if(m_variableKey!=null) {
			jsonMap.put(VARIABLEKEY, this.m_variableKey);
		}
		else {
			jsonMap.put(VARIABLEKEY, this.m_variableName);
		}
	}
}

@Component
class HAPBasicOperandVariable_parser extends HAPBasicOperand_parser{

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPBasicOperandVariable out = new HAPBasicOperandVariable();
		JSONObject jsonObj = (JSONObject)obj;

		this.parseToOperandJson(jsonObj, out, parseService);
		out.setVariableName((String)jsonObj.opt(HAPBasicOperandVariable.VARIABLENAME));
		out.setVariableKey((String)jsonObj.opt(HAPBasicOperandVariable.VARIABLEKEY));
		
		return out;
	}

	@Override
	public String getSubName() {   return HAPConstantShared.EXPRESSION_OPERAND_VARIABLE;    }
	
}
