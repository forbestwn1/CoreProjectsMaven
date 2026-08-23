package com.nosliw.application.core.external.story;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.brick.HAPBundleForBrick;
import com.nosliw.core.application.brick.HAPIdBrick;
import com.nosliw.core.application.brick.HAPIdBrickType;
import com.nosliw.core.application.entity.brick.HAPPluginDivision;
import com.nosliw.core.runtime.HAPRuntimeInfo;

@Component
public class HAPDivisionStory implements HAPPluginDivision{

	@Autowired
	private HAPServiceStory m_storyService;
	
	@Override
	public String getDivisionName() {  return HAPConstantShared.BRICK_DIVISION_STORY;   }

	@Override
	public HAPBundleForBrick getBundle(HAPIdBrick brickId, HAPRuntimeInfo runtimeInfo) {     return this.m_storyService.getBundle(brickId, runtimeInfo);   }

	@Override
	public Set<HAPIdBrickType> getBrickTypes() {
		// TODO Auto-generated method stub
		return null;
	}

}
