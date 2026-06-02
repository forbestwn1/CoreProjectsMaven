package com.nosliw.core.application.division.story.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

//for element container 
public class HAPStoryContainerChildrenElementsList extends HAPStoryContainerChildrenElementsCollection{

	public static final String INDEX = "index";

	public static final String CHILDREN = "children";
	
	private int m_index;
	
	private List<Pair<String, HAPStoryContainerChildrenElements>> m_childrenElement;
	
	public HAPStoryContainerChildrenElementsList() {
		super(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_LIST);
		this.m_index = 0;
		this.m_childrenElement = new ArrayList<Pair<String, HAPStoryContainerChildrenElements>>();
	}

	public void setIndex(int index) {   this.m_index = index;      }
	
    public Pair<String, HAPStoryContainerChildrenElements> getChildContainer(int childIndex) {    	return this.m_childrenElement.get(childIndex);    }
    void addChildContainerPair(Pair<String, HAPStoryContainerChildrenElements> containerPair) {  this.m_childrenElement.add(containerPair);     }
    
    @Override
	public HAPStoryContainerChildrenElements getChildContainer(String id) {
    	for(Pair<String, HAPStoryContainerChildrenElements> pair : this.m_childrenElement) {
    		if(id.equals(pair.getLeft())) {
    			return pair.getRight();
    		}
    	}
    	return null;
    }
    
    public String newChildContainer(HAPStoryContainerChildrenElements childContainer) {
    	String id = this.generateId();
    	this.m_childrenElement.add(Pair.of(id, childContainer));
    	return id;
    }

	@Override
	public HAPStoryContainerChildrenElementsSingle removeChild(String childName) {
		int i = 0;
		for(; i<this.m_childrenElement.size(); i++) {
			Pair<String, HAPStoryContainerChildrenElements> child = this.m_childrenElement.get(i);
			if(child.getLeft().equals(childName)) {
				break;
			}
		}
		
		return (HAPStoryContainerChildrenElementsSingle)this.m_childrenElement.remove(i).getRight();
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
		
		List<String> chilrendArrayStr = new ArrayList<String>();
		for(Pair<String, HAPStoryContainerChildrenElements> child : this.m_childrenElement) {
			List<String> childArrayStr = new ArrayList<String>();
			childArrayStr.add(child.getLeft());
			childArrayStr.add(child.getRight().toStringValue(HAPSerializationFormat.JSON));
			chilrendArrayStr.add(HAPUtilityJson.buildArrayJson(childArrayStr.toArray(new String[0])));
		}
		jsonMap.put(CHILDREN, HAPUtilityJson.buildArrayJson(chilrendArrayStr.toArray(new String[0])));
	}

}

@Component
class HAPStoryContainerChildrenElementsList__HAPEntityParsable extends HAPStoryContainerChildrenElements__HAPEntityParsable{

	@Override
	public String getSubName() {   return HAPConstantShared.STORYELEMENTCHILDREN_TYPE_LIST;   }
	
	protected void parseToEntity(JSONObject jsonObj, HAPStoryContainerChildrenElementsList container, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, container, parseService);
		 container.setIndex(jsonObj.getInt(HAPStoryContainerChildrenElementsList.INDEX));
		 
		 JSONArray childrenJsonArray = jsonObj.getJSONArray(HAPStoryContainerChildrenElementsList.CHILDREN);
		 for(int i=0; i<childrenJsonArray.length(); i++) {
			 JSONArray childJsonArray = childrenJsonArray.getJSONArray(i);
			 String childId = childJsonArray.getString(0);
			 HAPStoryContainerChildrenElements childContainer = HAPStoryContainerChildrenElements.parseChildrenContainer(childJsonArray.getJSONObject(1), parseService);
			 container.addChildContainerPair(Pair.of(childId, childContainer));
		 }
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryContainerChildrenElementsList out = new HAPStoryContainerChildrenElementsList();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}
}
