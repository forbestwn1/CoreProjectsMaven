package com.nosliw.core.application.division.story.definition;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryContainerChildrenElementsSingle extends HAPStoryContainerChildrenElements{

	public final static String CHILDELEMENT = "childElement";
	
	private HAPStoryChildElement m_childElement;

	public HAPStoryContainerChildrenElementsSingle() {
		super(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_SINGLE);
	}

	public HAPStoryContainerChildrenElementsSingle(HAPStoryChildElement childElement) {
		this();
		this.m_childElement = childElement;
	}

	public HAPStoryChildElement getChildElement() {     return this.m_childElement;      }
	public void setChildElement(HAPStoryChildElement childElement) {      this.m_childElement = childElement;      }

	@Override
	public HAPStoryContainerChildrenElements cloneContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(CHILDELEMENT, this.m_childElement.toStringValue(HAPSerializationFormat.JSON));
	}	
}

@Component
class HAPStoryContainerChildrenElementsSingle__HAPEntityParsable extends HAPStoryContainerChildrenElements__HAPEntityParsable{

	@Override
	public String getSubName() {   return HAPConstantShared.STORYELEMENTCHILDREN_TYPE_SINGLE;   }
	
	protected void parseToEntity(JSONObject jsonObj, HAPStoryContainerChildrenElementsSingle container, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, container, parseService);
   	    JSONObject childJsonObj = jsonObj.getJSONObject(HAPStoryContainerChildrenElementsSingle.CHILDELEMENT);
		
   	    HAPStoryChildElement childEle = new HAPStoryChildElement();
   	    childEle.setElementId(new HAPStoryIdElement(childJsonObj.getString(HAPStoryChildElement.ELEMENTID))); 
   	    
   	    JSONObject metadataJsonObj = childJsonObj.optJSONObject(HAPStoryChildElement.METADATA);
   	    if(metadataJsonObj!=null) {
   	    	childEle.setMetaData(HAPStoryMetaDataChildElement.parseMetaData(childJsonObj, parseService));
   	    }
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryContainerChildrenElementsSingle out = new HAPStoryContainerChildrenElementsSingle();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}
}
