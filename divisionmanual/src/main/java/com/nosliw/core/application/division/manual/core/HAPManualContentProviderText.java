package com.nosliw.core.application.division.manual.core;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.core.application.HAPIdBrick;
import com.nosliw.core.application.dynamic.HAPDynamicDefinitionContainer;

public class HAPManualContentProviderText implements HAPManualContentProvider{

	private HAPManualInfoContent m_contentInfo;
	
	public HAPManualContentProviderText(HAPManualInfoContent contentInfo) {
		this.m_contentInfo = contentInfo;
	}
	
	@Override
	public HAPDynamicDefinitionContainer getDynamicDefinition() {
		return new HAPDynamicDefinitionContainer();
	}

	@Override
	public HAPManualInfoContent getMainContent() {
		return this.m_contentInfo;
	}

	@Override
	public Map<String, HAPManualInfoContent> getBranchContents() {
		return new LinkedHashMap<String, HAPManualInfoContent>();
	}

	@Override
	public HAPManualInfoContent getLocalBrickContent(HAPIdBrick brickId) {
		return null;
	}

}
