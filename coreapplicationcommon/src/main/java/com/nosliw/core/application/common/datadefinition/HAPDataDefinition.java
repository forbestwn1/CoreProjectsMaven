package com.nosliw.core.application.common.datadefinition;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPParserEntityImpWithDomain;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPUtilityBasic;
import com.nosliw.core.data.criteria.HAPDataTypeCriteria;
import com.nosliw.core.data.criteria.HAPParserCriteriaImp;
import com.nosliw.core.data.criteria.HAPUtilityCriteria;

@HAPEntityWithAttribute
public abstract class HAPDataDefinition extends HAPSerializableImp implements HAPEntityParsable{

	public static final String PARSABLEENTITYDOMAIN = "core.data.definition";
	
	public static final String TYPE = "type";
	
	public static final String ISMULTIPLEALLOWED = "isMultipleAllowed";
	
	@HAPAttribute
	public static String CRITERIA = "criteria";

	private String m_type;
	
	//data type
	private HAPDataTypeCriteria m_criteria;
	
	private boolean m_isMultipleAllowed;
	
	public HAPDataDefinition(String type) {
		this.m_type = type;
		this.m_isMultipleAllowed = false;
	}

	public HAPDataDefinition(String type, HAPDataTypeCriteria criteria) {
		this(type);
		this.m_criteria = criteria;
	}

	public String getType() {     return this.m_type;      }
	
	public HAPDataTypeCriteria getCriteria() {   return this.m_criteria; }
	public void setCriteria(HAPDataTypeCriteria criteria) {    this.m_criteria = criteria;     }
	
	public boolean getIsMultipleAllowed() {    return this.m_isMultipleAllowed;   }
	public void setIsMultipleAllowed(boolean allowed) {     this.m_isMultipleAllowed = allowed;        }
	
	@Override
	public boolean equals(Object obj){
		boolean out = false;
		if(obj instanceof HAPDataDefinition){
			HAPDataDefinition dataInfo = (HAPDataDefinition)obj;
			if(HAPUtilityBasic.isEquals(this.getCriteria(), dataInfo.getCriteria())) 
			{
				if(HAPUtilityBasic.isEquals(this.getIsMultipleAllowed(), dataInfo.getIsMultipleAllowed())) {
					out = true;
				}
			}
		}
		return out;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(TYPE, this.m_type);
		if(this.getCriteria()!=null) {
			jsonMap.put(CRITERIA, HAPManagerSerialize.getInstance().toStringValue(this.getCriteria(), HAPSerializationFormat.LITERATE));
		}
		jsonMap.put(ISMULTIPLEALLOWED, this.m_isMultipleAllowed+"");
		typeJsonMap.put(ISMULTIPLEALLOWED, Boolean.class);
	}
	
	@Override
	public boolean buildObject(Object value, HAPSerializationFormat format) {
		if(value instanceof String) {
			this.m_criteria = HAPParserCriteriaImp.getInstance().parseCriteria((String)value);
		}
		else if(value instanceof JSONObject){
			JSONObject jsonValue = (JSONObject)value;
			this.m_criteria = HAPParserCriteriaImp.getInstance().parseCriteria((String)jsonValue.opt(CRITERIA));
		}
		return true;
	}
	
	protected void cloneToDataDefinition(HAPDataDefinition dataDef) {
		dataDef.m_criteria = HAPUtilityCriteria.cloneDataTypeCriteria(this.m_criteria);
		dataDef.m_isMultipleAllowed = this.m_isMultipleAllowed;
	}
	
	@Override
	public String toString(){		return this.toStringValue(HAPSerializationFormat.JSON);	}
}


abstract class HAPDataDefinition__HAPEntityParsable extends HAPParserEntityImpWithDomain{

	@Override
	public String getDomain() {   return HAPDataDefinition.PARSABLEENTITYDOMAIN;   }

	protected void parseToEntity(JSONObject jsonObj, HAPDataDefinition dataDefinition, HAPServiceParseEntity parseService) {
		dataDefinition.setCriteria(HAPParserCriteriaImp.getInstance().parseCriteria((String)jsonObj.opt(HAPDataDefinition.CRITERIA)));
		Object isMultipleAllowedObje = jsonObj.opt(HAPDataDefinition.ISMULTIPLEALLOWED);
		if(isMultipleAllowedObje!=null) {
			dataDefinition.setIsMultipleAllowed((Boolean)isMultipleAllowedObje);
		}
	}
	
}
