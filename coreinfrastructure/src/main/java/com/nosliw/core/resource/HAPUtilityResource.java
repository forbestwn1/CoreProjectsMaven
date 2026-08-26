package com.nosliw.core.resource;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.collect.Lists;
import com.nosliw.core.runtime.HAPRuntimeInfo;

public class HAPUtilityResource {

	public static HAPResource getResource(HAPResourceId resourceId, HAPManagerResource resourceMan, HAPRuntimeInfo runtimeInfo) {
		return resourceMan.getResources(Lists.asList(resourceId, new HAPResourceId[0]), runtimeInfo).getLoadedResource(resourceId);
	}

	public static Map<String, Object> buildResourceLoadPattern(HAPResourceId resourceId, Map<String, Object> info, HAPConfigureResource resourceConfigure) {
		if(info==null) {
			info = new LinkedHashMap<String, Object>();
		}
		if(isLoadResoureByFile(resourceId.getResourceTypeId().getResourceType(), resourceConfigure)) {
			info.put(HAPConfigureResource.RESOURCE_LOADPATTERN, HAPConfigureResource.RESOURCE_LOADPATTERN_FILE);
		}
		return info;
	}

	public static boolean isLoadResoureByFile(String resourceType, HAPConfigureResource resourceConfigure) {
		String mode = resourceConfigure.getFileLoadMode();
		if(mode==null) {
			mode = HAPConfigureResource.LOADRESOURCEBYFILE_MODE_DEPENDS;
		}
		if(HAPConfigureResource.LOADRESOURCEBYFILE_MODE_NEVER.equals(mode)) {
			return false;
		}
		if(HAPConfigureResource.LOADRESOURCEBYFILE_MODE_ALWAYS.equals(mode)) {
			return true;
		}
		return resourceConfigure.getFileLoadResources().contains(resourceType);
	}
	
}
