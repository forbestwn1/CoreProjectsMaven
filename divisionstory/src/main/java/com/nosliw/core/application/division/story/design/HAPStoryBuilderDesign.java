package com.nosliw.core.application.division.story.design;

import com.nosliw.common.exception.HAPServiceData;

//stateless
//   update design according to change request
public interface HAPStoryBuilderDesign {

	String getBuilderId();
	
	void initDesign(HAPStoryDesignStory design);
	
	//design story according to change
	//   storyDesign: current design
	//   change : change
	//out: design after change
	HAPServiceData buildStory(HAPStoryDesignStory storyDesign, HAPStoryRequestDesign changeRequest);
	
}
