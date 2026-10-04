package com.nosliw.core.data.expression.definition;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;

@HAPEntityWithAttribute
public class HAPDefinitionOperandReference extends HAPDefinitionOperand{

	@HAPAttribute
	public static String REFERENCE = "reference";
	
	@HAPAttribute
	public static String VARIABLEMAPPING = "variableMapping";
	
	//reference to expression (attachent name or resource id)
	private String m_reference;
	
	//mapping from this expression to referenced expression variable (ref variable id path --  source operand)
	private Map<String, HAPDefinitionOperand> m_variableMapping;
	
	public HAPDefinitionOperandReference(){
		super(HAPConstantShared.EXPRESSION_OPERAND_REFERENCE);
		this.m_variableMapping = new LinkedHashMap<String, HAPDefinitionOperand>();
	}
	
	public HAPDefinitionOperandReference(String reference){
		this();
		this.m_reference = reference;
	}

	public String getReference(){  return this.m_reference;  }
	public void setReference(String ref) {     this.m_reference = ref;        }
	
	public void addMapping(String varName, HAPDefinitionOperand operand) {	this.m_variableMapping.put(varName, operand);	}
	public Map<String, HAPDefinitionOperand> getMapping(){    return this.m_variableMapping;      }
	public void setMapping(Map<String, HAPDefinitionOperand> mapping) {    
		this.m_variableMapping.clear();
		this.m_variableMapping.putAll(mapping);
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
	    jsonMap.put(REFERENCE, this.m_reference);
	    jsonMap.put(VARIABLEMAPPING, HAPManagerSerialize.getInstance().toStringValue(this.m_variableMapping, HAPSerializationFormat.JSON));
	}
}


