package com.nosliw.core.application.division.story.definition;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;

public class HAPStoryReferenceElementWrapper extends HAPSerializableImp implements HAPStoryWithAlias{

	private HAPStoryIdElement m_elementId;
	
	private HAPStoryAlias m_alias;

	public HAPStoryReferenceElementWrapper() {}
	
	public HAPStoryReferenceElementWrapper(HAPStoryReferenceElement elementRef) {
		if(elementRef instanceof HAPStoryIdElement) {
			this.m_elementId = (HAPStoryIdElement)elementRef;
		} else if(elementRef instanceof HAPStoryAlias) {
			this.m_alias = (HAPStoryAlias)elementRef;
		}
	}

	public HAPStoryIdElement getElementId() {
		if(m_elementId==null) {
			throw new RuntimeException();
		}
		return this.m_elementId;
	}
	
	public HAPStoryAlias getAlias() {
		return this.m_alias;
	}
	
	@Override
	public void processAlias(HAPStoryStory story) {
		if(this.m_elementId==null) {
			this.m_elementId = story.getElementId(this.m_alias.getName());
		}
	}
	
	public HAPStoryReferenceElementWrapper cloneElementReferenceWrapper() {
		HAPStoryReferenceElementWrapper out = new HAPStoryReferenceElementWrapper();
		if(this.m_alias!=null) {
			out.m_alias = (HAPStoryAlias)this.m_alias.cloneElementReference();
		}
		if(this.m_elementId!=null) {
			out.m_elementId = (HAPStoryIdElement)this.m_elementId.cloneElementReference();
		}
		return out;
	}
	
	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;

		HAPStoryIdElement eleId = new HAPStoryIdElement();
		eleId.buildObject(jsonObj, HAPSerializationFormat.JSON);
		if(eleId.getTypeId()!=null) {
			this.m_elementId = eleId;
		}
		
		HAPStoryAlias alias = new HAPStoryAlias();
		alias.buildObject(jsonObj, HAPSerializationFormat.JSON);
		if(alias.getName()!=null) {
			this.m_alias = alias;
		}
		
		return true;  
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		if(this.m_alias!=null) {
			this.m_alias.buildJsonMap(jsonMap, typeJsonMap);
		}
		if(this.m_elementId!=null) {
			this.m_elementId.buildJsonMap(jsonMap, typeJsonMap);
		}
	}
}