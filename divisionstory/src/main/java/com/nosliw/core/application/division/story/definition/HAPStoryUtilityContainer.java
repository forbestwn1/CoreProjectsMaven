package com.nosliw.core.application.division.story.definition;

import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityBasic;
import com.nosliw.common.utils.HAPUtilityNamingConversion;

public class HAPStoryUtilityContainer {

	public static HAPStoryResultContainerChild tryGetChildElement(HAPStoryContainerChildrenElements currentContainer, String childPath) {
        if(HAPUtilityBasic.isStringEmpty(childPath)) {
			return new HAPStoryResultContainerChild(currentContainer, null);
		}
		
		HAPPath remainingPath = new HAPPath();
		HAPStoryContainerChildrenElementsWrapper childEleWrapper = null;
		
		String[] segs = HAPUtilityNamingConversion.parsePaths(childPath);
		boolean searching = true;
		for(int i=0; i<segs.length; i++) {
			String seg = segs[i];
			if(searching==true) {
				currentContainer = HAPStoryUtilityContainer.getChildContainer(currentContainer, seg);
				
				if(currentContainer==null) {
					if(i==segs.length-1) {
						return new HAPStoryResultContainerChild(null, remainingPath);
					} else {
						throw new RuntimeException();
					}
				}
				
				if(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_WRAPPER.equals(currentContainer.getContainerType())) {
					childEleWrapper = (HAPStoryContainerChildrenElementsWrapper)currentContainer;
					searching = false;
				}
			}
			else {
				remainingPath.appendSegment(seg);
			}
		}
		
		if(childEleWrapper==null) {
			throw new RuntimeException();
		}
		return new HAPStoryResultContainerChild(childEleWrapper, remainingPath);
	}

	public static HAPStoryResultContainerChild tryGetChildCollection(HAPStoryContainerChildrenElements currentContainer, String childPath) {
        if(HAPUtilityBasic.isStringEmpty(childPath)) {
			return new HAPStoryResultContainerChild(currentContainer, null);
		}

		HAPPath remainingPath = new HAPPath();
		
		String[] segs = HAPUtilityNamingConversion.parsePaths(childPath);
		boolean searching = true;
		for(int i=0; i<segs.length; i++) {
			String seg = segs[i];
			if(searching==true) {
				currentContainer = HAPStoryUtilityContainer.getChildContainer(currentContainer, seg);
				
				if(currentContainer==null) {
					if(i==segs.length-1) {
						return new HAPStoryResultContainerChild(null, remainingPath);
					} else {
						throw new RuntimeException();
					}
				}
				
				if(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_WRAPPER.equals(currentContainer.getContainerType())) {
					searching = false;
				}
			}
			else {
				remainingPath.appendSegment(seg);
			}
		}
		
		if(remainingPath.isEmpty()&&!currentContainer.getContainerType().equals(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_COLLECTION)) {
			throw new RuntimeException();
		}
		if(!remainingPath.isEmpty()&&!currentContainer.getContainerType().equals(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_WRAPPER)) {
			throw new RuntimeException();
		}
		return new HAPStoryResultContainerChild(currentContainer, remainingPath);
	}

	
	public static HAPStoryContainerChildrenElements getChildContainer(HAPStoryContainerChildrenElements currentContainer, String seg) {
		HAPStoryContainerChildrenElements out = null;
		String containerType = currentContainer.getContainerType();
		if(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_ATTRIBUTES.equals(containerType)) {
			out = ((HAPStoryContainerChildrenElementsAttributes)currentContainer).getChildContainer(seg);
		}
		else if(HAPConstantShared.STORYELEMENTCHILDREN_TYPE_COLLECTION.equals(containerType)) {
			if(HAPUtilityBasic.isNumber(seg)){
				//by index
				out = ((HAPStoryContainerChildrenElementsCollection)currentContainer).getChildContainer(Integer.valueOf(seg));
			}
			else {
				out = ((HAPStoryContainerChildrenElementsCollection)currentContainer).getChildContainer(seg);
			}
		}
		return out;
	}
	
	public static HAPStoryContainerChildrenElementsWrapper removeChild(HAPStoryContainerChildrenElements currentContainer, HAPPath childPath) {
		String[] segs = childPath.getPathSegments();
		for(int i=0; i<segs.length; i++) {
            String seg = segs[i];
            if(i==segs.length-1) {
            	return ((HAPStoryContainerChildrenElementsMultiple)currentContainer).removeChild(seg);
            }
            else {
            	currentContainer = HAPStoryUtilityContainer.getChildContainer(currentContainer, seg);
            }
		}
		return null;
	}

	public static HAPStoryContainerChildrenElements newElementsChildrenContainerAccordingToSeg(String seg) {
		HAPStoryContainerChildrenElements out = null;
		
		if(HAPUtilityBasic.isStringEmpty(seg)) {
		}
		else if(seg.startsWith(HAPStoryElement.SEG_ELEMENT) || HAPUtilityBasic.isNumber(seg)) {
			out = new HAPStoryContainerChildrenElementsCollection();
		}
		else {
			out = new HAPStoryContainerChildrenElementsAttributes();
		}
		return out;
	}


}
