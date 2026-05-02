package com.nosliw.core.runtime;

import java.util.List;
import java.util.Map;

import com.nosliw.core.resource.HAPResource;
import com.nosliw.core.resource.HAPResourceId;
import com.nosliw.core.resource.HAPResourceInfo;

public interface HAPRuntimeAdapterLoadResource {

	Object buildLoadResourceData(Map<HAPResourceId, HAPResourceInfo> resourcesInfo, List<HAPResource> resources);
	
}
