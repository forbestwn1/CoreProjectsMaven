package com.nosliw.core.application.division.story.definition;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.nosliw.common.utils.HAPConstantShared;

//for element container 
public class HAPStoryContainerChildrenElementsList extends HAPStoryContainerChildrenElements{

	private int m_index;
	
	private List<Pair<String, HAPStoryContainerChildrenElements>> m_childElement;
	
	public HAPStoryContainerChildrenElementsList() {
		super(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_LIST);
		this.m_index = 0;
		this.m_childElement = new ArrayList<Pair<String, HAPStoryContainerChildrenElements>>();
	}

    public HAPStoryContainerChildrenElements getChildContainer(int childIndex) {
    	return this.m_childElement.get(childIndex).getRight();
    }
    
    public HAPStoryContainerChildrenElements getChildContainer(String id) {
    	for(Pair<String, HAPStoryContainerChildrenElements> pair : this.m_childElement) {
    		if(id.equals(pair.getLeft())) {
    			return pair.getRight();
    		}
    	}
    	return null;
    }
    
    public String newChildContainer(HAPStoryContainerChildrenElements childContainer) {
    	String id = this.generateId();
    	this.m_childElement.add(Pair.of(id, childContainer));
    	return id;
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

	
}
