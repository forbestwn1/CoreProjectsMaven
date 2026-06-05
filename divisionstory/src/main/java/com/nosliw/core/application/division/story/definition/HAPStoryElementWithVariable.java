package com.nosliw.core.application.division.story.definition;

import com.nosliw.common.path.HAPPath;

public interface HAPStoryElementWithVariable {

	public static final String CHILD_VARIABLE = "variable";

	public static HAPPath getAddVariableChildPath() {	   return HAPStoryUtilityElement.buildChildPathForElement(new HAPPath(CHILD_VARIABLE));   }

	public static HAPPath getVariableEndPointPath(String contantName) {
		return new HAPPath(new String[] {CHILD_VARIABLE, contantName, HAPStoryElementWithEndPoint.CHILD_ENDPOINT});
	}
}
