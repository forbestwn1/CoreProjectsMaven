package com.nosliw.core.application.division.story.definition;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryContainerChildrenElementsWrapper extends HAPStoryContainerChildrenElements{

	public final static String ID = "id";
	
	public final static String ALIAS = "alias";
	
	public final static String CHILDELEMENT = "childElement";

	private String m_id;
	
	private String m_alias;
	
	private HAPStoryChildElement m_childElement;

	public HAPStoryContainerChildrenElementsWrapper() {
		super(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_WRAPPER);
	}

	public HAPStoryContainerChildrenElementsWrapper(HAPStoryChildElement childElement) {
		this();
		this.m_childElement = childElement;
	}

	public String getId() {     return this.m_id;      }
	public void setId(String id) {     this.m_id = id;       }
	
	public String getAlias() {      return this.m_alias;       }
	public void setAlias(String alias) {     this.m_alias = alias;      }
	
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
		jsonMap.put(ID, this.m_id);
		jsonMap.put(ALIAS, this.m_alias);
	}	
}

@Component
class HAPStoryContainerChildrenElementsSingle__HAPEntityParsable extends HAPStoryContainerChildrenElements__HAPEntityParsable{

	@Override
	public String getSubName() {   return HAPConstantShared.STORYELEMENTCHILDREN_TYPE_WRAPPER;   }
	
	protected void parseToEntity(JSONObject jsonObj, HAPStoryContainerChildrenElementsWrapper container, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, container, parseService);

		container.setId((String)jsonObj.opt(HAPStoryContainerChildrenElementsWrapper.ID));
		container.setAlias((String)jsonObj.opt(HAPStoryContainerChildrenElementsWrapper.ALIAS));
		
		JSONObject childJsonObj = jsonObj.getJSONObject(HAPStoryContainerChildrenElementsWrapper.CHILDELEMENT);
		
   	    HAPStoryChildElement childEle = new HAPStoryChildElement();
   	    childEle.setElementId(new HAPStoryIdElement(childJsonObj.getString(HAPStoryChildElement.ELEMENTID))); 
   	    
   	    JSONObject metadataJsonObj = childJsonObj.optJSONObject(HAPStoryChildElement.METADATA);
   	    if(metadataJsonObj!=null) {
   	    	childEle.setMetaData(HAPStoryMetaDataChildElement.parseMetaData(metadataJsonObj, parseService));
   	    }
   	    container.setChildElement(childEle);
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryContainerChildrenElementsWrapper out = new HAPStoryContainerChildrenElementsWrapper();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}
}
