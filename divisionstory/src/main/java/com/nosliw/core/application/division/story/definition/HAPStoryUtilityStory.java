package com.nosliw.core.application.division.story.definition;

import java.util.List;

import com.nosliw.common.path.HAPPath;

public class HAPStoryUtilityStory {

	public static List<HAPStoryContainerChildrenElementsWrapper> getChildren(HAPStoryStory story, HAPStoryIdElement elementId, HAPPath childPath){
		HAPStoryElement parentelement = story.getElement(elementId);
		
	}

	public static HAPStoryContainerChildrenElements getDescendants(HAPStoryStory story, HAPStoryElement element, HAPPath childPath) {
		HAPStoryResultElementChild result = element.tryGetChild(childPath.toString());
		
		
		
	}
	
}
