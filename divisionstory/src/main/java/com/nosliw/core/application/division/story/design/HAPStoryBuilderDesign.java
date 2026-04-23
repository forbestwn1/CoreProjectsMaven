package com.nosliw.core.application.division.story.design;

//stateless
//   update design according to change request
public interface HAPStoryBuilderDesign {

	
	void initDesign(HAPStoryDesignStory design);
	
	//design story according to change
	//   storyDesign: current design
	//   change : change
	//out: design after change
	HAPServiceData buildStory(HAPStoryDesignStory storyDesign, HAPStoryRequestDesign changeRequest);
	
	
	
	
}
