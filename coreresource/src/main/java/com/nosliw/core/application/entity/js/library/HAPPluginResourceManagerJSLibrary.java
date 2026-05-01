package com.nosliw.core.application.entity.js.library;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.staticc.HAPStaticRequest;
import com.nosliw.common.staticc.HAPStaticRequestInfo;
import com.nosliw.common.staticc.HAPStaticResponse;
import com.nosliw.common.staticc.HAPStaticResponseInfo;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityBasic;
import com.nosliw.common.utils.HAPUtilityFile;
import com.nosliw.core.resource.HAPPluginResourceManager;
import com.nosliw.core.resource.HAPResourceDataOrWrapper;
import com.nosliw.core.resource.HAPResourceHelper;
import com.nosliw.core.resource.HAPResourceIdSimple;
import com.nosliw.core.runtime.HAPRuntimeInfo;
import com.nosliw.core.service.HAPServiceStaticResource;
import com.nosliw.core.system.HAPSystemFolderUtility;

public class HAPPluginResourceManagerJSLibrary implements HAPPluginResourceManager{

    private HAPServiceStaticResource m_staticResourceService;

	public HAPPluginResourceManagerJSLibrary(HAPServiceStaticResource staticResourceService) {
		this.m_staticResourceService = staticResourceService;
		HAPResourceHelper.getInstance().registerResourceId(HAPConstantShared.RUNTIME_RESOURCE_TYPE_JSLIBRARY, HAPResourceIdJSLibrary.class, HAPJSLibraryId.class);
	}
	
	@Override
	public HAPResourceDataOrWrapper getResourceData(HAPResourceIdSimple simpleResourceId, HAPRuntimeInfo runtimeInfo) {
		HAPResourceIdJSLibrary resourceLibraryId = new HAPResourceIdJSLibrary(simpleResourceId);
		HAPJSLibraryId libraryId =  resourceLibraryId.getLibraryId();
		
		HAPStaticRequest staticRequest = new HAPStaticRequest();
		
		String name = libraryId.getName();
		String domain = "data.javascript.library.internal";
		if(name.startsWith("nosliw.")) {
			name = name.substring("nosliw.".length());
			
		}
		else if(name.startsWith("external.")) {
			name = name.substring("external.".length());
			domain = "data.javascript.library.external";
		}
		
		staticRequest.addStaticInfo(new HAPStaticRequestInfo(HAPStaticRequestInfo.STATIC_TYPE_LIBRARY, domain, name, libraryId.getVersion()));
		
		HAPServiceData serviceData = m_staticResourceService.getStatic(staticRequest);
		HAPStaticResponse staticResponse = (HAPStaticResponse)serviceData.getData();
		
//		RestTemplate restTemplate = new RestTemplate();
//		String responsStr = restTemplate.postForObject("http://localhost:8081/nosliw/static", staticRequest.toStringValue(HAPSerializationFormat.JSON), String.class);
//		HAPServiceData serviceData = new HAPServiceData();
//		serviceData.buildObject(new JSONObject(responsStr), HAPSerializationFormat.JSON);
//		
//		HAPStaticResponse staticResponse = new HAPStaticResponse();
//		staticResponse.buildObject(serviceData.getData(), HAPSerializationFormat.JSON);
		
		List<URI> uris = new ArrayList<URI>();
		for(HAPStaticResponseInfo responseInfo : staticResponse.getItems()) {
			uris.add(responseInfo.getURI());
		}
		
		return new HAPResourceDataJSLibrary(uris);
		
		
		
//		List<File> files = this.getLibraryFileName(libraryId);
//		if(files==null || files.size()==0) {
//			return null;
//		}
//		
//		List<URI> uris = new ArrayList<URI>();
//		for(File file : files){
//			uris.add(file.toURI());
//		}
//		return new HAPResourceDataJSLibrary(uris);
	}

	
	private List<File> getLibraryFileName(HAPJSLibraryId libraryId){
		String path = libraryId.getName().replace(".", "/");
		String folder = HAPSystemFolderUtility.getJSLibraryFolder() + path + (HAPUtilityBasic.isStringEmpty(libraryId.getVersion()) ? "" : "/" + libraryId.getVersion());
		Set<File> files = HAPUtilityFile.getAllFiles(folder);
		List<File> out = new ArrayList<File>(files);
		//make file sorted by name
		Collections.sort(out);
		return out;
	}

}
