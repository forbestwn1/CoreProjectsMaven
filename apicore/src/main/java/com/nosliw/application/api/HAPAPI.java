package com.nosliw.application.api;

import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.script.HAPJSScriptInfo;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityBasic;
import com.nosliw.common.utils.HAPUtilityFile;
import com.nosliw.common.utils.HAPUtilityNamingConversion;
import com.nosliw.core.application.entity.js.library.HAPGatewayBrowserLoadLibrary;
import com.nosliw.core.application.entity.js.library.HAPUtilityJSLibrary;
import com.nosliw.core.gateway.HAPGatewayManager;
import com.nosliw.core.gateway.HAPGatewayOutput;
import com.nosliw.core.resource.HAPUtilityResource;
import com.nosliw.core.runtime.HAPRuntimeInfo;
import com.nosliw.core.system.HAPSystemFolderUtility;
import com.nosliw.core.system.HAPSystemUtility;

@RestController
@RequestMapping("/nosliw")
public class HAPAPI {

	@HAPAttribute
	public static final String REQUEST_TYPE = "type";
	@HAPAttribute
	public static final String REQUEST_SERVICE = "service";
	@HAPAttribute
	public static final String REQUEST_MODE = "mode";
	@HAPAttribute
	public static final String REQUEST_CHILDREN = "children";
	
	@HAPAttribute
	final public static String COMMAND_PARM_RUNTIMEINFO = "runtimeInfo";

	@Autowired
	HAPGatewayManager m_gateManager;

	public static int index = 0;
	
	private final static Logger LOGGER = Logger.getLogger(HAPAPI.class.getName());

	@PostMapping("/gateway")
    public String gateway(@RequestBody String requestInfoStr) {
		HAPRequestInfo requestInfo = new HAPRequestInfo(URLDecoder.decode(requestInfoStr));
		
		HAPServiceData out = null;
		if(HAPConstantShared.SERVICECOMMAND_GROUPREQUEST.equals(requestInfo.getCommand())){
			JSONArray jsonGroupReqs = new JSONArray(requestInfo.getParms());

			List<String> requestsResult = new ArrayList<String>();
			for(int i=0; i<jsonGroupReqs.length(); i++){
				JSONObject req = jsonGroupReqs.getJSONObject(i);
				HAPServiceData serviceData = processRequest(req);
				String requestResult = serviceData.toStringValue(HAPSerializationFormat.JSON_FULL);
				requestsResult.add(requestResult);
			}
			out = HAPServiceData.createSuccessData(requestsResult);
		}
		return HAPUtilityJson.formatJson(out.toStringValue(HAPSerializationFormat.JSON_FULL));
	}
	
	@GetMapping("/loadlib")
    public HAPServiceData loadlib(@RequestBody HAPRequestInfo requestInfo) {
		HAPServiceData serviceData = null;
		try {
			JSONObject parmsJson = new JSONObject(requestInfo.getParms());
			serviceData = this.getGatewayManager().executeGateway(
					HAPConstantShared.GATEWAY_LOADLIBRARIES, 
					HAPGatewayBrowserLoadLibrary.COMMAND_LOADLIBRARY, 
					parmsJson, 
					new HAPRuntimeInfo(HAPConstantShared.RUNTIME_LANGUAGE_JS, HAPConstantShared.RUNTIME_ENVIRONMENT_BROWSER));

/*			
			if(HAPSystemUtility.getConsolidateLib()) {
				if(this.m_libraryTempFile==null) {
					this.m_libraryTempFile = System.currentTimeMillis()+"/library.js";
					HAPGatewayOutput gatewayOutput = (HAPGatewayOutput)serviceData.getData();
					List<String> fileNames = (List<String>)gatewayOutput.getData();
					StringBuffer libraryContent = new StringBuffer();
					for(String fileName : fileNames) {
						//remove version part in file url first
						String file1 = fileName; 
						int i = fileName.indexOf("?");
						if(i!=-1) {
							file1 = fileName.substring(0, i);
						}
						libraryContent.append(HAPUtilityFile.readFile(HAPSystemFolderUtility.getJSFolder()+file1));
					}
					HAPUtilityFile.writeFile(HAPSystemUtility.getJSTempFolder()+"libs/"+m_libraryTempFile, libraryContent.toString());
				}
				List<String> tempNames = new ArrayList<String>();
				String libUrl = "temp/libs/"+this.m_libraryTempFile;
				String libUrlWithVersion = HAPUtilityBasic.addVersionToUrl(libUrl, parmsJson.optString(HAPGatewayBrowserLoadLibrary.COMMAND_LOADLIBRARY_VERSION));
				tempNames.add(libUrlWithVersion);
				serviceData = HAPServiceData.createSuccessData(new HAPGatewayOutput(null, tempNames));
			}
*/			
		}
		catch(Exception e) {
			serviceData = HAPServiceData.createFailureData(e, "Exceptione during load library service request!!!!");
//			LOGGER.throwing(this.getClass().getName(), "service", e);
		}
	    return serviceData;
	}

	
	// process one request object
	private HAPServiceData processRequest(JSONObject req){
		HAPServiceData out = null;
		String reqType = req.getString(REQUEST_TYPE);
		if(HAPConstantShared.REMOTESERVICE_TASKTYPE_NORMAL.equals(reqType)){
			//for normal request
			JSONObject serviceJson = req.getJSONObject(REQUEST_SERVICE);
			HAPServiceInfo serviceInfo = new HAPServiceInfo(serviceJson);
			out = this.processRequest(serviceInfo); 
		}
		else if(HAPConstantShared.REMOTESERVICE_TASKTYPE_GROUP.equals(reqType)){
			//for group task, 
			boolean success = true;
			String mode = req.getString(REQUEST_MODE);
			List<HAPServiceData> serviceDatas = new ArrayList<HAPServiceData>();
			JSONArray jsonChildren = req.getJSONArray(REQUEST_CHILDREN);
			for(int j=0; j<jsonChildren.length(); j++){
				HAPServiceData serviceData = this.processRequest(jsonChildren.getJSONObject(j));
				serviceDatas.add(serviceData);
				if(serviceData.isFail()) {
					//if one child task fail, then stop processing 
					success = false;
					break;
				}
			}
			
			if(success==false){
				if(HAPConstantShared.REMOTESERVICE_GROUPTASK_MODE_ALWAYS.equals(mode)){
					//if group task mode is always, group task end with success 
					success = true;
				}
			}
			
			if(success) {
				out = HAPServiceData.createSuccessData(serviceDatas);
			} else {
				out = HAPServiceData.createFailureData(serviceDatas, "");
			}
		}
		return out;
	}
	
	private HAPServiceData processRequest(HAPServiceInfo serviceInfo){
		StringBuffer logContent = new StringBuffer();
		
		logContent.append("\n");
		logContent.append("*********************** Start Service ************************");
		logContent.append("\n");
		logContent.append(HAPServiceInfo.SERVICE_COMMAND + "  " + serviceInfo.getCommand());
		logContent.append("\n");
		logContent.append(HAPServiceInfo.SERVICE_PARMS + "   " + serviceInfo.getParms().toString());
		logContent.append("\n");
		
		HAPServiceData serviceData = null;
		try {
			serviceData = processServiceRequest(serviceInfo.getCommand(), serviceInfo.getParms());
		}
		catch(Exception e) {
			serviceData = HAPServiceData.createFailureData(e, "Exceptione during process gateway service request!!!!");
			LOGGER.severe(HAPUtilityBasic.toString(e));
		}
		
		String content = serviceData.toStringValue(HAPSerializationFormat.JSON_FULL);
		content = HAPUtilityJson.formatJson(content);
		
		logContent.append("return: \n" + content);
		logContent.append("\n");
		logContent.append("*********************** End Service ************************");
		logContent.append("\n");
		
		LOGGER.info(logContent.toString());
		return serviceData;
	}	
	
	protected HAPServiceData processServiceRequest(String gatewayCommand, JSONObject parms) throws Exception {
		HAPServiceData out = null;

		String[] segs = HAPUtilityNamingConversion.parseLevel1(gatewayCommand);
		String gatewayId = segs[0];
		String command = segs[1];
		
		out = this.getGatewayManager().executeGateway(gatewayId, command, parms, new HAPRuntimeInfo(HAPConstantShared.RUNTIME_LANGUAGE_JS, HAPConstantShared.RUNTIME_ENVIRONMENT_BROWSER));
		if(out.isSuccess()){
			HAPGatewayOutput output = (HAPGatewayOutput)out.getData();
			if(output!=null) {
				for(HAPJSScriptInfo scriptInfo : output.getScripts()){
					String file = scriptInfo.isFile();
					URI uri = scriptInfo.isURI();
					if(file!=null){
						scriptInfo.setFile(HAPUtilityJSLibrary.getBrowserScriptPath(file));
					}
					else if(uri!=null) {
						
					}
					else{
						if(HAPUtilityResource.LOADRESOURCEBYFILE_MODE_ALWAYS.equals(HAPSystemUtility.getLoadResourceByFileMode())){
							String name = "gatewayCommand_"+gatewayId+"_"+command+""+index++;
							String resourceFile = HAPSystemFolderUtility.getResourceTempFileFolder() + name + ".js";
							resourceFile = HAPUtilityFile.writeFile(resourceFile, scriptInfo.getScript());
							scriptInfo.setFile(HAPUtilityJSLibrary.getBrowserScriptPath(resourceFile));
							scriptInfo.setScript(null);
						}
						else {
							String escaptedScript = StringEscapeUtils.escapeEcmaScript(scriptInfo.getScript());
							scriptInfo.setScript(escaptedScript);
						}
					}
				}
			}
		}
		return out;
	}
	
	private HAPGatewayManager getGatewayManager() {   return this.m_gateManager;   }
	
	
}
