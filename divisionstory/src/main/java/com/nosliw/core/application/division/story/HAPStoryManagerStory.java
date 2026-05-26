package com.nosliw.core.application.division.story;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.core.application.HAPBundle;
import com.nosliw.core.application.HAPIdBrick;
import com.nosliw.core.application.HAPIdBrickType;
import com.nosliw.core.application.HAPPluginDivision;
import com.nosliw.core.application.division.story.definition.HAPStoryStory;
import com.nosliw.core.application.division.story.design.HAPStoryManagerDesign;
import com.nosliw.core.runtime.HAPRuntimeInfo;

@Component
public class HAPStoryManagerStory implements HAPPluginDivision{

	@Autowired
	private HAPStoryManagerDesign m_storyDesignMan;
	
	@Override
	public String getDivisionName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HAPBundle getBundle(HAPIdBrick brickId, HAPRuntimeInfo runtimeInfo) {
		HAPBundle out = new HAPBundle();
		
		HAPStoryStory story = this.m_storyDesignMan.getDesign(brickId.getId()).getStory();
		
		
		
		return null;
	}

	@Override
	public Set<HAPIdBrickType> getBrickTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
