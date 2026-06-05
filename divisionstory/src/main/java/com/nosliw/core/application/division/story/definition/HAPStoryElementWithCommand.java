package com.nosliw.core.application.division.story.definition;

import com.nosliw.common.path.HAPPath;

public interface HAPStoryElementWithCommand {

	public static final String CHILD_COMMAND = "command";
	
	public static HAPPath getAddCommandChildPath() {	   return HAPStoryUtilityElement.buildChildPathForElement(new HAPPath(CHILD_COMMAND));   }
	
	public static HAPPath getAddCommandChildPath(String alias) {	   return HAPStoryUtilityElement.buildChildPathForElement(new HAPPath(CHILD_COMMAND), alias);   }
	
}
