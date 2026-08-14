package com.nosliw.core.application.division.story;

import java.nio.file.Path;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityFileNio;
import com.nosliw.core.application.HAPBundleForBrick;
import com.nosliw.core.application.HAPIdBrick;
import com.nosliw.core.application.HAPIdBrickType;
import com.nosliw.core.application.HAPPluginDivision;
import com.nosliw.core.application.common.brick.serialize.HAPUtilityExport;
import com.nosliw.core.application.division.manual.common.contentprovider.HAPManualContentProviderFile;
import com.nosliw.core.application.division.manual.core.HAPManualManagerBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionUtilityBrickLocation1;
import com.nosliw.core.application.division.story.converter.manual.HAPStoryUtilityConverter;
import com.nosliw.core.application.division.story.design.HAPStoryManagerDesign;
import com.nosliw.core.application.entity.brickcriteria.HAPManagerBrickCriteria;
import com.nosliw.core.runtime.HAPRuntimeInfo;

@Component
public class HAPStoryManagerStory implements HAPPluginDivision{

	@Autowired
	private HAPStoryManagerDesign m_storyDesignMan;
	
	@Autowired
	private HAPManualManagerBrick m_manulBrickManager;

	@Autowired
	private HAPManagerBrickCriteria m_brickCriteriaMan;
	
	@Autowired
	private HAPServiceParseEntity m_parseService;
	
	@Override
	public String getDivisionName() {  return HAPConstantShared.BRICK_DIVISION_STORY;   }

	@Override
	public HAPBundleForBrick getBundle(HAPIdBrick brickId, HAPRuntimeInfo runtimeInfo) {
		
		HAPBundleForBrick out = null;
		
		Path rootPath = this.m_storyDesignMan.getStoryStorageRootPath();
		
		Path bundleFolder = HAPStoryUtilityConverter.getDesignConverBundleFolder(rootPath, brickId);
		out = HAPUtilityExport.importBundle(bundleFolder, HAPSerializationFormat.JSON, m_parseService);
		
		if(out==null) {
			Path manualFolder = HAPStoryUtilityConverter.getDesignConverToManualFolder(rootPath, brickId); 
			
			if(!HAPUtilityFileNio.isPathExists(manualFolder)) {
				manualFolder = this.m_storyDesignMan.convertDesignToManual(brickId);
			}
			
			HAPManualContentProviderFile contentProvider = new HAPManualContentProviderFile(brickId, HAPManualDefinitionUtilityBrickLocation1.buildBrickLocationInfoFromMainFolder(brickId.getBrickTypeId(), manualFolder), m_brickCriteriaMan, this.m_parseService);
			out = this.m_manulBrickManager.buildBundle(contentProvider, runtimeInfo);
			HAPUtilityExport.exportBundle(out, bundleFolder, HAPSerializationFormat.JSON);
		}
		return out;
	}

	@Override
	public Set<HAPIdBrickType> getBrickTypes() {
		// TODO Auto-generated method stub
		return null;
	}

}
