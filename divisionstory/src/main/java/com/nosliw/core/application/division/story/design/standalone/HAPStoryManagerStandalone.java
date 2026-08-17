package com.nosliw.core.application.division.story.design.standalone;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.common.manual.HAPManualContentProviderText;
import com.nosliw.core.application.common.manual.gateway.standalone.HAPManualStandaloneResponse;
import com.nosliw.core.application.common.uitag.HAPUITageQueryData;
import com.nosliw.core.application.division.story.service.uitag.HAPUITagService;
import com.nosliw.core.runtime.HAPRuntimeManager;

@HAPEntityWithAttribute
@Component
public class HAPStoryManagerStandalone {

	@HAPAttribute
	public static final String COMMAND_CEATESTANDALONE_PARM_UITAGQUERY = "uiTagQuery";

	@HAPAttribute
	public static final String COMMAND_CEATESTANDALONE_PARM_ID = "resourceId";

	@HAPAttribute
	public static final String COMMAND_CEATESTANDALONE_PARM_DOMAIN = "domain";

	@Autowired
	private HAPServiceParseEntity m_entityParseService;
	
	@Autowired
	private HAPUITagService m_uiTagService;

	public HAPManualStandaloneResponse buildStandalone(JSONObject requestJson) {
		JSONObject uiTagQueryJsonObj = requestJson.getJSONObject(COMMAND_CEATESTANDALONE_PARM_UITAGQUERY);
		HAPUITageQueryData uiTagQuery = HAPUITageQueryData.parseUITagQueryData(uiTagQueryJsonObj, m_entityParseService);
		HAPManualContentProviderText contentProvider = HAPStoryUtilityUITag.buildStandaloneBundleForUITag(uiTagQuery, this.m_uiTagService, HAPRuntimeManager.RUNTIME_JS_BROWSER);
		return new HAPManualStandaloneResponse(contentProvider);
	}
	
}
