package com.nosliw.core.application.division.manual.common.contentprovider;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nosliw.core.application.HAPIdBrick;
import com.nosliw.core.application.common.command.HAPCommandProcess;
import com.nosliw.core.application.common.event.HAPEventEmitter;
import com.nosliw.core.application.division.manual.core.HAPManualInfoContent;
import com.nosliw.core.application.dynamic.HAPDynamicDefinitionContainer;

public class HAPManualContentProviderText implements HAPManualContentProvider{

	private HAPManualInfoContent m_contentInfo;
	
	private Map<String, HAPManualInfoContent> m_branchContentInfos;
	
	private Map<String, HAPManualInfoContent> m_localContentInfos;
	
	private List<HAPEventEmitter> m_eventExpose;
	
	private List<HAPCommandProcess> m_commandExpose;
	
	public HAPManualContentProviderText() {
		this.m_branchContentInfos = new LinkedHashMap<String, HAPManualInfoContent>();
		this.m_localContentInfos = new LinkedHashMap<String, HAPManualInfoContent>();
		this.m_eventExpose = new ArrayList<HAPEventEmitter>();
		this.m_commandExpose = new ArrayList<HAPCommandProcess>();
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

	@Override
	public List<HAPEventEmitter> getExposedEvent() {		return this.m_eventExpose;	}
	public void addExposedEvent(HAPEventEmitter exposedEvent) {		this.m_eventExpose.add(exposedEvent);	}

	@Override
	public List<HAPCommandProcess> getExposedCommand(){     return this.m_commandExpose;      }
	public void addExposedCommand(HAPCommandProcess exposedCommand) {    this.m_commandExpose.add(exposedCommand);         }
}
