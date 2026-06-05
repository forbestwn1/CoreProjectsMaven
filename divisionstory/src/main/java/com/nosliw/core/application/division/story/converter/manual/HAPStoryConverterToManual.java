package com.nosliw.core.application.division.story.converter.manual;

import com.nosliw.core.application.division.manual.core.HAPManualContentProviderText;
import com.nosliw.core.application.division.story.definition.HAPStoryAliasElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryStory;

public class HAPStoryConverterToManual {

	public HAPManualContentProviderText convert(HAPStoryStory story) {
		
		//get module element (root)
		HAPStoryElement moduleElement = story.getElement(new HAPStoryAliasElement(HAPStoryStory.ALIAS_ROOT));
		
		//get page elemtns
		
		
	}
	
	
	
}
