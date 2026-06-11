package com.nosliw.core.application.division.story.definition;

public class HAPStoryUtilityStory {

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
    	
    	HAPStoryResultContainerChild result;
    	do {
        	result = HAPStoryUtilityContainer.tryGetChildElement(currentContainer, storyPath.getPath().toString());
    	}while(result.getRemainingPath()!=null && !result.getRemainingPath().isEmpty());
    	
        return (HAPStoryContainerChildrenElementsCollection)result.getChildContainer();
    }

    
}
