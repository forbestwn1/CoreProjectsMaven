package com.nosliw.core.application.division.manual.core.process;

import java.util.Map;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.common.utils.HAPUtilityFile;
import com.nosliw.core.application.HAPIdBrick;
import com.nosliw.core.application.HAPIdBrickType;
import com.nosliw.core.application.division.manual.core.HAPManualContentProviderText;
import com.nosliw.core.application.division.manual.core.HAPManualInfoContent;

public class HAPManualUtilityExporterContentProviderText {

	public static void export(HAPManualContentProviderText cotentProvider, String exportFolder) {
		
		formatContentProviderText(cotentProvider);
		
		HAPUtilityFile.deleteFolder(exportFolder);
		
		HAPUtilityFile.writeFile(exportFolder + "/main."+cotentProvider.getMainContent().getFormat(), cotentProvider.getMainContent().getContent());
		
		Map<String, HAPManualInfoContent> localContents = cotentProvider.getLocalBrickContents();
		for(String key : localContents.keySet()) {
			HAPIdBrick brickId = new HAPIdBrick();
			brickId.buildObject(key, HAPSerializationFormat.LITERATE);

			HAPIdBrickType brickTypeId = brickId.getBrickTypeId();
			
			HAPManualInfoContent localContent = localContents.get(key);
			HAPUtilityFile.writeFile(exportFolder + "/local/" + brickTypeId.getBrickType() + "/" + brickTypeId.getVersion() + "/" + brickId.getId() + "." +localContent.getFormat(), localContent.getContent());
			
		}
		
	}
	
	private static void formatContentProviderText(HAPManualContentProviderText contentProvider) {
		formatContent(contentProvider.getMainContent());
		
		for(HAPManualInfoContent contentInfo : contentProvider.getLocalBrickContents().values()) {
			formatContent(contentInfo);
		}
		
		for(HAPManualInfoContent contentInfo : contentProvider.getBranchContents().values()) {
			formatContent(contentInfo);
		}
		
	}
	
	private static void formatContent(HAPManualInfoContent contentInfo) {
		if(contentInfo.getFormat()==HAPSerializationFormat.JSON) {
			contentInfo.setContent(HAPUtilityJson.formatJson(contentInfo.getContent()));
		}
	}
	
}
