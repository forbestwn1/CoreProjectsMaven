package com.nosliw.core.application.division.story.definition;

import com.nosliw.common.path.HAPPath;

public class HAPStoryUtilityStory {

	public static HAPPath buildChildPathForElement(HAPPath basePath) {
		HAPPath out = new HAPPath(basePath);
		out.appendSegment(HAPStoryElement.SEG_ELEMENT);
		return out;
	}

	
	
}
