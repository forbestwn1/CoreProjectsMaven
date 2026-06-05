package com.nosliw.core.application.division.story.definition;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

//for attribute
public class HAPStoryContainerChildrenElementsAttributes extends HAPStoryContainerChildrenElementsMultiple{

	public static final String CHILDREN = "children";
	
	
	private Map<String, HAPStoryContainerChildrenElements> m_childElement;
	
	public HAPStoryContainerChildrenElementsAttributes() {
		super(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_ATTRIBUTES);
		this.m_childElement = new LinkedHashMap<String, HAPStoryContainerChildrenElements>();
	}

	public Map<String, HAPStoryContainerChildrenElements> getChildrenContainer(){
		return this.m_childElement;
	}
	
    @Override
	public HAPStoryContainerChildrenElements getChildContainer(String childName) {
    	return this.m_childElement.get(childName);
    }
    
    public HAPStoryContainerChildrenElements newChildContainer(String childName, HAPStoryChildElement child) {
    	HAPStoryContainerChildrenElementsWrapper wrapper = new HAPStoryContainerChildrenElementsWrapper(child);
    	return this.newChildContainer(childName, wrapper);
    }
    
    
    public HAPStoryContainerChildrenElements newChildContainer(String childName, HAPStoryContainerChildrenElements childContainer) {
    	this.addChildContainer(childName, childContainer);
    	return childContainer;
    }
    
    void addChildContainer(String childName, HAPStoryContainerChildrenElements childContainer) {
    	this.m_childElement.put(childName, childContainer);
    }

    @Override
	public HAPStoryContainerChildrenElementsWrapper removeChild(String childName) {
    	return (HAPStoryContainerChildrenElementsWrapper)this.m_childElement.remove(childName);
    }
    
	@Override
	public HAPStoryContainerChildrenElements cloneContainer() {
		// TODO Auto-generated method stub
		return null;
	}
 
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		Map<String, String> chilrendMapStr = new LinkedHashMap<String, String>();
		for(String name : this.m_childElement.keySet()) {
			chilrendMapStr.put(name, this.m_childElement.get(name).toStringValue(HAPSerializationFormat.JSON));
		}
		jsonMap.put(CHILDREN, HAPUtilityJson.buildMapJson(chilrendMapStr));
	}
	
}

@Component
class HAPStoryContainerChildrenElementsMap__HAPEntityParsable extends HAPStoryContainerChildrenElements__HAPEntityParsable{

	@Override
	public String getSubName() {   return HAPConstantShared.STORYELEMENTCHILDREN_TYPE_ATTRIBUTES;   }
	
	protected void parseToEntity(JSONObject jsonObj, HAPStoryContainerChildrenElementsAttributes container, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, container, parseService);
   	    JSONObject childrenJsonMap = jsonObj.getJSONObject(HAPStoryContainerChildrenElementsAttributes.CHILDREN);
		
		for(Object key : childrenJsonMap.keySet()) {
			String name = (String)key;
 		    HAPStoryContainerChildrenElements childContainer = HAPStoryContainerChildrenElements.parseChildrenContainer(childrenJsonMap.getJSONObject(name), parseService);
			container.addChildContainer(name, childContainer);
		}
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryContainerChildrenElementsAttributes out = new HAPStoryContainerChildrenElementsAttributes();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}
}

