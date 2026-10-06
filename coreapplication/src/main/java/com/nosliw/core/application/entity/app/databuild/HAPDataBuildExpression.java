package com.nosliw.core.application.entity.app.databuild;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPDataBuildExpression extends HAPSerializableImp{

	@HAPAttribute
	public static final String OPERAND = "operand";
	
	private HAPDataBuildOperand m_operand;
	
	
	public HAPDataBuildOperand getOperand() {     return this.m_operand;      }
	public void setOperand(HAPDataBuildOperand operand) {       this.m_operand = operand;          }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(OPERAND, this.m_operand.toStringValue(HAPSerializationFormat.JSON));
	}
	
	public static HAPDataBuildExpression buildDataBuildExpression(JSONObject jsonObj, HAPServiceParseEntity entityParseService) {
		HAPDataBuildExpression out = new HAPDataBuildExpression();
		out.setOperand(HAPDataBuildOperand.parseOperandDefinition(jsonObj.optJSONObject(OPERAND), entityParseService));
		return out;
	}
	
}
