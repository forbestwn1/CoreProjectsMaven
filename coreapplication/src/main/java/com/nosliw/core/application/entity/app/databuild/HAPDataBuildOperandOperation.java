package com.nosliw.core.application.entity.app.databuild;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.data.HAPDataTypeId;
import com.nosliw.core.data.HAPOperationId;
import com.nosliw.core.data.expression.definition.HAPDefinitionOperand;

public class HAPDataBuildOperandOperation extends HAPDefinitionOperand implements HAPDataBuildOperand{

	@HAPAttribute
	public static String DATATYPEID = "dataTypeId";
	
	@HAPAttribute
	public static String OPERATION = "operation";
	
	@HAPAttribute
	public static String BASE = "base";
	
	@HAPAttribute
	public static String PARMS = "parms";
	
	//the data type operation defined on
	protected HAPDataTypeId m_dataTypeId;
	
	//operation name
	protected String m_operation;
	
	//base dataHAPDefinitionOperand
	protected HAPDataBuildOperand m_base;

	//operation parms
	protected List<HAPDataBuildParmInOperationOperand> m_parms = new ArrayList<HAPDataBuildParmInOperationOperand>();

	public HAPDataBuildOperandOperation(){
		super(HAPConstantShared.EXPRESSION_OPERAND_OPERATION);
	}

	public HAPDataBuildOperandOperation(HAPDataBuildOperand base, String operation, List<HAPDataBuildParmInOperationOperand> parms){
		this();
		if(base!=null) {
			this.m_base = base;
		}
		this.m_operation = operation;

		for(HAPDataBuildParmInOperationOperand opParm : parms) {
			this.m_parms.add(opParm);
		}
	}
	
	public HAPDataBuildOperand getBase(){  return this.m_base;  }
	public void setBase(HAPDataBuildOperand base) {   this.m_base = base;     }
	
	public List<HAPDataBuildParmInOperationOperand> getParms(){   return this.m_parms;   }
	public void addParm1(HAPDataBuildParmInOperationOperand parm){		this.m_parms.add(parm);	}

	public HAPDataTypeId getDataTypeId(){   return this.m_dataTypeId; }
	public void setDataTypeId(HAPDataTypeId dataTypeId) {     this.m_dataTypeId = dataTypeId;        }

	public String getOperaion(){  return this.m_operation;  }
	public void setOperation(String operation) {      this.m_operation = operation;       }
	
	public void addParm(HAPDataBuildParmInOperationOperand parm) {     this.m_parms.add(parm);       }
	
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
