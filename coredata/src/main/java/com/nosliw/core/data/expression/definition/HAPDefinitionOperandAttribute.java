package com.nosliw.core.data.expression.definition;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;

@HAPEntityWithAttribute
public class HAPDefinitionOperandAttribute extends HAPDefinitionOperand{

	@HAPAttribute
	public static String ATTRIBUTE = "attribute";

	public static String BASE = "base";

	private String m_attribute;
	
	private HAPDefinitionOperand m_base;
	
	public HAPDefinitionOperandAttribute(){
		super(HAPConstantShared.EXPRESSION_OPERAND_ATTRIBUTEOPERATION);
	}

	public HAPDefinitionOperandAttribute(HAPDefinitionOperand base, String attribute){
		this();
		this.setBase(base);
		this.m_attribute = attribute;
	}
	
	public String getAttribute(){   return this.m_attribute;   }
	public void setAttribute(String attribute) {     this.m_attribute = attribute;      }
	
	public HAPDefinitionOperand getBase() {  return this.m_base;  }
	
	public void setBase(HAPDefinitionOperand base){	this.m_base = base;	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(ATTRIBUTE, this.getAttribute());
		jsonMap.put(BASE, this.m_base.toStringValue(HAPSerializationFormat.JSON));
	}
}

