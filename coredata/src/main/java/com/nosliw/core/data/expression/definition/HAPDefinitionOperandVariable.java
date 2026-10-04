package com.nosliw.core.data.expression.definition;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.utils.HAPConstantShared;

@HAPEntityWithAttribute
public class HAPDefinitionOperandVariable extends HAPDefinitionOperand{

	@HAPAttribute
	public static String VARIABLENAME = "variableName";
	
	protected String m_variableName;

	public HAPDefinitionOperandVariable(){
		super(HAPConstantShared.EXPRESSION_OPERAND_VARIABLE);
	}

	public HAPDefinitionOperandVariable(String name){
		this();
		this.m_variableName = name;
	}

	public String getVariableName(){  return this.m_variableName;  }
	public void setVariableName(String name){   this.m_variableName = name;  }


	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(VARIABLENAME, this.m_variableName);
	}
}

