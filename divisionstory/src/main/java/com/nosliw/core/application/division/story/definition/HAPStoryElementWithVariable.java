package com.nosliw.core.application.division.story.definition;

import com.nosliw.common.path.HAPPath;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementAccessoryVariable;

public interface HAPStoryElementWithVariable {

	public static final String CHILD_VARIABLE = "variable";

	public static HAPPath buildPathForVariableEndPoint(String variableName) {
		return new HAPPath(new String[] { HAPStoryUtilityElement.buildChildNameFromPath(new String[] {HAPStoryElementWithVariable.CHILD_VARIABLE, variableName}), HAPStoryElementAccessoryVariable.CHILD_ENDPOINT});
	}
}
