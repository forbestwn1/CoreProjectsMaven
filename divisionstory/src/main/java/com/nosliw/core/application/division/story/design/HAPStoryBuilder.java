package com.nosliw.core.application.division.story.design;

import org.springframework.stereotype.Component;

//stateless
//   update design according to change request
@Component
public interface HAPStoryBuilder {

	String getBuilderId();
	
	HAPStoryBuilderResponseNew initDesign(HAPStoryDesign design);
	
	//design story according to change
	//   storyDesign: current design
	//   change : change
	//out: design after change
	HAPStoryBuilderResponseBuild buildStory(HAPStoryDesign storyDesign, HAPStoryBuilderRequest changeRequest);
	
}
