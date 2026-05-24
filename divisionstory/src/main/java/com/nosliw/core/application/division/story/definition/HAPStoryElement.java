package com.nosliw.core.application.division.story.definition;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.path.HAPPath;
import com.nosliw.common.serialization.HAPSerializable;
import com.nosliw.common.utils.HAPUtilityBasic;
import com.nosliw.common.utils.HAPUtilityNamingConversion;

@HAPEntityWithAttribute
public interface HAPStoryElement extends HAPSerializable{

	@HAPAttribute
	public static final String ELEMENTID = "elementId";
	
	@HAPAttribute
	public static final String ELEMENTTYPE = "elementType";
	
	HAPStoryIdElement getElementId();
	
	HAPStoryIdElementType getElementType();
	
	boolean addChild(HAPStoryElement ele, String childName);
	
	HAPStoryIdElement getChild(String childName);
	
	HAPStoryElement cloneStoryElement();
	
	default HAPPath parseChildNameToPath(String childName) {
		if(HAPUtilityBasic.isStringEmpty(childName)) {
			return null;
		}
		return new HAPPath(HAPUtilityNamingConversion.parseNameSegments(childName));
	}
	
	default String buildChildNameFromPath(String[] segs) {
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
