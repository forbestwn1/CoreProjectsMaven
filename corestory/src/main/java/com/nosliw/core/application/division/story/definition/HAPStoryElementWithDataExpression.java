package com.nosliw.core.application.division.story.definition;

import com.nosliw.common.path.HAPPath;

public interface HAPStoryElementWithDataExpression {

	public static final String CHILD_DATAEXPRESSION = "dataExpression";

	public static HAPPath getAddDataExpressionChildPath(String dataExpressionName) {	   return HAPStoryUtilityElement.getAddElementChildPath(new HAPPath(CHILD_DATAEXPRESSION), dataExpressionName);   }

}
