package com.nosliw.core.application.division.story.api;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityFileNio;
import com.nosliw.core.application.brick.HAPBundleForBrick;
import com.nosliw.core.application.brick.HAPIdBrick;
import com.nosliw.core.application.common.brick.serialize.HAPUtilityExport;
import com.nosliw.core.application.common.gateway.HAPUtilityGateway;
import com.nosliw.core.application.common.manual.HAPManualContentProviderText;
import com.nosliw.core.application.common.manual.HAPManualUtilityExporterContentProvider;
import com.nosliw.core.application.common.manual.gateway.compile.HAPManualGatewayCompile;
import com.nosliw.core.application.division.story.converter.manual.HAPStoryConverterToManual;
import com.nosliw.core.application.division.story.design.HAPStoryConfigure;
import com.nosliw.core.application.division.story.design.HAPStoryDesign;
import com.nosliw.core.application.division.story.design.HAPStoryManagerDesign;
import com.nosliw.core.application.division.story.service.HAPGatewayServiceConfigure;
import com.nosliw.core.gateway.HAPGatewayOutput;
import com.nosliw.core.runtime.HAPRuntimeInfo;

@Component
public class HAPStoryService{

	@Autowired
	private HAPStoryManagerDesign m_storyDesignMan;
	
	@Autowired
	private HAPServiceParseEntity m_parseService;

	@Autowired
	private HAPStoryManagerDesign m_designManager;

	@Autowired
	private HAPStoryConfigure m_storyConfigure;
	
	@Autowired
	private HAPGatewayServiceConfigure gatewayServiceConfigure;
	
	@Autowired
	private RestTemplate m_resteTemplate;

	public HAPManualContentProviderText convertDesignToManual(HAPIdBrick brickId, HAPRuntimeInfo runtimeInfo) {
		HAPStoryDesign design = m_designManager.getDesign(brickId);
		//convert
		HAPManualContentProviderText contentProvider = HAPStoryConverterToManual.convert(design.getStory());
		//save bundle content
		Path manualFolder = HAPUtilityLocation.getManualFolder(this.m_storyDesignMan.getStoryStorageRootPath(), brickId);
		HAPManualUtilityExporterContentProvider.export(contentProvider, manualFolder);
		return contentProvider;
	}

	public HAPManualContentProviderText convertDesignToManual(HAPStoryDesign design, HAPRuntimeInfo runtimeInfo) {
		//convert
		HAPManualContentProviderText contentProvider = HAPStoryConverterToManual.convert(design.getStory());
		//save bundle content
		Path manualFolder = HAPUtilityLocation.getManualFolder(this.m_storyDesignMan.getStoryStorageRootPath(), design.getBrickId());
		HAPManualUtilityExporterContentProvider.export(contentProvider, manualFolder);
		return contentProvider;
	}

	public HAPBundleForBrick getBundle(HAPIdBrick brickId, HAPRuntimeInfo runtimeInfo) {
		HAPBundleForBrick out = null;
		
		Path rootPath = this.m_storyDesignMan.getStoryStorageRootPath();
		Path bundleFolder = HAPUtilityLocation.getBundleFolder(rootPath, brickId);
		
		out = HAPUtilityExport.importBundle(bundleFolder, HAPSerializationFormat.JSON, m_parseService);
		
		if(out==null) {
			out = this.compileToBundle(brickId, runtimeInfo);
			HAPUtilityExport.exportBundle(out, bundleFolder, HAPSerializationFormat.JSON);
		}
		return out;
	}

	public HAPBundleForBrick compileToBundle(HAPIdBrick brickId, HAPRuntimeInfo runtimeInfo) {
		Path manualFolder = HAPUtilityLocation.getManualFolder(this.getStoryStorageRootPath(), brickId);
		HAPManualContentProviderText contentProviderText = HAPManualUtilityExporterContentProvider.importContentText(manualFolder, m_parseService);
		
		Map<String, Object> parmValue = new LinkedHashMap<String, Object>();
		parmValue.put(HAPManualGatewayCompile.PARMS_CONTENT, contentProviderText);
		
		HAPServiceData serviceData = HAPUtilityGateway.executeGatewaySingle(
				gatewayServiceConfigure.getUrlSingle(), 
				HAPConstantShared.GATEWAY_MANUAL_COMPILE, 
				HAPManualGatewayCompile.COMMAND_COMPILE,
				parmValue, 
				m_resteTemplate);
		
		HAPGatewayOutput gatewayOutput = (HAPGatewayOutput)serviceData.getData();
		return (HAPBundleForBrick)m_parseService.parseEntityJSONExplicit((JSONObject)gatewayOutput.getData(), HAPBundleForBrick.class.getName());
	}
	
	
	public Path getStoryStorageRootPath() {		return HAPUtilityFileNio.buildPath(this.m_storyConfigure.getPath());	}

	
//	public HAPBundleForBrick getBundle1(HAPIdBrick brickId, HAPRuntimeInfo runtimeInfo) {
//		
//		HAPBundleForBrick out = null;
//		
//		Path rootPath = this.m_storyDesignMan.getStoryStorageRootPath();
//		
//		Path bundleFolder = HAPUtilityLocation.getBundleFolder(rootPath, brickId);
//		out = HAPUtilityExport.importBundle(bundleFolder, HAPSerializationFormat.JSON, m_parseService);
//		
//		if(out==null) {
//			Path manualFolder = HAPUtilityLocation.getManualFolder(rootPath, brickId); 
//			
//			if(!HAPUtilityFileNio.isPathExists(manualFolder)) {
//				manualFolder = this.m_storyDesignMan.convertDesignToManual(brickId);
//			}
//			
//			HAPManualContentProviderFile contentProvider = new HAPManualContentProviderFile(brickId, HAPManualDefinitionUtilityBrickLocation.buildBrickLocationInfoFromMainFolder(brickId.getBrickTypeId(), manualFolder), m_brickCriteriaMan, this.m_parseService);
//			out = this.m_manulBrickManager.buildBundle(contentProvider, runtimeInfo);
//			HAPUtilityExport.exportBundle(out, bundleFolder, HAPSerializationFormat.JSON);
//		}
//		return out;
//	}
	
}
