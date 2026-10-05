package com.nosliw.core.application.entity.app.expressionbuild;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.data.criteria.HAPDataTypeCriteria;
import com.nosliw.core.data.expression.definition.HAPDefinitionOperand;

@HAPEntityWithAttribute
public class HAPDataBuildParmInOperationOperand extends HAPSerializableImp{

	@HAPAttribute
	public static String NAME = "name";
	
	@HAPAttribute
	public static String VALUE = "value";
	
	@HAPAttribute
	public static String CRITERIA = "criteria";
	
	private String m_name;
	
	private HAPDataBuild m_operand;
	
	//data type
	private HAPDataTypeCriteria m_criteria;

	public HAPDataBuildParmInOperationOperand(){
	}
	
	public HAPDataBuildParmInOperationOperand(String name, HAPDefinitionOperand operand){
		this.m_name = name;
		this.m_operand = operand;
	}
	
	public String getName(){		return this.m_name;	}
	public void setName(String name) {      this.m_name = name;      }
	
	public HAPDefinitionOperand getOperand(){  return this.m_operand; }
	public void setOperand(HAPDefinitionOperand operand) {      this.m_operand = operand;       }
	
	public HAPDataTypeCriteria getDataTypeCriteria() {     return this.m_criteria;     }
	public void setDataTypeCriteria(HAPDataTypeCriteria criteria) {     this.m_criteria = criteria;      }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
	    jsonMap.put(NAME, this.m_name);
	    jsonMap.put(OPERAND, this.m_operand.toStringValue(HAPSerializationFormat.JSON));
	    if(this.m_criteria!=null) {
	    	jsonMap.put(CRITERIA, this.m_criteria.toStringValue(HAPSerializationFormat.LITERATE));
	    }
	}
	
}
