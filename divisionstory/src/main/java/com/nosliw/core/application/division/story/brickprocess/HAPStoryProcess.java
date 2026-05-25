package com.nosliw.core.application.division.story.brickprocess;

import com.nosliw.core.application.HAPBrick;
import com.nosliw.core.application.division.story.definition.HAPStoryAliasElement;
import com.nosliw.core.application.division.story.definition.HAPStoryStory;
import com.nosliw.core.application.division.story.definition.element.HAPStoryElementEntityModule;

public class HAPStoryProcess {

	public static HAPBrick process(HAPStoryStory story) {
		//get root element
		HAPStoryElementEntityModule moduleElement = (HAPStoryElementEntityModule)story.getElement(new HAPStoryAliasElement(HAPStoryStory.ALIAS_ROOT));
		
		//build brick tree
		
		
		//process value port
		
		//process variable
		
		//process brick
		
		//process adapter
		
		//post process
		
		//process task
		
	}
	
	
}
