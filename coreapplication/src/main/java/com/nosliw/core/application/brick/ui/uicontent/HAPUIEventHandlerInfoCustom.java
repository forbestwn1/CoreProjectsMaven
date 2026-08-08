package com.nosliw.core.application.brick.ui.uicontent;

import java.util.Map;

import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.common.event.HAPEventHandlerReference;

public class HAPUIEventHandlerInfoCustom extends HAPUIEventHandlerInfo{

	public HAPUIEventHandlerInfoCustom() {
		
	}
	
	public HAPUIEventHandlerInfoCustom(String event, HAPEventHandlerReference handlerInfo) {
		super(event, handlerInfo);
	}

	@Override
	public void parseContent(String content, HAPServiceParseEntity parseService) {
		this.setHandlerInfo(HAPEventHandlerReference.parseHandlerInfo(content, parseService));
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
	}

}
