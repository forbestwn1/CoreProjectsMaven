package com.nosliw.core.data.expression.imp.basic;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPProcessTracker;
import com.nosliw.core.data.HAPData;
import com.nosliw.core.data.HAPDataTypeHelper;
import com.nosliw.core.data.HAPUtilityData;
import com.nosliw.core.data.criteria.HAPDataTypeCriteria;
import com.nosliw.core.data.criteria.HAPUtilityCriteria;
import com.nosliw.core.data.expression.HAPOperandConstant;
import com.nosliw.core.data.expression.definition.HAPDefinitionOperandConstant;
import com.nosliw.core.data.matcher.HAPMatchers;

public class HAPBasicOperandConstant extends HAPBasicOperand implements HAPOperandConstant{

	private HAPData m_data;
	
	private String m_name;

	public HAPBasicOperandConstant() {
		super(HAPConstantShared.EXPRESSION_OPERAND_CONSTANT);
	}
	
	public HAPBasicOperandConstant(HAPDefinitionOperandConstant operandDefinition) {
		super(HAPConstantShared.EXPRESSION_OPERAND_CONSTANT, operandDefinition);
		
		String stringValue = operandDefinition.getStringValue();
		if(stringValue!=null) {
			HAPData data = HAPUtilityData.buildDataWrapper(stringValue);
			if(data==null){
				//not a valid data literate, then it is a constant name
				this.m_name = stringValue;
			}
			else{
				this.m_data = data;
			}
		}
		else if(operandDefinition.getData()!=null) {
			this.m_data = operandDefinition.getData();
		}
	}

	@Override
	public HAPData getData() {   return this.m_data;   }
	public void setData(HAPData data) {     this.m_data = data;           }
	
	public String getName() {    return this.m_name;   }

	@Override
	public HAPMatchers discover(
			HAPBasicContainerVariable variablesContainer,
			HAPDataTypeCriteria expectCriteria, 
			HAPProcessTracker processTracker,
			HAPDataTypeHelper dataTypeHelper) {
		//set output criteria
		if(this.getOutputCriteria()==null){
			HAPDataTypeCriteria criteria = dataTypeHelper.getDataTypeCriteriaByData(m_data);
			this.setOutputCriteria(criteria);
		}
		return HAPUtilityCriteria.isMatchable(this.getOutputCriteria(), expectCriteria, dataTypeHelper);
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(DATA, HAPManagerSerialize.getInstance().toStringValue(this.m_data, HAPSerializationFormat.JSON));
	}
}

@Component
class HAPBasicOperandConstant_parser extends HAPBasicOperand_parser{

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPBasicOperandConstant out = new HAPBasicOperandConstant();
		JSONObject jsonObj = (JSONObject)obj;

		this.parseToOperandJson(jsonObj, out, parseService);
		
		Object dataObj = jsonObj.opt(HAPBasicOperandConstant.DATA);
		if(dataObj!=null) {
			out.setData(HAPUtilityData.buildDataWrapperFromObject(dataObj));
		}
		
		return out;
	}

	@Override
	public String getSubName() {   return HAPConstantShared.EXPRESSION_OPERAND_CONSTANT;    }
	
}
