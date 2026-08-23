package com.nosliw.core.application.entity.jslibrary;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.jslibrary.HAPJSLibraryId;
import com.nosliw.core.application.common.jslibrary.HAPResourceDataJSLibrary;
import com.nosliw.core.resource.HAPPluginResourceManager;
import com.nosliw.core.resource.HAPResourceDataOrWrapper;
import com.nosliw.core.resource.HAPResourceHelper;
import com.nosliw.core.resource.HAPResourceIdSimple;
import com.nosliw.core.runtime.HAPRuntimeInfo;
import com.nosliw.core.service.staticresource.HAPServiceStaticResource;
import com.nosliw.core.service.staticresource.HAPStaticRequest;
import com.nosliw.core.service.staticresource.HAPStaticRequestInfoLibrary;
import com.nosliw.core.service.staticresource.HAPStaticResponse;
import com.nosliw.core.service.staticresource.HAPStaticResponseInfo;
import com.nosliw.core.service.staticresource.HAPStaticResponseInfoFile;

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
		String domain = HAPConstantShared.STATIC_LIBRARY_DOMAIN_INTERNAL; 
		if(name.startsWith("nosliw.")) {
			name = name.substring("nosliw.".length());
			
		}
		else if(name.startsWith("external.")) {
			name = name.substring("external.".length());
			domain = HAPConstantShared.STATIC_LIBRARY_DOMAIN_EXTERNAL;
		}
		
		staticRequest.addStaticInfo(new HAPStaticRequestInfoLibrary(domain, name, libraryId.getVersion()));
		
		HAPServiceData serviceData = m_staticResourceService.getStatic(staticRequest);
		HAPStaticResponse staticResponse = (HAPStaticResponse)serviceData.getData();
		
		List<URI> uris = new ArrayList<URI>();
		for(HAPStaticResponseInfo responseInfo : staticResponse.getItems()) {
			uris.add(((HAPStaticResponseInfoFile)responseInfo).getURI());
		}
		
		return new HAPResourceDataJSLibrary(uris);
	}

}
