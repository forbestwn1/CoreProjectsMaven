package com.nosliw.core.application.division.story;

import java.io.File;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.HAPBrick;
import com.nosliw.core.application.HAPBundleForBrick;
import com.nosliw.core.application.HAPIdBrick;
import com.nosliw.core.application.HAPIdBrickType;
import com.nosliw.core.application.HAPPluginDivision;
import com.nosliw.core.application.common.serialize.HAPUtilityExport;
import com.nosliw.core.application.division.manual.core.HAPManualContentProviderFile;
import com.nosliw.core.application.division.manual.core.HAPManualManagerBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionUtilityBrickLocation;
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
		
		File bundleFolder = new File(HAPStoryUtility.getDesignConverBundleFolder(brickId));
		out = HAPUtilityExport.importBundle(bundleFolder.getAbsolutePath(), HAPSerializationFormat.JSON, m_parseService);
		
		if(out==null) {
			File manualFolder = new File(HAPStoryUtilityConverter.getDesignConverToManualFolder(brickId)); 
			if(!manualFolder.exists()) {
				manualFolder = new File(this.m_storyDesignMan.convertDesignToManual(brickId));
			}
			
			HAPManualContentProviderFile contentProvider = new HAPManualContentProviderFile(brickId, HAPManualDefinitionUtilityBrickLocation.buildBrickLocationInfoFromMainFolder(brickId.getBrickTypeId(), manualFolder), m_brickCriteriaMan, this.m_parseService);
			out = this.m_manulBrickManager.buildBundle(contentProvider, runtimeInfo);
			HAPUtilityExport.exportBundle(out, bundleFolder.getAbsolutePath(), HAPSerializationFormat.JSON);
		}
		return out;
	}

	@Override
	public Set<HAPIdBrickType> getBrickTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HAPBrick deserializeBrick(Object obj, HAPSerializationFormat format) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
