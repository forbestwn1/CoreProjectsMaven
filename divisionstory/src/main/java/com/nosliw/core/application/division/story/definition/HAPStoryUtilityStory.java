package com.nosliw.core.application.division.story.definition;

import java.util.List;

import com.nosliw.common.path.HAPPath;

public class HAPStoryUtilityStory {

    public static HAPStoryElement getDescendantElement(HAPStoryPath storyPath, HAPStoryStory story) {
    	
    }
	
	public static HAPStoryContainerChildrenElementsWrapper getDescendant(HAPStoryPath storyPath, HAPStoryStory story) {
    	
    	HAPStoryContainerChildrenElementsAttributes elementAttrContainer = story.getElement(storyPath.getBaseStoryElementId()).getChildren();
    	
    	
    	HAPStoryUtilityContainer.tryGetChildElement(elementAttrContainer, storyPath.getPath());
    	
    	
    }

    public static HAPStoryContainerChildrenElementsCollection getDescendantCollection(HAPStoryPath storyPath, HAPStoryStory story) {
    	
    }

    
	public static List<HAPStoryContainerChildrenElementsWrapper> getChildren(HAPStoryStory story, HAPStoryIdElement elementId, HAPPath childPath){
		HAPStoryElement parentelement = story.getElement(elementId);
		
	}

	public static HAPStoryContainerChildrenElements getDescendants(HAPStoryStory story, HAPStoryElement element, HAPPath childPath) {
		HAPStoryResultContainerChild result = element.tryGetChild(childPath.toString());
		
		
		
	}
	
}
