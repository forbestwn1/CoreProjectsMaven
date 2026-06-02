package com.nosliw.core.application.division.story.definition;

import java.util.Map;

//entity element (module, page, datasource, ....)
public abstract class HAPStoryElementEntityComplex 
                        extends HAPStoryElement 
                        implements HAPStoryElementWithCommand, 
                                   HAPStoryElementWithConstant, 
                                   HAPStoryElementWithVariable, 
                                   HAPStoryElementWithEvent,
                                   HAPStoryElementWithTask{

	
	public HAPStoryElementEntityComplex(HAPStoryIdElementType elementType) {
		super(elementType);
	}

	protected void cloneToStoryElement(HAPStoryElementEntityComplex storyEle) {
		super.cloneToStoryElement(storyEle);
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
	}
}
