package com.nosliw.core.application.division.story.definition;

import com.nosliw.common.path.HAPPath;

public interface HAPStoryElementWithCommand {

	public static final String CHILD_COMMAND = "command";
	
	public static HAPPath getAddCommandChildPath() {	   return HAPStoryUtilityStory.buildChildPathForElement(new HAPPath(CHILD_COMMAND));   }
	
	
}
