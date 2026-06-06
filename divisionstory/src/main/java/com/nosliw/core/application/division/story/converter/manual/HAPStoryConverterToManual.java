package com.nosliw.core.application.division.story.converter.manual;

import java.util.List;

import com.nosliw.core.application.division.manual.core.HAPManualContentProviderText;
import com.nosliw.core.application.division.story.definition.HAPStoryAliasElement;
import com.nosliw.core.application.division.story.definition.HAPStoryContainerChildrenElementsWrapper;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryStory;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementEntityModule;

public class HAPStoryConverterToManual {

	public HAPManualContentProviderText convert(HAPStoryStory story) {
		
		//get module element (root)
		HAPStoryElement moduleElement = story.getElement(new HAPStoryAliasElement(HAPStoryStory.ALIAS_ROOT));
		
		//get page elemtns
		List<HAPStoryContainerChildrenElementsWrapper> pagesChildren = moduleElement.getChildCollection(HAPStoryElementEntityModule.CHILD_PAGE);
		
	}
	
	
	
}
