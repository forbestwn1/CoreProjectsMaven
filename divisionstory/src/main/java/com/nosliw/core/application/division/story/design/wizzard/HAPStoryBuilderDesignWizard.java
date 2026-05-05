package com.nosliw.core.application.division.story.design.wizzard;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.core.application.division.story.HAPStoryStory;
import com.nosliw.core.application.division.story.design.HAPStoryBuilderDesign;
import com.nosliw.core.application.division.story.design.HAPStoryDesignStory;
import com.nosliw.core.application.division.story.design.HAPStoryRequestDesign;

public class HAPStoryBuilderDesignWizard implements HAPStoryBuilderDesign{

	@Override
	public String getBuilderId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initDesign(HAPStoryDesignStory design) {
		HAPStoryStory story = design.getStory();
		
		
		
	}

	@Override
	public HAPServiceData buildStory(HAPStoryDesignStory storyDesign, HAPStoryRequestDesign changeRequest) {
		HAPStoryRequestDesignWizard wiardRequest = (HAPStoryRequestDesignWizard)changeRequest;
		
		
		// TODO Auto-generated method stub
		return null;
	}

}
