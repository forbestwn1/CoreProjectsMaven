package com.nosliw.core.application.division.manual.core.standalone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.core.application.HAPBundleForBrick;
import com.nosliw.core.application.common.command.HAPCommandProcess;
import com.nosliw.core.application.common.event.HAPEventEmitter;
import com.nosliw.core.application.division.manual.core.HAPManualContentProviderText;
import com.nosliw.core.application.division.manual.core.HAPManualInfoContent;
import com.nosliw.core.application.division.manual.core.HAPManualManagerBrick;
import com.nosliw.core.runtime.HAPRuntimeInfo;

@Component
public class HAPManualManangerStandalone {

	@Autowired
	private HAPManualManagerBrick m_manualBrickMan;

	public HAPBundleForBrick buildStandalone(HAPStandaloneDefinition definition, HAPRuntimeInfo runtimeInfo) {
		
		HAPManualContentProviderText contentProvider = new HAPManualContentProviderText();

		HAPManualInfoContent maintContentInfo = new HAPManualInfoContent(definition.getContent(), definition.getFormat(), definition.getBrickTypeId());
		contentProvider.setMainContent(maintContentInfo);
		
		for(HAPEventEmitter eventEmitter : definition.getExposeEvents()) {
			contentProvider.addExposedEvent(eventEmitter);
		}
	
		for(HAPCommandProcess command : definition.getExposeCommands()) {
			contentProvider.addExposedCommand(command);
		}
		
		return this.m_manualBrickMan.buildBundle(contentProvider, runtimeInfo);
	}
	
}
