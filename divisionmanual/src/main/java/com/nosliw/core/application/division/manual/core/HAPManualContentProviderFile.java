package com.nosliw.core.application.division.manual.core;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPUtilityFile;
import com.nosliw.core.application.HAPBundleForBrick;
import com.nosliw.core.application.HAPIdBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionInfoBrickLocation;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionUtilityBrickLocation;
import com.nosliw.core.application.dynamic.HAPDynamicDefinitionContainer;
import com.nosliw.core.application.dynamic.HAPDynamicUtilityParser;
import com.nosliw.core.application.entity.brickcriteria.HAPManagerBrickCriteria;

public class HAPManualContentProviderFile implements HAPManualContentProvider{

	private HAPManagerBrickCriteria m_brickCriteriaMan;
	
	private HAPIdBrick m_brickId;
	
	private HAPManualDefinitionInfoBrickLocation m_entityLocationInfo;
	
	private HAPDynamicDefinitionContainer m_dynamicDefContainer = new HAPDynamicDefinitionContainer();
	
	private Map<String, HAPManualInfoContent> m_branchContents = new LinkedHashMap<String, HAPManualInfoContent>();
	
	private HAPManualInfoContent m_mainContent;
	
	public HAPManualContentProviderFile(HAPIdBrick brickId, HAPManagerBrickCriteria brickCriteriaMan) {
		this.m_brickCriteriaMan = brickCriteriaMan;
		this.m_brickId = brickId;
		this.init();
	}

	
	private void init() {
		
		this.m_entityLocationInfo = HAPManualDefinitionUtilityBrickLocation.getBrickLocationInfo(this.m_brickId);
		
		
		//bundle info
		if(!this.m_entityLocationInfo.getIsSingleFile()) {
			//if folder based, try to get bundle info
			File bundleInfoFile = new File(this.m_entityLocationInfo.getBasePath().getPath()+"/bundle.json");
			if(bundleInfoFile.exists()) {
				//if bundle file exists, parse bundle file
				JSONObject bundleInfoObj = new JSONObject(HAPUtilityFile.readFile(bundleInfoFile));
				Object dynamicTaskObj = bundleInfoObj.opt(HAPBundleForBrick.DYNAMIC);
				if(dynamicTaskObj!=null) {
					HAPDynamicUtilityParser.parseDynamicDefinitionContainer(dynamicTaskObj, m_dynamicDefContainer, this.m_brickCriteriaMan);
				}
			}

			//if folder based, try to get branch info
			//branch
			Map<String, HAPManualDefinitionInfoBrickLocation> branchInfos = HAPManualDefinitionUtilityBrickLocation.getBranchBrickLocationInfos(this.m_entityLocationInfo.getBasePath().getPath());
			for(String branchName : branchInfos.keySet()) {
				m_branchContents.put(branchName, buildContentInfo(branchInfos.get(branchName)));
			}
		}
		
		this.m_mainContent = this.buildContentInfo(m_entityLocationInfo);
		
	}

	private HAPManualInfoContent buildContentInfo(HAPManualDefinitionInfoBrickLocation locationInfo) {
		HAPSerializationFormat format = locationInfo.getFormat();
		String content = HAPUtilityFile.readFile(locationInfo.getFiile());
		return new HAPManualInfoContent(content, format, locationInfo.getBrickTypeId());
	}
	
	@Override
	public HAPDynamicDefinitionContainer getDynamicDefinition() {
		return this.m_dynamicDefContainer;
	}
	
	@Override
	public HAPManualInfoContent getMainContent() {
		return this.m_mainContent;
	}
	
	@Override
	public Map<String, HAPManualInfoContent> getBranchContents(){
		return this.m_branchContents;
	}
	
	@Override
	public HAPManualInfoContent getLocalBrickContent(HAPIdBrick brickId) {
		HAPManualDefinitionInfoBrickLocation entityLocationInfo = HAPManualDefinitionUtilityBrickLocation.getLocalBrickLocationInfo(this.m_entityLocationInfo.getBasePath().getPath(), brickId);
		String content = HAPUtilityFile.readFile(entityLocationInfo.getFiile());
		return new HAPManualInfoContent(content, entityLocationInfo.getFormat(), entityLocationInfo.getBrickTypeId());
	}
	
}
