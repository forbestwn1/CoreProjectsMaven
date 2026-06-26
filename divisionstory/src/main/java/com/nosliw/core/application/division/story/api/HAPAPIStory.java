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
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.HAPBundleForBrick;
import com.nosliw.core.application.HAPBundleForExecute;
import com.nosliw.core.application.HAPIdBrick;
import com.nosliw.core.application.HAPIdBrickType;
import com.nosliw.core.application.HAPUtilityBundleForExecute;
import com.nosliw.core.application.common.datadefinition.HAPDataDefinition;
import com.nosliw.core.application.common.datadefinition.HAPParserDataDefinition;
import com.nosliw.core.application.division.manual.core.standalone.HAPManualManangerStandalone;
import com.nosliw.core.application.division.story.HAPStoryManagerStory;
import com.nosliw.core.application.division.story.design.HAPStoryBuilderRequest;
import com.nosliw.core.application.division.story.design.HAPStoryBuilderResponseBuild;
import com.nosliw.core.application.division.story.design.HAPStoryBuilderResponseNew;
import com.nosliw.core.application.division.story.design.HAPStoryDesign;
import com.nosliw.core.application.division.story.design.HAPStoryManagerDesign;
import com.nosliw.core.application.division.story.design.uitag.HAPStoryUtilityUITag;
import com.nosliw.core.application.entity.uitag.HAPManagerUITag;
import com.nosliw.core.runtime.HAPRuntimeManager;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@RestController
@RequestMapping("/nosliw/design")
@HAPEntityWithAttribute
public class HAPAPIStory {

	@Autowired
	private HAPStoryManagerStory m_storyManager;
	
	@Autowired
	private HAPStoryManagerDesign m_designManager;
	
	@Autowired
	private HAPServiceParseEntity m_entityParseService;
	
	@Autowired
	private HAPManagerUITag m_uiTagMan;
	
	@Autowired
	private HAPManualManangerStandalone m_standaloneMan;
	
	@PostMapping("/new")
    public String newDesign(@RequestParam String builderId, @RequestParam String brickType, @RequestParam String brickVersion) {
		HAPStoryBuilderResponseNew newResponse = m_designManager.newStoryDesign(new HAPIdBrickType(brickType, brickVersion), builderId, null);
		HAPServiceData out = HAPServiceData.createSuccessData(newResponse);
	    return HAPUtilityJson.formatJson(out.toStringValue(HAPSerializationFormat.JSON_FULL));
	}	

	@PostMapping("/build")
    public String buildDesignNext(@RequestBody String requestBody) {
		HAPStoryBuilderRequest request = (HAPStoryBuilderRequest)this.m_entityParseService.parseEntityJSONExplicit(new JSONObject(requestBody), HAPStoryBuilderRequest.PARSABLEENTITYTYPE);
		HAPStoryBuilderResponseBuild buildResponse = m_designManager.designStory(request);
		HAPServiceData out = HAPServiceData.createSuccessData(buildResponse);
	    return HAPUtilityJson.formatJson(out.toStringValue(HAPSerializationFormat.JSON_FULL));
	}	

	@GetMapping("/{brickType}/{brickVersion}/{id}")
    public String getDesign(@PathVariable String brickType, @PathVariable String brickVersion, @PathVariable String id) {
		HAPStoryDesign design = m_designManager.getDesign(buildBrickId(brickType, brickVersion, id));
		HAPServiceData out = HAPServiceData.createSuccessData(design);
	    return HAPUtilityJson.formatJson(out.toStringValue(HAPSerializationFormat.JSON_FULL));
	}	

	@PostMapping("/convert/{brickType}/{brickVersion}/{id}")
    public String convertDesign(@PathVariable String brickType, @PathVariable String brickVersion, @PathVariable String id) {
		m_designManager.convertDesignToManual(buildBrickId(brickType, brickVersion, id));
		HAPServiceData out = HAPServiceData.createSuccessData();
	    return HAPUtilityJson.formatJson(out.toStringValue(HAPSerializationFormat.JSON_FULL));
	}	

	@PostMapping("/bundle/{brickType}/{brickVersion}/{id}")
    public String buildBundle(@PathVariable String brickType, @PathVariable String brickVersion, @PathVariable String id) {
		HAPBundleForBrick bundle = this.m_storyManager.getBundle(buildBrickId(brickType, brickVersion, id), HAPRuntimeManager.RUNTIME_JS_BROWSER);
		HAPServiceData out = HAPServiceData.createSuccessData(bundle);
	    return HAPUtilityJson.formatJson(out.toStringValue(HAPSerializationFormat.JSON_FULL));
	}	

	@PostMapping("/uitag")
    public String uiTagForConstant(@RequestBody String requestBody) {
		HAPDataDefinition dataDefinition = HAPParserDataDefinition.parseDataDefinition(new JSONObject(requestBody), m_entityParseService);
		HAPBundleForBrick bundleForBrick = HAPStoryUtilityUITag.buildStandaloneBundleForUITag(dataDefinition, this.m_uiTagMan, this.m_standaloneMan, HAPRuntimeManager.RUNTIME_JS_BROWSER);
		
		HAPBundleForExecute bundleForExe = HAPUtilityBundleForExecute.toBundleExecutable(bundleForBrick, null);
		
		HAPServiceData out = HAPServiceData.createSuccessData(bundleForExe);
	    return HAPUtilityJson.formatJson(out.toStringValue(HAPSerializationFormat.JSON_FULL));
	}
	
	private HAPIdBrick buildBrickId(String brickType, String brickVersion, String brickId) {
		return new HAPIdBrick(new HAPIdBrickType(brickType, brickVersion), HAPConstantShared.BRICK_DIVISION_STORY, brickId);
	}

}
