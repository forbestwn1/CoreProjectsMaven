package com.nosliw.core.application.division.story.definition;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;

@HAPEntityWithAttribute
public class HAPStoryIdElementType extends HAPSerializableImp{

	@HAPAttribute
	public static final String ELEMENTTYPE = "elementType";
	
	//type of entity
	private String m_elementType;
	
	public HAPStoryIdElementType() {}
	
	public HAPStoryIdElementType(String elementType) {
		this.m_elementType = elementType;
	}
	
	public String getElementType() {    return this.m_elementType;    }
	
	@Override
	public boolean equals(Object obj) {
		boolean out = false;
		if(obj instanceof HAPStoryIdElementType) {
			HAPStoryIdElementType elementTypeId = (HAPStoryIdElementType)obj;
			return elementTypeId.getElementType().equals(this.getElementType());
		}
		return out;
	}
	
	public String getKey() {     return this.m_elementType;      }
	private void parseKey(String key) {    this.m_elementType = key;     }
	
	@Override
	public int hashCode() {		return this.getKey().hashCode();	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(ELEMENTTYPE, this.m_elementType);
	}

	@Override
	protected boolean buildObjectByLiterate(String literateValue){
		this.parseKey(literateValue);
		return true;  
	}
	
	@Override
	protected boolean buildObjectByJson(Object obj){
		if(obj instanceof JSONObject) {
			JSONObject jsonObj = (JSONObject)obj;
			this.m_elementType = (String)jsonObj.opt(ELEMENTTYPE);
		}
		else if(obj instanceof String) {
			parseKey((String)obj);
		}
		return true;  
	}
	
}
