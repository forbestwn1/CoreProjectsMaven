package com.nosliw.core.application.division.story.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
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
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.brick.HAPIdBrick;
import com.nosliw.core.application.brick.HAPIdBrickType;
import com.nosliw.core.application.common.manual.gateway.standalone.HAPManualStandaloneProviderRequest;
import com.nosliw.core.application.common.manual.gateway.standalone.HAPManualStandaloneResponse;
import com.nosliw.core.application.division.story.design.HAPStoryBuilderRequest;
import com.nosliw.core.application.division.story.design.HAPStoryBuilderResponseBuild;
import com.nosliw.core.application.division.story.design.HAPStoryBuilderResponseNew;
import com.nosliw.core.application.division.story.design.HAPStoryDesign;
import com.nosliw.core.application.division.story.design.HAPStoryManagerDesign;
import com.nosliw.core.application.division.story.design.standalone.HAPStoryManagerStandalone;
import com.nosliw.core.runtime.HAPRuntimeManager;

@RestController
@RequestMapping("/nosliw/story")
@HAPEntityWithAttribute
public class HAPAPIStory {

	@Autowired
	private HAPStoryService m_storyService;
	
	@Autowired
	private HAPStoryManagerDesign m_designManager;
	
	@Autowired
	private HAPServiceParseEntity m_entityParseService;
	
	@Autowired
	private HAPStoryManagerStandalone m_standaloneMan;
	
	@PostMapping("/design/new")
    public String newDesign(@RequestParam String builderId, @RequestParam String brickType, @RequestParam String brickVersion) {
		HAPStoryBuilderResponseNew newResponse = m_designManager.newStoryDesign(new HAPIdBrickType(brickType, brickVersion), builderId, null);
		HAPServiceData out = HAPServiceData.createSuccessData(newResponse);
		
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	    return HAPUtilityJson.formatJson(out.toStringValue(HAPSerializationFormat.JSON_FULL));
	}	

	@PostMapping("/design/build")
    public String buildDesignNext(@RequestBody String requestBody) {
		HAPStoryBuilderRequest request = (HAPStoryBuilderRequest)this.m_entityParseService.parseEntityJSONExplicit(new JSONObject(requestBody), HAPStoryBuilderRequest.PARSABLEENTITYTYPE);
		HAPStoryBuilderResponseBuild buildResponse = m_designManager.designStory(request);
		HAPServiceData out = HAPServiceData.createSuccessData(buildResponse);

//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		return HAPUtilityJson.formatJson(out.toStringValue(HAPSerializationFormat.JSON_FULL));
	}	

	@GetMapping("/design/{brickType}/{brickVersion}/{id}")
    public String getDesign(@PathVariable String brickType, @PathVariable String brickVersion, @PathVariable String id) {
		HAPStoryDesign design = m_designManager.getDesign(buildBrickId(brickType, brickVersion, id));
		HAPServiceData out = HAPServiceData.createSuccessData(design);
	    return HAPUtilityJson.formatJson(out.toStringValue(HAPSerializationFormat.JSON_FULL));
	}	

	@PostMapping("/bundle/convert/{brickType}/{brickVersion}/{id}")
    public String convertDesignToManual(@PathVariable String brickType, @PathVariable String brickVersion, @PathVariable String id) {
		this.m_storyService.convertDesignToManual(buildBrickId(brickType, brickVersion, id), HAPRuntimeManager.RUNTIME_JS_BROWSER);
		HAPServiceData out = HAPServiceData.createSuccessData();
	    return HAPUtilityJson.formatJson(out.toStringValue(HAPSerializationFormat.JSON_FULL));
	}	

	@PostMapping("/bundle/{brickType}/{brickVersion}/{id}")
    public String compileToBundle(@PathVariable String brickType, @PathVariable String brickVersion, @PathVariable String id) {
		JSONObject bundle =  this.m_storyService.compileToBundle(buildBrickId(brickType, brickVersion, id), HAPRuntimeManager.RUNTIME_JS_BROWSER);
		HAPServiceData out = HAPServiceData.createSuccessData(bundle);
	    return HAPUtilityJson.formatJson(out.toStringValue(HAPSerializationFormat.JSON_FULL));
	}	

	@GetMapping("/bundle/{brickType}/{brickVersion}/{id}")
    public String getBundle(@PathVariable String brickType, @PathVariable String brickVersion, @PathVariable String id) {
		JSONObject bundle = this.m_storyService.getBundle(buildBrickId(brickType, brickVersion, id), HAPRuntimeManager.RUNTIME_JS_BROWSER);
		HAPServiceData out = HAPServiceData.createSuccessData(bundle);
	    return HAPUtilityJson.formatJson(out.toStringValue(HAPSerializationFormat.JSON_FULL));
	}	

	@PostMapping("/standalone")
    public String buildStandAlone(@RequestBody String requestBody) {
		List<HAPManualStandaloneResponse> out = new ArrayList<HAPManualStandaloneResponse>();
		JSONArray requestJsonArray = new JSONArray(requestBody);
		for(int i=0; i<requestJsonArray.length(); i++) {
			HAPManualStandaloneProviderRequest request = new HAPManualStandaloneProviderRequest();
			request.buildObject(requestJsonArray.getJSONObject(i), HAPSerializationFormat.JSON);
			
			out.add(this.m_standaloneMan.buildStandalone((JSONObject)request.getParms()));
		}
		HAPServiceData serviceData = HAPServiceData.createSuccessData(out);
	    return HAPUtilityJson.formatJson(serviceData.toStringValue(HAPSerializationFormat.JSON_FULL));
	}	
	
	private HAPIdBrick buildBrickId(String brickType, String brickVersion, String brickId) {
		return new HAPIdBrick(new HAPIdBrickType(brickType, brickVersion), HAPConstantShared.BRICK_DIVISION_STORY, brickId);
	}

}
