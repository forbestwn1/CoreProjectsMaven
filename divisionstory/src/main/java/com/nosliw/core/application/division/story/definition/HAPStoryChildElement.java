package com.nosliw.core.application.division.story.definition;

import java.util.Map;

import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;

public class HAPStoryChildElement extends HAPSerializableImp{

	public static final String ELEMENTID = "elementId";
	
	public static final String METADATA = "metaData";
	
	private HAPStoryIdElement m_elementId;
	
	private HAPStoryMetaDataChildElement m_metaData;
	
	public HAPStoryChildElement() {}
	
	public HAPStoryChildElement(HAPStoryIdElement elementId, HAPStoryMetaDataChildElement metaData) {
		this.m_elementId = elementId;
		this.m_metaData = metaData;
	}
	
	public HAPStoryIdElement getElementId() {    return this.m_elementId;     }
	public void setElementId(HAPStoryIdElement eleId) {     this.m_elementId = eleId;     }
	
	public HAPStoryMetaDataChildElement getMetaData() {    return this.m_metaData;      }
	public void setMetaData(HAPStoryMetaDataChildElement metaData) {     this.m_metaData = metaData;       }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(ELEMENTID, this.m_elementId.getKey());
		if(this.m_metaData!=null) {
			jsonMap.put(METADATA, this.m_metaData.toStringValue(HAPSerializationFormat.JSON));
		}
	}	
	
}
