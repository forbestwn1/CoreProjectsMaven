package com.nosliw.core.application.entity.manual;

import org.json.JSONObject;

import com.nosliw.core.application.common.brick.HAPBundleForBrick;
import com.nosliw.core.application.common.manual.HAPManualContentProvider;
import com.nosliw.core.runtime.HAPRuntimeInfo;

public interface HAPServiceManual {

	HAPBundleForBrick deserializeBundle(JSONObject jsonObj);
	
	HAPBundleForBrick buildBundle(HAPManualContentProvider contentProvider, HAPRuntimeInfo runtimeInfo);
	
}
