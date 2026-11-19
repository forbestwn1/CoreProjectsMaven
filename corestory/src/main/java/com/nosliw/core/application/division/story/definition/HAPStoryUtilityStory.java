package com.nosliw.core.application.division.story.definition;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;

public class HAPStoryUtilityStory {

    public static HAPStoryElement getDescendantElement(HAPStoryIdElement baseElement, HAPPath subPath, HAPStoryStory story) {
    	return getDescendantElement(new HAPStoryPath(baseElement, subPath), story);
    }

    public static HAPStoryElement getDescendantElement(HAPStoryPath storyPath, HAPStoryStory story) {
    	if(storyPath.getPath()==null||storyPath.getPath().isEmpty()) {
    		return story.getElement(storyPath.getBaseStoryElementId());
    	}
    	
    	return story.getElement(getDescendant(storyPath, story).getChildElement().getElementId());
    }
	
	public static HAPStoryContainerChildrenElementsWrapper getDescendant(HAPStoryPath storyPath, HAPStoryStory story) {
    	HAPStoryContainerChildrenElements currentContainer = story.getElement(storyPath.getBaseStoryElementId()).getChildren();
    	
    	HAPStoryResultContainerChild result;
    	do {
        	result = HAPStoryUtilityContainer.tryGetChildElement(currentContainer, storyPath.getPath().toString());
    	}while(result.getRemainingPath()!=null && !result.getRemainingPath().isEmpty());
    	
        return (HAPStoryContainerChildrenElementsWrapper)result.getChildContainer();
    }

    public static HAPStoryContainerChildrenElementsCollection getDescendantCollection(HAPStoryPath storyPath, HAPStoryStory story) {
    	HAPStoryContainerChildrenElements currentContainer = story.getElement(storyPath.getBaseStoryElementId()).getChildren();
    	HAPPath currentPath = storyPath.getPath();
    	
    	HAPStoryResultContainerChild result;
    	do {
    		if(currentContainer.getContainerType().equals(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_WRAPPER)) {
    			currentContainer = story.getElement(((HAPStoryContainerChildrenElementsWrapper)currentContainer).getChildElement().getElementId()).getChildren();
    		}
    		
        	result = HAPStoryUtilityContainer.tryGetChildCollection(currentContainer, currentPath.toString());
        	currentContainer = result.getChildContainer();
        	currentPath = result.getRemainingPath();
    	}while(result.getRemainingPath()!=null && !result.getRemainingPath().isEmpty());
    	
        return (HAPStoryContainerChildrenElementsCollection)result.getChildContainer();
    }

    
}
