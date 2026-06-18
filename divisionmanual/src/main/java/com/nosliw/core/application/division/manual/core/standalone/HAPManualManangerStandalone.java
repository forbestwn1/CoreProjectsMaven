package com.nosliw.core.application.division.manual.core.standalone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.core.application.HAPBundleForBrick;
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
		
		return this.m_manualBrickMan.buildBundle(contentProvider, runtimeInfo);
		
	}
	
}
