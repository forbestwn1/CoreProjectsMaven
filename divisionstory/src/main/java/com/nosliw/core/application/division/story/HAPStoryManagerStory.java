package com.nosliw.core.application.division.story;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.HAPBundle;
import com.nosliw.core.application.HAPIdBrick;
import com.nosliw.core.application.HAPIdBrickType;
import com.nosliw.core.application.HAPPluginDivision;
import com.nosliw.core.application.division.manual.core.HAPManualManagerBrick;
import com.nosliw.core.application.division.manual.core.process.HAPManualContentProviderText;
import com.nosliw.core.application.division.story.design.HAPStoryManagerDesign;
import com.nosliw.core.runtime.HAPRuntimeInfo;

@Component
public class HAPStoryManagerStory implements HAPPluginDivision{

	@Autowired
	private HAPStoryManagerDesign m_storyDesignMan;
	
	@Autowired
	private HAPManualManagerBrick m_manulBrickManager;
	
	@Override
	public String getDivisionName() {  return HAPConstantShared.BRICK_DIVISION_STORY;   }

	@Override
	public HAPBundle getBundle(HAPIdBrick brickId, HAPRuntimeInfo runtimeInfo) {
		HAPManualContentProviderText contentProvider = this.m_storyDesignMan.convertDesignToManual(brickId.getId());
		return this.m_manulBrickManager.buildBundle(contentProvider, runtimeInfo);
	}

	@Override
	public Set<HAPIdBrickType> getBrickTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
