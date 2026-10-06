package com.nosliw.core.application.entity.app.databuild;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.data.criteria.HAPDataTypeCriteria;
import com.nosliw.core.data.criteria.HAPUtilityCriteria;

@HAPEntityWithAttribute
public class HAPDataBuildParmInOperationOperand extends HAPSerializableImp{

	@HAPAttribute
	public static String NAME = "name";
	
	@HAPAttribute
	public static String VALUE = "value";
	
	@HAPAttribute
	public static String CRITERIA = "criteria";
	
	private String m_name;
	
	private HAPDataBuild m_value;
	
	//data type
	private HAPDataTypeCriteria m_criteria;

	public HAPDataBuildParmInOperationOperand(){
	}
	
	public HAPDataBuildParmInOperationOperand(String name){
		this.m_name = name;
	}
	
	public String getName(){		return this.m_name;	}
	public void setName(String name) {      this.m_name = name;      }
	
	public HAPDataBuild getValue(){  return this.m_value; }
	public void setValue(HAPDataBuild value) {      this.m_value = value;       }
	
	public HAPDataTypeCriteria getDataTypeCriteria() {     return this.m_criteria;     }
	public void setDataTypeCriteria(HAPDataTypeCriteria criteria) {     this.m_criteria = criteria;      }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
	    jsonMap.put(NAME, this.m_name);
	    jsonMap.put(VALUE, this.m_value.toStringValue(HAPSerializationFormat.JSON));
	    if(this.m_criteria!=null) {
	    	jsonMap.put(CRITERIA, this.m_criteria.toStringValue(HAPSerializationFormat.LITERATE));
	    }
	}
	
	public static HAPDataBuildParmInOperationOperand buildDataBuildParm(JSONObject jsonObj, HAPServiceParseEntity parseService) {
		HAPDataBuildParmInOperationOperand out = new HAPDataBuildParmInOperationOperand();
		out.setName((String)jsonObj.opt(NAME));
		out.setDataTypeCriteria(HAPUtilityCriteria.parseCriteria((String)jsonObj.opt(CRITERIA)));
		out.setValue(HAPDataBuild.buildDataBuild(jsonObj.optJSONObject(VALUE), parseService));
		return out;
	}
	
}
