package com.nosliw.core.application.division.story.definition;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializable;

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
	
}
