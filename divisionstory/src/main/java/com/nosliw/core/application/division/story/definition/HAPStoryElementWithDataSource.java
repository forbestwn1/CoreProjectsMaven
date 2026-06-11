package com.nosliw.core.application.division.story.definition;

import com.nosliw.common.path.HAPPath;

public class HAPStoryElementWithDataSource {

	public static final String CHILD_DATASOURCE = "dataSource";
	
	public static HAPPath getAddDataSourceChildPath() {	   return HAPStoryUtilityElement.getAddElementChildPath(new HAPPath(CHILD_DATASOURCE));   }
	
	
}
