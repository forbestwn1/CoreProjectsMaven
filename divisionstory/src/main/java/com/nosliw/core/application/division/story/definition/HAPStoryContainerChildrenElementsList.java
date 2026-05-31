package com.nosliw.core.application.division.story.definition;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.nosliw.common.utils.HAPConstantShared;

//for element container 
public class HAPStoryContainerChildrenElementsList extends HAPStoryContainerChildrenElementsCollection{

	private int m_index;
	
	private List<Pair<String, HAPStoryContainerChildrenElements>> m_childrenElement;
	
	public HAPStoryContainerChildrenElementsList() {
		super(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_LIST);
		this.m_index = 0;
		this.m_childrenElement = new ArrayList<Pair<String, HAPStoryContainerChildrenElements>>();
	}

    public Pair<String, HAPStoryContainerChildrenElements> getChildContainer(int childIndex) {
    	return this.m_childrenElement.get(childIndex);
    }
    
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

	
}
