package com.nosliw.core.data.expression.definition;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;

@HAPEntityWithAttribute
public class HAPDefinitionParmInOperationOperand extends HAPSerializableImp{

	@HAPAttribute
	public static String NAME = "name";
	
	@HAPAttribute
	public static String OPERAND = "operand";
	
	private String m_name;
	
	private HAPDefinitionOperand m_operand;
	
	public HAPDefinitionParmInOperationOperand(String name, HAPDefinitionOperand operand){
		this.m_name = name;
		this.m_operand = operand;
	}
	
	public String getName(){		return this.m_name;	}
	
	public HAPDefinitionOperand getOperand(){  return this.m_operand; }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
	    jsonMap.put(NAME, this.m_name);
	    jsonMap.put(OPERAND, this.m_operand.toStringValue(HAPSerializationFormat.JSON));
	}


}
