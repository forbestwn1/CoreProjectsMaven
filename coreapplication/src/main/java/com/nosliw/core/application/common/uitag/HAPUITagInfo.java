package com.nosliw.core.application.common.uitag;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.core.data.matcher.HAPMatchers;
import com.nosliw.core.data.matcher.HAPMatchersCombo;

@HAPEntityWithAttribute
public class HAPUITagInfo extends HAPEntityInfoImp{

	@HAPAttribute
	public static final String ATTRIBUTES = "attributes";

	@HAPAttribute
	public static final String ATTRIBUTEFORDATA = "attributeForData";
	
	@HAPAttribute
	public static final String MATCHERS = "matchers";
	
	private Map<String, String> m_attributes;

	private Map<String, HAPMatchersCombo> m_matchers;

	private String m_attributeForData;
	
	public HAPUITagInfo() {
		this.m_attributes = new LinkedHashMap<String, String>();
		this.m_matchers = new LinkedHashMap<String, HAPMatchersCombo>();
	}

	public HAPUITagInfo(HAPUITagDefinition tagDef) {
		this();
		tagDef.cloneToEntityInfo(this);
	}
	
	public Map<String, String> getAttributes(){   return this.m_attributes;    }

	public void setAttributeForData(String attrName) {       this.m_attributeForData = attrName;        }
	public String getAttributeForData() {     return this.m_attributeForData;      }
	
	public void addMatchers(String name, HAPMatchers matchers) { 
		this.m_matchers.put(name, new HAPMatchersCombo(matchers));    
	}
	
	public Map<String, HAPMatchersCombo> getMatchers(){    return this.m_matchers;    }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(ATTRIBUTES, HAPUtilityJson.buildJson(this.m_attributes, HAPSerializationFormat.JSON));
		jsonMap.put(MATCHERS, HAPUtilityJson.buildJson(this.m_matchers, HAPSerializationFormat.JSON));
		jsonMap.put(ATTRIBUTEFORDATA, this.m_attributeForData);
	}
	
	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		super.buildEntityInfoByJson(jsonObj);
		
		this.m_attributeForData = (String)jsonObj.opt(ATTRIBUTEFORDATA);
		
		JSONObject attrsObj = jsonObj.optJSONObject(ATTRIBUTES);
		if(attrsObj!=null) {
			for(Object key : attrsObj.keySet()) {
				this.m_attributes.put((String)key, attrsObj.getString((String)key));
			}
		}
		
		JSONObject matchersJsonObj = jsonObj.optJSONObject(MATCHERS);
		if(matchersJsonObj!=null) {
			for(Object key : matchersJsonObj.keySet()) {
				String name = (String)key;
				
				HAPMatchersCombo matchers = new HAPMatchersCombo();
				matchers.buildObject(matchersJsonObj.getJSONObject(name), HAPSerializationFormat.JSON);
				this.m_matchers.put(name, matchers);
			}
		}
		
		return true;  
	}	
}
