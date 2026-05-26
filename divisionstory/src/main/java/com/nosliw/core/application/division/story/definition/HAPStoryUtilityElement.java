package com.nosliw.core.application.division.story.definition;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityBasic;
import com.nosliw.common.utils.HAPUtilityNamingConversion;

public class HAPStoryUtilityElement {

	
	private static void transversElement(HAPStoryContainerChildrenElements childrenElementContainer, HAPPath path, HAPStoryHandlerElement handler, HAPStoryStory story) {
		
		String containerType = childrenElementContainer.getContainerType();
		if(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_MAP.equals(containerType)) {
			currentContainer = ((HAPStoryContainerChildrenElementsMap)childrenElementContainer).getChildContainer(seg);
		}
		else if(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_LIST.equals(containerType)) {
			currentContainer = ((HAPStoryContainerChildrenElementsList)childrenElementContainer).getChildContainer(Integer.valueOf(seg));
		}
		
		
		
	}
	
	
	
	
	public static HAPPath parseChildNameToPath(String childName) {
		if(HAPUtilityBasic.isStringEmpty(childName)) {
			return null;
		}
		return new HAPPath(HAPUtilityNamingConversion.parseNameSegments(childName));
	}
	
	public static String buildChildNameFromPath(String[] segs) {
		if(segs==null||segs.length==0) {
			return null;
		}
		
		String out = null;
		for(int i=0; i<segs.length; i++) {
			if(i==0) {
				out = segs[0];
			} else {
				out = HAPUtilityNamingConversion.cascadeNameSegment(out, segs[i]);
			}
		}
		return out;
	}
	
}
