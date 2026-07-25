package com.nosliw.core.data.expression.definition;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityBasic;
import com.nosliw.core.data.HAPDataTypeId;
import com.nosliw.core.data.HAPOperationId;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPDefinitionOperandOperation extends HAPDefinitionOperand{

	public static String DATATYPEID = "dataTypeId";
	
	public static String OPERATION = "operation";
	
	public static String BASE = "base";
	
	public static String PARMS = "parms";
	
	//the data type operation defined on
	protected HAPDataTypeId m_dataTypeId;
	
	//operation name
	protected String m_operation;
	
	//base dataHAPDefinitionOperand
	protected HAPDefinitionOperand m_base;

	//operation parms
	protected Map<String, HAPDefinitionOperand> m_parms = new LinkedHashMap<String, HAPDefinitionOperand>();

	public HAPDefinitionOperandOperation(){
		super(HAPConstantShared.EXPRESSION_OPERAND_OPERATION);
	}

	public HAPDefinitionOperandOperation(HAPDefinitionOperand base, String operation, List<HAPDefinitionParmInOperationOperand> parms){
		this();
		if(base!=null) {
			this.m_base = base;
		}
		this.m_operation = operation;

		for(HAPDefinitionParmInOperationOperand opParm : parms) {
			this.m_parms.put(opParm.getName(), opParm.getOperand());
		}
	}
	
	public HAPDefinitionOperandOperation(String dataTypeIdLiterate, String operation, List<HAPDefinitionParmInOperationOperand> parms){
		this();
		this.m_dataTypeId = (HAPDataTypeId)HAPManagerSerialize.getInstance().buildObject(HAPDataTypeId.class.getName(), dataTypeIdLiterate, HAPSerializationFormat.LITERATE);
		this.m_operation = operation;
		
		for(HAPDefinitionParmInOperationOperand opParm : parms){
			if(HAPUtilityBasic.isStringEmpty(opParm.getName())) {
				this.m_base = opParm.getOperand();
			} else {
				this.m_parms.put(opParm.getName(), opParm.getOperand());
			}
		}
	}
	
	public HAPDefinitionOperand getBase(){  return this.m_base;  }
	public void setBase(HAPDefinitionOperand base) {   this.m_base = base;     }
	
	public Map<String, HAPDefinitionOperand> getParms(){   return this.m_parms;   }
	
	public void addParm(String name, HAPDefinitionOperand parmOperand){
		this.m_parms.put(name, parmOperand);
	}
	
	public HAPDataTypeId getDataTypeId(){   return this.m_dataTypeId; }
	public void setDataTypeId(HAPDataTypeId dataTypeId) {     this.m_dataTypeId = dataTypeId;        }

	public String getOperaion(){  return this.m_operation;  }
	public void setOperation(String operation) {      this.m_operation = operation;       }
	
	public HAPOperationId getOperationId(){
		HAPOperationId out = null;
		if(this.m_dataTypeId!=null){
			out = new HAPOperationId(this.m_dataTypeId, this.m_operation);
		}
		return out;  
	}
	
	@Override
	public List<HAPDefinitionOperand> getChildren(){
		List<HAPDefinitionOperand> out = new ArrayList<HAPDefinitionOperand>();
		if(this.m_base!=null) {
			out.add(this.m_base);
		}
		out.addAll(this.m_parms.values());
		return out;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		if(this.m_dataTypeId!=null) {
			jsonMap.put(DATATYPEID, this.m_dataTypeId.toStringValue(HAPSerializationFormat.LITERATE));
		}
		jsonMap.put(OPERATION, this.m_operation);
		if(this.m_base!=null) {
			jsonMap.put(BASE, this.m_base.toStringValue(HAPSerializationFormat.JSON));
		}
		jsonMap.put(PARMS, HAPManagerSerialize.getInstance().toStringValue(m_parms, HAPSerializationFormat.JSON));
	}
	
}

@Component
class HAPDefinitionOperandOperation__HAPEntityParsable extends HAPDefinitionOperand__HAPEntityParsable{

	@Override
	public String getSubName() {     return HAPConstantShared.EXPRESSION_OPERAND_OPERATION;    }
	
	protected void parseToEntity(JSONObject jsonObj, HAPDefinitionOperandOperation operandDefinition, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, operandDefinition, parseService);

		operandDefinition.setBase(HAPDefinitionOperand.parseOperandDefinition(jsonObj.optJSONObject(HAPDefinitionOperandOperation.BASE), parseService));
		
		Object dataTypeObj = jsonObj.opt(HAPDefinitionOperandOperation.DATATYPEID);
		if(dataTypeObj!=null) {
			HAPDataTypeId dataTypeId = new HAPDataTypeId();
			if(dataTypeObj instanceof String) {
				dataTypeId.buildObject(dataTypeObj, HAPSerializationFormat.LITERATE);
			}
			else {
				dataTypeId.buildObject(dataTypeObj, HAPSerializationFormat.JSON);
			}
			operandDefinition.setDataTypeId(dataTypeId);
		}
		operandDefinition.setOperation(jsonObj.getString(HAPDefinitionOperandOperation.OPERATION));
		
		JSONObject parmsObj = jsonObj.getJSONObject(HAPDefinitionOperandOperation.PARMS);
		for(Object key : parmsObj.keySet()) {
			String name = (String)key;
			operandDefinition.addParm(name, HAPDefinitionOperand.parseOperandDefinition(parmsObj.getJSONObject(name), parseService));
		}
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPDefinitionOperandOperation out = new HAPDefinitionOperandOperation();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}
}
