package com.nosliw.core.data.expression.definition;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.data.HAPData;

@HAPEntityWithAttribute
public class HAPDefinitionOperandConstant extends HAPDefinitionOperand{

	@HAPAttribute
	public static String DATA = "data";

	@HAPAttribute
	public static String CONSTANTSTR = "constantStr";

	protected HAPData m_data;

	protected String m_constantStr;

	public HAPDefinitionOperandConstant() {
		super(HAPConstantShared.EXPRESSION_OPERAND_CONSTANT);
	}
	
	public HAPDefinitionOperandConstant(String constantStr) {
		this();
		this.m_constantStr = constantStr;
	}

	public HAPDefinitionOperandConstant(HAPData data) {
		this();
		this.m_data = data;
	}

	public String getStringValue(){  return this.m_constantStr;  }
	public void setStringValue(String strValue) {     this.m_constantStr = strValue;      }

	public HAPData getData() {   return this.m_data;    }
	public void setData(HAPData data) {     this.m_data = data;     }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(CONSTANTSTR, this.m_constantStr);
		if(this.m_data!=null) {
			jsonMap.put(DATA, this.m_data.toStringValue(HAPSerializationFormat.JSON));
		}
	}
}

