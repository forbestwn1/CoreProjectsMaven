package com.nosliw.core.application.division.story.definition;

import com.nosliw.common.path.HAPPath;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementAccessoryConstant;

public interface HAPStoryElementWithConstant {

	public static final String CHILD_CONSTANT = "constant";

	public static HAPPath buildPathForConstantEndPoint(String constantName) {
		return new HAPPath(new String[] { HAPStoryUtilityElement.buildChildNameFromPath(new String[] {HAPStoryElementWithConstant.CHILD_CONSTANT, constantName}), HAPStoryElementAccessoryConstant.CHILD_ENDPOINT});
	}
}
