package com.nosliw.core.application.division.story.api;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

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
import org.springframework.web.client.RestTemplate;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.brick.HAPIdBrick;
import com.nosliw.core.application.brick.HAPIdBrickType;
import com.nosliw.core.application.common.datasource.HAPQueryServiceDefinition;
import com.nosliw.core.application.common.manual.gateway.standalone.HAPManualStandaloneProviderRequest;
import com.nosliw.core.application.common.manual.gateway.standalone.HAPManualStandaloneResponse;
import com.nosliw.core.application.division.story.design.HAPStoryBuilderRequest;
import com.nosliw.core.application.division.story.design.HAPStoryBuilderResponseBuild;
import com.nosliw.core.application.division.story.design.HAPStoryBuilderResponseNew;
import com.nosliw.core.application.division.story.design.HAPStoryDesign;
import com.nosliw.core.application.division.story.design.HAPStoryManagerDesign;
import com.nosliw.core.application.division.story.design.standalone.HAPStoryManagerStandalone;
import com.nosliw.core.configure.global.HAPConfigureGlobalApplication;
import com.nosliw.core.gateway.HAPUtilityGateway;
import com.nosliw.core.gateway.types.HAPGatewayService;
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
	
	@Autowired
	private HAPConfigureGlobalApplication m_globalAppConfigure;
	
	@Autowired
	private HAPConfigureStoryDataSource m_dataSourceConfigure;
	
	@Autowired
	private RestTemplate m_resteTemplate;
	
	private final static Logger LOGGER = Logger.getLogger(HAPAPIStory.class.getName());
	
	private void logEndpoint(String endpoint, String details) {
		LOGGER.info(endpoint + " :: " + details);
	}
	
	private void logEndpointResult(String endpoint, String details, Object result) {
		LOGGER.info(endpoint + " :: " + details + " -> " + String.valueOf(result));
	}

	@PostMapping("/design/new")
    public String newDesign(@RequestParam String builderId, @RequestParam String brickType, @RequestParam String brickVersion) {
		logEndpoint("/design/new", "builderId=" + builderId + ", brickType=" + brickType + ", brickVersion=" + brickVersion);
		HAPStoryBuilderResponseNew newResponse = m_designManager.newStoryDesign(new HAPIdBrickType(brickType, brickVersion), builderId, null);
		HAPServiceData out = HAPServiceData.createSuccessData(newResponse);
		logEndpointResult("/design/new", "Success", out);
	    return HAPUtilityJson.formatJson(out.toStringValue(HAPSerializationFormat.JSON_FULL));
	}	

	@PostMapping("/design/build")
    public String buildDesignNext(@RequestBody String requestBody) {
		logEndpoint("/design/build", "requestBody=" + requestBody);
		HAPStoryBuilderRequest request = (HAPStoryBuilderRequest)this.m_entityParseService.parseEntityJSONExplicit(new JSONObject(requestBody), HAPStoryBuilderRequest.ENTITYNAMEFORSERIALIZE);
		HAPStoryBuilderResponseBuild buildResponse = m_designManager.designStory(request);
		HAPServiceData out = HAPServiceData.createSuccessData(buildResponse);
		logEndpointResult("/design/build", "Success", out);
		return HAPUtilityJson.formatJson(out.toStringValue(HAPSerializationFormat.JSON_FULL));
	}	

	@PostMapping("/design/datasource")
    public String queryDataSource() {
		logEndpoint("/design/datasource", "querying data source");
		Map<String, Object> parmsValue = new LinkedHashMap<String, Object>();
		HAPQueryServiceDefinition query = new HAPQueryServiceDefinition();
		
		List<String> tags = m_dataSourceConfigure.getTag();
		if(tags!=null) {
			for(String tag : tags) {
				query.addKeywords(tag);
			}
		}
		
		parmsValue.put(HAPGatewayService.COMMAND_SEARCHDEFINITION_QUERY, query);
		HAPServiceData out = HAPUtilityGateway.executeGatewaySingle(m_globalAppConfigure.getCoreBaseurl(), HAPConstantShared.GATEWAY_SERVICE, HAPGatewayService.COMMAND_SEARCHDEFINITION, parmsValue, this.m_resteTemplate);
		logEndpointResult("/design/datasource", "Success", out);
	    return HAPUtilityJson.formatJson(out.toStringValue(HAPSerializationFormat.JSON_FULL));
	}	

	@GetMapping("/design/{brickType}/{brickVersion}/{id}")
    public String getDesign(@PathVariable String brickType, @PathVariable String brickVersion, @PathVariable String id) {
		logEndpoint("/design/{brickType}/{brickVersion}/{id}", "brickType=" + brickType + ", brickVersion=" + brickVersion + ", id=" + id);
		HAPStoryDesign design = m_designManager.getDesign(buildBrickId(brickType, brickVersion, id));
		HAPServiceData out = HAPServiceData.createSuccessData(design);
		logEndpointResult("/design/{brickType}/{brickVersion}/{id}", "Success", out);
	    return HAPUtilityJson.formatJson(out.toStringValue(HAPSerializationFormat.JSON_FULL));
	}	

	@PostMapping("/bundle/convert/{brickType}/{brickVersion}/{id}")
    public String convertDesignToManual(@PathVariable String brickType, @PathVariable String brickVersion, @PathVariable String id) {
		logEndpoint("/bundle/convert/{brickType}/{brickVersion}/{id}", "brickType=" + brickType + ", brickVersion=" + brickVersion + ", id=" + id);
		this.m_storyService.convertDesignToManual(buildBrickId(brickType, brickVersion, id), HAPRuntimeManager.RUNTIME_JS_BROWSER);
		HAPServiceData out = HAPServiceData.createSuccessData();
		logEndpointResult("/bundle/convert/{brickType}/{brickVersion}/{id}", "Success", out);
	    return HAPUtilityJson.formatJson(out.toStringValue(HAPSerializationFormat.JSON_FULL));
	}	

	@PostMapping("/bundle/{brickType}/{brickVersion}/{id}")
    public String compileToBundle(@PathVariable String brickType, @PathVariable String brickVersion, @PathVariable String id) {
		logEndpoint("/bundle/{brickType}/{brickVersion}/{id}", "brickType=" + brickType + ", brickVersion=" + brickVersion + ", id=" + id);
		JSONObject bundle =  this.m_storyService.compileToBundle(buildBrickId(brickType, brickVersion, id), HAPRuntimeManager.RUNTIME_JS_BROWSER);
		HAPServiceData out = HAPServiceData.createSuccessData(bundle);
		logEndpointResult("/bundle/{brickType}/{brickVersion}/{id}", "Success", out);
	    return HAPUtilityJson.formatJson(out.toStringValue(HAPSerializationFormat.JSON_FULL));
	}	

	@GetMapping("/bundle/{brickType}/{brickVersion}/{id}")
    public String getBundle(@PathVariable String brickType, @PathVariable String brickVersion, @PathVariable String id) {
		logEndpoint("/bundle/{brickType}/{brickVersion}/{id}", "brickType=" + brickType + ", brickVersion=" + brickVersion + ", id=" + id);
		JSONObject bundle = this.m_storyService.getBundle(buildBrickId(brickType, brickVersion, id), HAPRuntimeManager.RUNTIME_JS_BROWSER);
		HAPServiceData out = HAPServiceData.createSuccessData(bundle);
		logEndpointResult("/bundle/{brickType}/{brickVersion}/{id}", "Success", out);
	    return HAPUtilityJson.formatJson(out.toStringValue(HAPSerializationFormat.JSON_FULL));
	}	

	@PostMapping("/standalone")
    public String buildStandAlone(@RequestBody String requestBody) {
		logEndpoint("/standalone", "requestBody=" + requestBody);
		List<HAPManualStandaloneResponse> out = new ArrayList<HAPManualStandaloneResponse>();
		JSONArray requestJsonArray = new JSONArray(requestBody);
		for(int i=0; i<requestJsonArray.length(); i++) {
			HAPManualStandaloneProviderRequest request = new HAPManualStandaloneProviderRequest();
			request.buildObject(requestJsonArray.getJSONObject(i), HAPSerializationFormat.JSON);
			
			out.add(this.m_standaloneMan.buildStandalone((JSONObject)request.getParms()));
		}
		HAPServiceData serviceData = HAPServiceData.createSuccessData(out);
		logEndpointResult("/standalone", "Success", serviceData);
	    return HAPUtilityJson.formatJson(serviceData.toStringValue(HAPSerializationFormat.JSON_FULL));
	}	
	
	private HAPIdBrick buildBrickId(String brickType, String brickVersion, String brickId) {
		return new HAPIdBrick(new HAPIdBrickType(brickType, brickVersion), HAPConstantShared.BRICK_DIVISION_STORY, brickId);
	}
}