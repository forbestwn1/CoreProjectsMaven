package com.nosliw.core.application.division.story.definition;

import com.nosliw.common.path.HAPPath;

public interface HAPStoryElementWithCommand {

	public static final String CHILD_TASK = "task";
	
	public static HAPPath getAddTaskChildPath() {	   return HAPStoryUtilityStory.buildChildPathForElement(new HAPPath(CHILD_TASK));   }
	
}
