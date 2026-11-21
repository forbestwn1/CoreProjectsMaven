package com.nosliw.core.application.entity.brick;

import org.json.JSONObject;

import com.nosliw.core.application.common.brick.HAPBundleForBrick;

public interface HAPDeserilizerBundle {

	HAPBundleForBrick deserializeBundle(JSONObject jsonObj);
	
}
