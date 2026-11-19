package com.nosliw.core.application.division.story.api;

import java.nio.file.Path;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPUtilityFileNio;
import com.nosliw.core.application.common.brick.HAPBundleForBrick;
import com.nosliw.core.application.common.brick.HAPIdBrick;
import com.nosliw.core.application.common.brick.serialize.HAPUtilityExport;
import com.nosliw.core.application.common.manual.HAPManualContentProviderText;
import com.nosliw.core.application.common.manual.HAPManualUtilityExporterContentProvider;
import com.nosliw.core.application.division.story.converter.manual.HAPStoryConverterToManual;
import com.nosliw.core.application.division.story.design.HAPStoryDesign;
import com.nosliw.core.application.division.story.design.HAPStoryManagerDesign;
import com.nosliw.core.application.entity.manual.HAPServiceManual;
import com.nosliw.core.configure.global.HAPConfigureGlobalApplication;
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
	private HAPConfigureStoryPersistence m_storyConfigure;
	
	@Autowired
	private HAPConfigureGlobalApplication m_globalAppConfigure;
	
	@Autowired
	private RestTemplate m_resteTemplate;
	
	@Autowired
	private HAPServiceManual m_manualService;

	public HAPManualContentProviderText convertDesignToManual(HAPIdBrick brickId, HAPRuntimeInfo runtimeInfo) {
		HAPStoryDesign design = m_designManager.getDesign(brickId);
		//convert
		HAPManualContentProviderText contentProvider = HAPStoryConverterToManual.convert(design);
		//save bundle content
		Path manualFolder = HAPUtilityLocation.getManualFolder(this.m_storyDesignMan.getStoryStorageRootPath(), brickId);
		HAPManualUtilityExporterContentProvider.export(contentProvider, manualFolder);
		return contentProvider;
	}

	public HAPManualContentProviderText convertDesignToManual(HAPStoryDesign design, HAPRuntimeInfo runtimeInfo) {
		//convert
		HAPManualContentProviderText contentProvider = HAPStoryConverterToManual.convert(design);
		//save bundle content
		Path manualFolder = HAPUtilityLocation.getManualFolder(this.m_storyDesignMan.getStoryStorageRootPath(), design.getBrickId());
		HAPManualUtilityExporterContentProvider.export(contentProvider, manualFolder);
		return contentProvider;
	}

	public JSONObject getBundle(HAPIdBrick brickId, HAPRuntimeInfo runtimeInfo) {
		JSONObject out = null;
		
		Path rootPath = this.m_storyDesignMan.getStoryStorageRootPath();
		Path bundleFolder = HAPUtilityLocation.getBundleFolder(rootPath, brickId);
		
		out = HAPUtilityExport.importBundleToJsonObj(bundleFolder);
		
		if(out==null) {
			out = this.compileToBundle(brickId, runtimeInfo);
		}
		return out;
	}

	public JSONObject compileToBundle(HAPIdBrick brickId, HAPRuntimeInfo runtimeInfo) {
		Path manualFolder = HAPUtilityLocation.getManualFolder(this.getStoryStorageRootPath(), brickId);
		HAPManualContentProviderText contentProviderText = HAPManualUtilityExporterContentProvider.importContentText(manualFolder, m_parseService);
		
		HAPBundleForBrick bundleForBrick = m_manualService.buildBundle(contentProviderText, runtimeInfo);
		JSONObject out = new JSONObject(bundleForBrick.toStringValue(HAPSerializationFormat.JSON));

		Path rootPath = this.m_storyDesignMan.getStoryStorageRootPath();
		Path bundleFolder = HAPUtilityLocation.getBundleFolder(rootPath, brickId);
		HAPUtilityExport.exportBundle(out, bundleFolder);
		
		return out;
	}
	
	
	public Path getStoryStorageRootPath() {		return HAPUtilityFileNio.buildPath(this.m_storyConfigure.getPath());	}

}
