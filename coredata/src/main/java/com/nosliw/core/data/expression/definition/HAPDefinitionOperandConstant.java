package com.nosliw.core.data.expression.definition;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.data.HAPData;
import com.nosliw.core.data.HAPUtilityData;

public class HAPDefinitionOperandConstant extends HAPDefinitionOperand{

	public static String DATA = "data";

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

@Component
class HAPDefinitionOperandConstant__HAPEntityParsable extends HAPDefinitionOperand__HAPEntityParsable{

	@Override
	public String getSubName() {     return HAPConstantShared.EXPRESSION_OPERAND_CONSTANT;    }
	
	protected void parseToEntity(JSONObject jsonObj, HAPDefinitionOperandConstant operandDefinition, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, operandDefinition, parseService);
		operandDefinition.setStringValue((String)jsonObj.opt(HAPDefinitionOperandConstant.CONSTANTSTR));
		operandDefinition.setData(HAPUtilityData.buildDataWrapperFromObject(jsonObj.opt(HAPDefinitionOperandConstant.DATA)));
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPDefinitionOperandConstant out = new HAPDefinitionOperandConstant();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}
}
