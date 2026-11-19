package com.nosliw.core.application.common.manual;

import java.util.Map;

public class HAPManualContentPrivderUtility {

	public static HAPManualContentProviderText fromFileToText(HAPManualContentProviderFile contentFile) {
		HAPManualContentProviderText out = new HAPManualContentProviderText();
		
		out.setMainContent(contentFile.getMainContent());
		
		Map<String, HAPManualInfoContent> branchContents = contentFile.getBranchContents();
		for(String name : branchContents.keySet()) {
			out.addBranchContent(name, branchContents.get(name));
		}
		
		out.setDyanmicDefinition(contentFile.getDynamicDefinition());
		
//		contentFile.getLocalBrickContent(null)
		
		return out;
	}
	
	
}
