package com.nosliw.core.application.division.story.design;

import org.springframework.stereotype.Component;

import com.nosliw.common.exception.HAPServiceData;

//stateless
//   update design according to change request
@Component
public interface HAPStoryBuilder {

	String getBuilderId();
	
	void initDesign(HAPStoryDesign design);
	
	//design story according to change
	//   storyDesign: current design
	//   change : change
	//out: design after change
	HAPServiceData buildStory(HAPStoryDesign storyDesign, HAPStoryBuilderRequest changeRequest);
	
}
