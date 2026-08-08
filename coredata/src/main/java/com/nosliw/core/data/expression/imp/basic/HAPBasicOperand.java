package com.nosliw.core.data.expression.imp.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPParserEntityImpWithDomain;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPProcessTracker;
import com.nosliw.core.data.HAPDataTypeHelper;
import com.nosliw.core.data.criteria.HAPDataTypeCriteria;
import com.nosliw.core.data.criteria.HAPUtilityCriteria;
import com.nosliw.core.data.expression.HAPOperand;
import com.nosliw.core.data.expression.definition.HAPDefinitionOperand;
import com.nosliw.core.data.matcher.HAPMatchers;

public abstract class HAPBasicOperand extends HAPSerializableImp implements HAPOperand, HAPEntityParsable{

	public static final String PARSE_DOMAIN = "dataexpression.operand";
	
	private String m_type;
	
	private HAPDataTypeCriteria m_outputCriteria;
	
	private HAPDefinitionOperand m_operandDefinition;

	public HAPBasicOperand(String type) {
		this.m_type = type;
	}
	
	public HAPBasicOperand(String type, HAPDefinitionOperand operandDefinition) {
		this(type);
		this.m_operandDefinition = operandDefinition;
	}
	
	@Override
	public String getType() {   return this.m_type;  }

	public HAPDefinitionOperand getOperandDefinition() {    return this.m_operandDefinition;     }
	
	@Override
	public HAPDataTypeCriteria getOutputCriteria() {   return this.m_outputCriteria;  }
	public void setOutputCriteria(HAPDataTypeCriteria dataTypeCriteria){  this.m_outputCriteria = dataTypeCriteria; }

	public List<HAPBasicWrapperOperand> getChildren(){   return new ArrayList<HAPBasicWrapperOperand>();      }
	
	/**
	 * Try best to process operand in order to discovery
	 * 		Variables:	narrowest variable criteria
	 * 		Converter for the gap between output criteria and expect criteria
	 * 		Output criteria
	 * @param variablesInfo  all the variables info in context
	 * @param expectCriteria expected output criteria for this operand
	 * @return  matchers from output criteria to expect criteria
	 */
	public abstract HAPMatchers discover(HAPBasicContainerVariable variablesContainer,
			HAPDataTypeCriteria expectCriteria,
			HAPProcessTracker processTracker, 
			HAPDataTypeHelper dataTypeHelper);


	protected HAPBasicWrapperOperand createOperandWrapper(HAPBasicOperand operand){
		return new HAPBasicWrapperOperand(operand);
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(TYPE, this.getType());
		jsonMap.put(OUTPUTCRITERIA, HAPManagerSerialize.getInstance().toStringValue(this.getOutputCriteria(), HAPSerializationFormat.LITERATE));
	}
	
	static public HAPBasicOperand parseOperandJson(JSONObject jsonObj, HAPServiceParseEntity parseService) {
		return (HAPBasicOperand)parseService.parseEntityJSONImplicitAttribute(jsonObj, HAPBasicOperand.TYPE, PARSE_DOMAIN);
	}
	
}

abstract class HAPBasicOperand_parser extends HAPParserEntityImpWithDomain{

	@Override
	public String getDomain() {    return HAPBasicOperand.PARSE_DOMAIN;   }

	public void parseToOperandJson(JSONObject jsonObj, HAPBasicOperand operand, HAPServiceParseEntity parseService) {
		operand.setOutputCriteria(HAPUtilityCriteria.parseCriteria((String)jsonObj.opt(HAPOperand.OUTPUTCRITERIA)));
	}
}
