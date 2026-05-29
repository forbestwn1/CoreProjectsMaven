package com.nosliw.core.application.division.story.definition;

import com.nosliw.common.path.HAPPath;

public interface HAPStoryElementWithConstant {

	public static final String CHILD_CONSTANT = "constant";

	public static HAPPath getAddConstantChildPath() {	   return HAPStoryUtilityStory.buildChildPathForElement(new HAPPath(CHILD_CONSTANT));   }

	public static HAPPath getConstantEndPointPath(String contantName) {
		return new HAPPath(new String[] {CHILD_CONSTANT, contantName, HAPStoryElementWithEndPoint.CHILD_ENDPOINT});
	}
}
