package com.nosliw.core.application.division.story.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityBasic;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

//for element container 
public class HAPStoryContainerChildrenElementsCollection extends HAPStoryContainerChildrenElementsMultiple{

	public static final String INDEX = "index";

	public static final String CHILDREN = "children";
	
	private int m_index;
	
	private List<HAPStoryContainerChildrenElementsWrapper> m_childrenElement;
	
	public HAPStoryContainerChildrenElementsCollection() {
		super(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_COLLECTION);
		this.m_index = 0;
		this.m_childrenElement = new ArrayList<HAPStoryContainerChildrenElementsWrapper>();
	}

	public void setIndex(int index) {   this.m_index = index;      }
	
	public List<HAPStoryContainerChildrenElementsWrapper> getChildren(){    return this.m_childrenElement;      }
	
    public HAPStoryContainerChildrenElementsWrapper getChildContainer(int childIndex) {   return this.m_childrenElement.get(childIndex);   } 
    void addChildContainerPair(HAPStoryContainerChildrenElementsWrapper element) {  this.m_childrenElement.add(element);     }
    
    @Override
	public HAPStoryContainerChildrenElements getChildContainer(String id) {
		int index = this.getChild(id);
        return this.m_childrenElement.get(index);
    }
    
    public HAPStoryContainerChildrenElementsWrapper newChildContainer(HAPStoryChildElement childElement) {
    	return this.newChildContainer(childElement, null);
    }

    public HAPStoryContainerChildrenElementsWrapper newChildContainer(HAPStoryChildElement childElement, String alias) {
    	String id = this.generateId();
    	HAPStoryContainerChildrenElementsWrapper wrapper = new HAPStoryContainerChildrenElementsWrapper(childElement);
    	wrapper.setAlias(alias);
    	wrapper.setId(id);
    	this.m_childrenElement.add(wrapper);
    	return wrapper;
    }

	@Override
	public HAPStoryContainerChildrenElementsWrapper removeChild(String childName) {
		int index = this.getChild(childName);
		return this.m_childrenElement.remove(index);
	}

	private int getChild(String idOrAlias) {
		int i = 0;
		for(; i<this.m_childrenElement.size(); i++) {
			HAPStoryContainerChildrenElementsWrapper child = this.m_childrenElement.get(i);
			if(HAPUtilityBasic.isEquals(child.getId(), idOrAlias)) {
				return i;
			}
			if(HAPUtilityBasic.isEquals(child.getAlias(), idOrAlias)) {
				return i;
			}
		}
		return -1;
	}
	
    private String generateId() {
    	this.m_index++;
    	return "id" + this.m_index;
    }
    
	@Override
	public HAPStoryContainerChildrenElements cloneContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(INDEX, this.m_index+"");
		typeJsonMap.put(INDEX, Integer.class);
		jsonMap.put(CHILDREN, HAPManagerSerialize.getInstance().toStringValue(this.m_childrenElement, HAPSerializationFormat.JSON));
	}

}

@Component
class HAPStoryContainerChildrenElementsList__HAPEntityParsable extends HAPStoryContainerChildrenElements__HAPEntityParsable{

	@Override
	public String getSubName() {   return HAPConstantShared.STORYELEMENTCHILDREN_TYPE_COLLECTION;   }
	
	protected void parseToEntity(JSONObject jsonObj, HAPStoryContainerChildrenElementsCollection container, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, container, parseService);
		 container.setIndex(jsonObj.getInt(HAPStoryContainerChildrenElementsCollection.INDEX));
		 
		 JSONArray childrenJsonArray = jsonObj.getJSONArray(HAPStoryContainerChildrenElementsCollection.CHILDREN);
		 for(int i=0; i<childrenJsonArray.length(); i++) {
			 JSONObject childJsonObj = childrenJsonArray.getJSONObject(i);
			 HAPStoryContainerChildrenElementsWrapper child = (HAPStoryContainerChildrenElementsWrapper)HAPStoryContainerChildrenElements.parseChildrenContainer(childJsonObj, parseService);
			 container.addChildContainerPair(child);
		 }
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryContainerChildrenElementsCollection out = new HAPStoryContainerChildrenElementsCollection();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}
}
