package com.nosliw.core.application.division.story.api;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.core.application.division.story.design.HAPStoryBuilderRequest;
import com.nosliw.core.application.division.story.design.HAPStoryBuilderResponseBuild;
import com.nosliw.core.application.division.story.design.HAPStoryBuilderResponseNew;
import com.nosliw.core.application.division.story.design.HAPStoryDesign;
import com.nosliw.core.application.division.story.design.HAPStoryManagerDesign;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@RestController
@RequestMapping("/nosliw/story")
@HAPEntityWithAttribute
public class HAPAPIStory {

	@Autowired
	private HAPStoryManagerDesign m_designManager;
	
	@Autowired
	private HAPServiceParseEntity m_entityParseService;
	
	@PostMapping("/new")
    public String newStory(@RequestParam String builderId) {
		HAPStoryBuilderResponseNew newResponse = m_designManager.newStoryDesign(builderId, null);
		HAPServiceData out = HAPServiceData.createSuccessData(newResponse);
	    return HAPUtilityJson.formatJson(out.toStringValue(HAPSerializationFormat.JSON_FULL));
	}	

	@PostMapping("/build")
    public String buildStory(@RequestBody String requestBody) {
		HAPStoryBuilderRequest request = (HAPStoryBuilderRequest)this.m_entityParseService.parseEntityJSONExplicit(new JSONObject(requestBody), HAPStoryBuilderRequest.PARSABLEENTITYTYPE);
		HAPStoryBuilderResponseBuild buildResponse = m_designManager.designStory(request);
		HAPServiceData out = HAPServiceData.createSuccessData(buildResponse);
	    return HAPUtilityJson.formatJson(out.toStringValue(HAPSerializationFormat.JSON_FULL));
	}	

	@GetMapping("/design/{id}")
    public String getStory(@PathVariable String id) {
		HAPStoryDesign design = m_designManager.getDesign(id);
		HAPServiceData out = HAPServiceData.createSuccessData(design);
	    return HAPUtilityJson.formatJson(out.toStringValue(HAPSerializationFormat.JSON_FULL));
	}	
}
