package com.nosliw.core.application.common.manual;

import java.nio.file.Path;
import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.common.utils.HAPUtilityFileNio;
import com.nosliw.core.application.brick.HAPIdBrick;
import com.nosliw.core.application.brick.HAPIdBrickType;

public class HAPManualUtilityExporterContentProvider {

	public static Path getPathFileForContentText(Path exportFolder) {
		return HAPUtilityFileNio.buildPath(exportFolder, "text.json");
	}
	
	public static Path getPathDirForContentFile(Path exportFolder) {
		return HAPUtilityFileNio.buildPath(exportFolder, "file");
	}

	public static HAPManualContentProviderText importContentText(Path rootFolder, HAPServiceParseEntity parseService) {
		Path contentPathText = getPathFileForContentText(rootFolder);
		String content = HAPUtilityFileNio.readFile(contentPathText);
		HAPManualContentProviderText out = (HAPManualContentProviderText)parseService.parseEntityJSONExplicit(new JSONObject(content), HAPManualContentProviderText.class.getName());
		return out;
	}

	public static HAPManualContentProviderFile importContentFile(HAPManualContentProviderText cotentProvider, Path rootFolder, HAPServiceParseEntity parseService) {
//		Path contentPathFile = getPathFileForContentFile(rootFolder);
//		
//		HAPManualContentProviderFile contentProvider = new HAPManualContentProviderFile(brickId, HAPManualDefinitionUtilityBrickLocation.buildBrickLocationInfoFromMainFolder(brickId.getBrickTypeId(), manualFolder), m_brickCriteriaMan, this.m_parseService);
//		
//		
//		String content = HAPUtilityFileNio.readFile(contentPathText);
//		HAPManualContentProviderText out = (HAPManualContentProviderText)parseService.parseEntityJSONExplicit(new JSONObject(content), HAPManualContentProviderText.class.getName());
//		return out;
	}
	
	public static void export(HAPManualContentProviderText cotentProvider, Path rootFolder) {
		
		formatContentProviderText(cotentProvider);
		
		HAPUtilityFileNio.deletePath(rootFolder);
		
		Path contentPathFile = getPathDirForContentFile(rootFolder);
		Path contentPathText = getPathFileForContentText(rootFolder);
		
		HAPUtilityFileNio.writeFile(contentPathText, cotentProvider.toStringValue(HAPSerializationFormat.JSON));
		
		HAPUtilityFileNio.writeFile(contentPathFile, "main."+cotentProvider.getMainContent().getFormat(), cotentProvider.getMainContent().getContent());
		
		Map<String, HAPManualInfoContent> localContents = cotentProvider.getLocalBrickContents();
		for(String key : localContents.keySet()) {
			HAPIdBrick brickId = new HAPIdBrick();
			brickId.buildObject(key, HAPSerializationFormat.LITERATE);

			HAPIdBrickType brickTypeId = brickId.getBrickTypeId();
			
			HAPManualInfoContent localContent = localContents.get(key);
			
			HAPUtilityFileNio.writeFile(HAPUtilityFileNio.buildPath(contentPathFile, "local", brickTypeId.getBrickType(), brickTypeId.getVersion()), brickId.getId() + "." +localContent.getFormat(), localContent.getContent());
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
