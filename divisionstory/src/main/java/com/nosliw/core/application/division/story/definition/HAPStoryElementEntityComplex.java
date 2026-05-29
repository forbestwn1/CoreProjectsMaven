package com.nosliw.core.application.division.story.definition;

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
	
}
