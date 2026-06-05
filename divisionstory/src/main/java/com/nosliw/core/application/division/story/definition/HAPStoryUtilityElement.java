package com.nosliw.core.application.division.story.definition;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPUtilityNamingConversion;

public class HAPStoryUtilityElement {

	public static HAPPath buildChildPathForElement(HAPPath basePath) {
		HAPPath out = new HAPPath(basePath);
		return out.appendSegment(HAPStoryElement.SEG_ELEMENT);
	}
	
	public static HAPPath buildChildPathForElement(HAPPath basePath, String alias) {
		HAPPath out = new HAPPath(basePath);
		if(alias==null) {
			return out.appendSegment(HAPStoryElement.SEG_ELEMENT);
		} else {
			return out.appendSegment(HAPUtilityNamingConversion.cascadeDetail(HAPStoryElement.SEG_ELEMENT, alias));
		}
	}
}
