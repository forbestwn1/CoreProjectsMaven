package com.nosliw.core.data.expression.imp.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPProcessTracker;
import com.nosliw.core.data.HAPDataTypeHelper;
import com.nosliw.core.data.criteria.HAPDataTypeCriteria;
import com.nosliw.core.data.expression.HAPOperand;
import com.nosliw.core.data.expression.HAPOperandAttribute;
import com.nosliw.core.data.expression.definition.HAPDefinitionOperandAttribute;
import com.nosliw.core.data.matcher.HAPMatchers;

public class HAPBasicOperandAttribute extends HAPBasicOperand implements HAPOperandAttribute{

	private String m_attribute;
	
	private HAPBasicWrapperOperand m_base;
	
	public HAPBasicOperandAttribute() {
		super(HAPConstantShared.EXPRESSION_OPERAND_ATTRIBUTEOPERATION);
	}
	
	public HAPBasicOperandAttribute(HAPDefinitionOperandAttribute operandDefinition) {
		super(HAPConstantShared.EXPRESSION_OPERAND_ATTRIBUTEOPERATION, operandDefinition);
		this.m_attribute = operandDefinition.getAttribute();
	}

	@Override
	public String getAttribute() {    return this.m_attribute;    }

	@Override
	public HAPOperand getBase() {   return this.m_base.getOperand();    }   
	public void setBase(HAPBasicOperand base) {   this.m_base = new HAPBasicWrapperOperand(base);    }

	@Override
	public List<HAPBasicWrapperOperand> getChildren(){   
		List<HAPBasicWrapperOperand> out = new ArrayList<HAPBasicWrapperOperand>();
		out.add(this.m_base);
		return out;
	}
	
	@Override
	public HAPMatchers discover(
			HAPBasicContainerVariable variablesContainer,
			HAPDataTypeCriteria expectCriteria, 
			HAPProcessTracker processTracker,
			HAPDataTypeHelper dataTypeHelper) {
		return null;
	}
	
	@Override
	protected void buildJSJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJSJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(ATTRIBUTE, this.m_attribute);
		jsonMap.put(BASEDATA, HAPManagerSerialize.getInstance().toStringValue(this.getBase(), HAPSerializationFormat.JAVASCRIPT));
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(ATTRIBUTE, this.m_attribute);
		jsonMap.put(BASEDATA, HAPManagerSerialize.getInstance().toStringValue(this.getBase(), HAPSerializationFormat.JSON));
	}
}

class HAPBasicOperandAttribute_parser extends HAPBasicOperand_parser{

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPBasicOperandAttribute out = new HAPBasicOperandAttribute();
		
		JSONObject jsonObj = (JSONObject)obj;
		this.parseToOperandJson(jsonObj, out, parseService);
		
		return out;
	}

	@Override
	public String getSubName() {    return HAPConstantShared.EXPRESSION_OPERAND_ATTRIBUTEOPERATION;   }
	
}
