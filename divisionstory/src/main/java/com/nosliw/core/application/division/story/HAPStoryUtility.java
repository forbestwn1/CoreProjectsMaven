package com.nosliw.core.application.division.story;

import com.nosliw.core.application.HAPIdBrick;
import com.nosliw.core.application.division.story.design.HAPStoryDesignUtilityExport;

public class HAPStoryUtility {

	public static String getDesignConverBundleFolder(HAPIdBrick brickId) {
		return HAPStoryDesignUtilityExport.getDesignFolder(brickId).getAbsolutePath()+"/bundle";
	}

}
