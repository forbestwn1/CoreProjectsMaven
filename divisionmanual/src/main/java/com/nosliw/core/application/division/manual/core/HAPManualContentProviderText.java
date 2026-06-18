package com.nosliw.core.application.division.manual.core;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.core.application.HAPIdBrick;
import com.nosliw.core.application.dynamic.HAPDynamicDefinitionContainer;

public class HAPManualContentProviderText implements HAPManualContentProvider{

	private HAPManualInfoContent m_contentInfo;
	
	private Map<String, HAPManualInfoContent> m_branchContentInfos;
	
	private Map<String, HAPManualInfoContent> m_localContentInfos;
	
	public HAPManualContentProviderText() {
		this.m_branchContentInfos = new LinkedHashMap<String, HAPManualInfoContent>();
		this.m_localContentInfos = new LinkedHashMap<String, HAPManualInfoContent>();
	}
	
	@Override
	public HAPDynamicDefinitionContainer getDynamicDefinition() {
		return new HAPDynamicDefinitionContainer();
	}

	@Override
	public HAPManualInfoContent getMainContent() {
		return this.m_contentInfo;
	}
	
	public void setMainContent(HAPManualInfoContent contentInfo) {
		this.m_contentInfo = contentInfo;
	}

	@Override
	public Map<String, HAPManualInfoContent> getBranchContents() {
		return this.m_branchContentInfos;
	}
	
	public void addBranchContent(String name, HAPManualInfoContent contentInfo) {
		this.m_branchContentInfos.put(name, contentInfo);
	}

	@Override
	public HAPManualInfoContent getLocalBrickContent(HAPIdBrick brickId) {
		return this.m_localContentInfos.get(brickId.getKey());
	}
	
	public Map<String, HAPManualInfoContent> getLocalBrickContents(){
		return this.m_localContentInfos;
	}
	
	public void addLocalBrickContent(HAPIdBrick brickId, HAPManualInfoContent contentInfo) {
		this.m_localContentInfos.put(brickId.getKey(), contentInfo);
	}

}
