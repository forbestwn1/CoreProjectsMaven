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
import com.nosliw.common.utils.HAPUtilityBasic;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

//for element container 
public class HAPStoryContainerChildrenElementsList extends HAPStoryContainerChildrenElementsCollection{

	public static final String INDEX = "index";

	public static final String CHILDREN = "children";
	
	private int m_index;
	
	private List<HAPStoryContainerChildrenElementsList_Element> m_childrenElement;
	
	public HAPStoryContainerChildrenElementsList() {
		super(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_LIST);
		this.m_index = 0;
		this.m_childrenElement = new ArrayList<HAPStoryContainerChildrenElementsList_Element>();
	}

	public void setIndex(int index) {   this.m_index = index;      }
	
    public Pair<String, HAPStoryContainerChildrenElements> getChildContainer(int childIndex) {  
    	HAPStoryContainerChildrenElementsList_Element child = this.m_childrenElement.get(childIndex);
    	return Pair.of(child.id, child.child);
    }
    void addChildContainerPair(String id, HAPStoryContainerChildrenElements element, String alias) {  this.m_childrenElement.add(new HAPStoryContainerChildrenElementsList_Element(id, element, alias));     }
    
    @Override
	public HAPStoryContainerChildrenElements getChildContainer(String id) {
		int index = this.getChild(id);
        return this.m_childrenElement.get(index).child;
    }
    
    public String newChildContainer(HAPStoryContainerChildrenElements childContainer) {
    	return this.newChildContainer(childContainer);
    }

    public String newChildContainer(HAPStoryContainerChildrenElements childContainer, String alias) {
    	String id = this.generateId();
    	this.m_childrenElement.add(new HAPStoryContainerChildrenElementsList_Element(id, childContainer, alias));
    	return id;
    }

	@Override
	public HAPStoryContainerChildrenElementsSingle removeChild(String childName) {
		int index = this.getChild(childName);
		return (HAPStoryContainerChildrenElementsSingle)this.m_childrenElement.remove(index).child;
	}

	private int getChild(String idOrAlias) {
		int i = 0;
		for(; i<this.m_childrenElement.size(); i++) {
			HAPStoryContainerChildrenElementsList_Element child = this.m_childrenElement.get(i);
			if(HAPUtilityBasic.isEquals(child.id, idOrAlias)) {
				return i;
			}
			if(HAPUtilityBasic.isEquals(child.alias, idOrAlias)) {
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
		
		List<String> chilrendArrayStr = new ArrayList<String>();
		for(HAPStoryContainerChildrenElementsList_Element child : this.m_childrenElement) {
			List<String> childArrayStr = new ArrayList<String>();
			childArrayStr.add(child.id);
			childArrayStr.add(child.child.toStringValue(HAPSerializationFormat.JSON));
			if(child.alias!=null) {
				childArrayStr.add(child.alias);
			}
			chilrendArrayStr.add(HAPUtilityJson.buildArrayJson(childArrayStr.toArray(new String[0])));
		}
		jsonMap.put(CHILDREN, HAPUtilityJson.buildArrayJson(chilrendArrayStr.toArray(new String[0])));
	}

}

class HAPStoryContainerChildrenElementsList_Element{

	public HAPStoryContainerChildrenElementsList_Element(String id, HAPStoryContainerChildrenElements child, String alias) {
		this.id = id;
		this.child = child;
		this.alias = alias;
	}
	
	public String id;

	public String alias;
	
	public HAPStoryContainerChildrenElements child;
	
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
			 String alias = null;
			 if(childJsonArray.length()>=3) {
				alias = childJsonArray.getString(2);
			 }
			 
			 container.addChildContainerPair(childId, childContainer, alias);
		 }
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryContainerChildrenElementsList out = new HAPStoryContainerChildrenElementsList();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}
}
