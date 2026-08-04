package com.nosliw.core.application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.path.HAPPath;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.command.HAPCommandProcess;
import com.nosliw.core.application.common.command.HAPCommandWithExport;
import com.nosliw.core.application.common.event.HAPEventEmitter;
import com.nosliw.core.application.common.event.HAPEventProcess;
import com.nosliw.core.application.dynamic.HAPDynamicDefinitionContainer;
import com.nosliw.core.resource.HAPResourceDependency;
import com.nosliw.core.resource.HAPResourceIdSimple;
import com.nosliw.core.resource.HAPWithResourceDependency;
import com.nosliw.core.runtime.HAPRuntimeInfo;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPParserEntity;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@Component
class HAPBundleForBrick_parser implements HAPParserEntity{

	@Override
	public String getEntityType() {    return HAPBundleForBrick.class.getName();   }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPBundleForBrick out = new HAPBundleForBrick(); 

		JSONObject jsonObj = (JSONObject)obj;
		
		//main brick
		out.setMainBrickWrapper((HAPWrapperBrickRoot)parseService.parseEntityJSONExplicit(jsonObj.getJSONObject(HAPBundleForBrick.MAINBRICK), HAPWrapperBrickRoot.class.getName()));
		
		//branch bricks
		JSONObject branchBricksJsonObj = jsonObj.optJSONObject(HAPBundleForBrick.BRANCHBRICKS);
		for(Object key : branchBricksJsonObj.keySet()) {
			String name = (String)key;
			out.setBranchBrickWrapper(name, (HAPWrapperBrickRoot)parseService.parseEntityJSONExplicit(branchBricksJsonObj.getJSONObject(name), HAPWrapperBrickRoot.class.getName()));
		}
		
		//event process
		JSONObject eventProcessJsonObj = jsonObj.getJSONObject(HAPBundleForBrick.EVENTPROCESS);
		for(Object key : eventProcessJsonObj.keySet()) {
			String name  = (String)key;
			HAPEventProcess eventProcess = (HAPEventProcess)parseService.parseEntityJSONExplicit(eventProcessJsonObj.getJSONObject(name), HAPEventProcess.class.getName());
			out.addEventProcess(name, eventProcess);
		}
		
		//exposed event
		JSONArray exposedEventJsonArray = jsonObj.getJSONArray(HAPBundleForBrick.EXPORTEVENT);
		for(int i=0; i<exposedEventJsonArray.length(); i++) {
			out.addExportEvent((HAPEventEmitter)parseService.parseEntityJSONExplicit(exposedEventJsonArray.getJSONObject(i), HAPEventEmitter.class.getName()));
		}
		
		//command exposed
		JSONObject commandExposesJsonObj = jsonObj.getJSONObject(HAPBundleForBrick.EXPORTCOMMAND);
		for(Object key : commandExposesJsonObj.keySet()) {
			String name  = (String)key;
			HAPCommandProcess command = (HAPCommandProcess)parseService.parseEntityJSONExplicit(commandExposesJsonObj.getJSONObject(name), HAPCommandProcess.class.getName());
			out.addCommandExport(command);
		}
		
		//alias mapping
		JSONObject aliasMappingJsonObj = jsonObj.getJSONObject(HAPBundleForBrick.ALIASMAPPING);
		for(Object key : aliasMappingJsonObj.keySet()) {
			String name  = (String)key;
			out.addAliasMapping(name, new HAPPath(aliasMappingJsonObj.getString(name)));
		}
		
		//export resource info
		JSONArray exposeResourceInfosJsonArray = jsonObj.getJSONArray(HAPBundleForBrick.EXPORTRESOURCE);
		for(int i=0; i<exposeResourceInfosJsonArray.length(); i++) {
			HAPInfoExportBrick exportBrickInfo = new HAPInfoExportBrick();
			exportBrickInfo.buildObject(exposeResourceInfosJsonArray.getJSONObject(i), HAPSerializationFormat.JSON);
			out.addExportResourceInfo(exportBrickInfo);
		}
		
		//value structure domain
		out.setValueStructureDomain((HAPDomainValueStructure)parseService.parseEntityJSONExplicit(jsonObj.getJSONObject(HAPBundleForBrick.VALUESTRUCTUREDOMAIN), HAPDomainValueStructure.class.getName()));
		
		//dynamic info
		
		
		return out;
	}
	
}



@HAPEntityWithAttribute
public class HAPBundleForBrick extends HAPSerializableImp implements HAPWithResourceDependency, HAPCommandWithExport, HAPEntityParsable{

	@HAPAttribute
	public final static String MAINBRICK = "mainBrick"; 

	@HAPAttribute
	public final static String BRANCHBRICKS = "branchBricks"; 

	@HAPAttribute
	public static final String VALUESTRUCTUREDOMAIN = "valueStructureDomain";

	@HAPAttribute
	public final static String DYNAMIC = "dynamic"; 

	@HAPAttribute
	public final static String ALIASMAPPING = "aliasMapping"; 

	@HAPAttribute
	public final static String EVENTPROCESS = "eventProcess"; 

	@HAPAttribute
	public final static String EXPORTEVENT = "eventExport"; 

	@HAPAttribute
	public final static String EXTRADATA = "extraData"; 

	@HAPAttribute
	public final static String EXPORTRESOURCE = "exportResource"; 

	private HAPWrapperBrickRoot m_mainBrickWrapper;

	//other brick that support main brick, for instance, global task
	private Map<String, HAPWrapperBrickRoot> m_branchBricks;
	
	//processed value structure
	private HAPDomainValueStructure m_valueStructureDomain;
	
	//all events and their handler reference pair in bundle
	private Map<String, HAPEventProcess> m_eventProcesses;
	//event that will expose to external
	private List<HAPEventEmitter> m_exportEvents;
	
	private Map<String, HAPCommandProcess> m_commandExports;

	//need dynamic input during runtime
	private HAPDynamicDefinitionContainer m_dynamicInfo;

	private Map<String, HAPPath> m_aliasMapping;
	
	private Object m_extraData;

	private List<HAPInfoExportBrick> m_exportResourceInfos;
	
	public HAPBundleForBrick() {
		this.m_valueStructureDomain = new HAPDomainValueStructure();
		this.m_exportResourceInfos = new ArrayList<HAPInfoExportBrick>();
		this.m_branchBricks = new LinkedHashMap<String, HAPWrapperBrickRoot>();
		this.m_eventProcesses = new LinkedHashMap<String, HAPEventProcess>();
		this.m_exportEvents = new ArrayList<HAPEventEmitter>();
		this.m_aliasMapping = new LinkedHashMap<String, HAPPath>();

		this.m_commandExports = new LinkedHashMap<String, HAPCommandProcess>();

		this.m_dynamicInfo = new HAPDynamicDefinitionContainer(); 
		
		HAPInfoExportBrick defaultExport = new HAPInfoExportBrick(new HAPPath());
		defaultExport.setName(HAPConstantShared.NAME_DEFAULT);
		this.addExportResourceInfo(defaultExport);
	}
	
	public void addEventProcess(String id, HAPEventProcess eventProcess) {     this.m_eventProcesses.put(id, eventProcess);       }
	public HAPEventProcess getEventProcess(String id) {       return this.m_eventProcesses.get(id);       }
	public Map<String, HAPEventProcess> getEventProcesses(){    return this.m_eventProcesses;       }
	
	public void addExportEvent(HAPEventEmitter eventEmitter) {     this.m_exportEvents.add(eventEmitter);    }
	public List<HAPEventEmitter> getExportEvents(){    return this.m_exportEvents;      }
	
	@Override
	public List<String> getCommandExportNames() {    return new ArrayList<>(this.m_commandExports.keySet());  }
	@Override
	public HAPCommandProcess getCommandExport(String name) {   return this.m_commandExports.get(name);   }
	public void addCommandExport(HAPCommandProcess command) {    this.m_commandExports.put(command.getCommandDefinition().getName(), command);         }
	public Map<String, HAPCommandProcess> getCommandExorts(){     return this.m_commandExports;       }
	
	public void addExportResourceInfo(HAPInfoExportBrick exportResourceInfo) {		
		exportResourceInfo.setPathFromRoot(HAPUtilityBundleForBrick.normalizePathWithBranch(exportResourceInfo.getPathFromRoot().getPath(), HAPConstantShared.NAME_ROOTBRICK_MAIN));
		this.m_exportResourceInfos.add(exportResourceInfo);	
	}
	public List<HAPInfoExportBrick> getExportResourceInfos(){    return this.m_exportResourceInfos;    }
	
	public HAPDomainValueStructure getValueStructureDomain() {	return this.m_valueStructureDomain;	}
	public void setValueStructureDomain(HAPDomainValueStructure valueStructureDomain) {      this.m_valueStructureDomain = valueStructureDomain;         }
	
	public void addRootBrickWrapper(HAPWrapperBrickRoot brickWrapper) {
		String name = brickWrapper.getName();
		if(HAPConstantShared.NAME_ROOTBRICK_MAIN.equals(name)) {
			this.setMainBrickWrapper(brickWrapper);
		}
		else {
			this.setBranchBrickWrapper(name, brickWrapper);
		}
	}
	public HAPWrapperBrickRoot getRootBrickWrapper(String name) {
		if(HAPConstantShared.NAME_ROOTBRICK_MAIN.equals(name)) {
			return this.getMainBrickWrapper();
		}
		else {
			return this.getBranchBrickWrapper(name);
		}
	}
	
	public HAPWrapperBrickRoot getMainBrickWrapper() {    return this.m_mainBrickWrapper;     }
	public void setMainBrickWrapper(HAPWrapperBrickRoot brickWrapper) {     this.m_mainBrickWrapper = brickWrapper;      }
	
	public void setBranchBrickWrapper(String branch, HAPWrapperBrickRoot brickWrapper) {     this.m_branchBricks.put(branch, brickWrapper);        }
	public HAPWrapperBrickRoot getBranchBrickWrapper(String branch) {     return this.m_branchBricks.get(branch);         }
	public Map<String, HAPWrapperBrickRoot> getBranchBrickWrappers() {     return this.m_branchBricks;         }
	public Set<String> getBranchNames(){   return this.m_branchBricks.keySet();    }
	
	public HAPDynamicDefinitionContainer getDynamicInfo() {     return this.m_dynamicInfo;        }
	public void setDynamicInfo(HAPDynamicDefinitionContainer dynamicInfo) {      this.m_dynamicInfo = dynamicInfo;     }
	
	public Object getExtraData() {   return this.m_extraData;    }
	public void setExtraData(Object data) {   this.m_extraData = data;    }

	public Map<String, HAPPath> getAliasMappings(){    return this.m_aliasMapping;     }
	public void addAliasMapping(String alias, HAPPath path) {    this.m_aliasMapping.put(alias, path);       }
	public HAPPath getBrickPathByAlias(String alias) {    return this.m_aliasMapping.get(alias);      }
	
	public Set<HAPResourceIdSimple> getResourceDependency(){
		Set<HAPResourceIdSimple> out = new HashSet<HAPResourceIdSimple>();
//		for(HAPInfoResourceIdNormalize normalizedResourceId : this.m_externalComplexEntityDpendency) {
//			out.add(normalizedResourceId.getRootResourceIdSimple());
//		}
		return out;
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(MAINBRICK, this.m_mainBrickWrapper.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(VALUESTRUCTUREDOMAIN, this.m_valueStructureDomain.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(DYNAMIC, this.m_dynamicInfo.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(EXTRADATA, HAPManagerSerialize.getInstance().toStringValue(m_extraData, HAPSerializationFormat.JSON));
		jsonMap.put(ALIASMAPPING, HAPUtilityJson.buildJsonStringValue(this.m_aliasMapping, HAPSerializationFormat.JSON));
		
		Map<String, String> branchJsonMap = new LinkedHashMap<String, String>();
		for(String branch : this.m_branchBricks.keySet()) {
			branchJsonMap.put(branch, this.m_branchBricks.get(branch).toStringValue(HAPSerializationFormat.JSON));
		}
		jsonMap.put(BRANCHBRICKS, HAPUtilityJson.buildMapJson(branchJsonMap));
		
		List<String> eventStrArray = new ArrayList<String>();
		for(HAPEventProcess eventProcess : this.m_eventProcesses.values()) {
			eventStrArray.add(eventProcess.toStringValue(HAPSerializationFormat.JSON));
		}
		jsonMap.put(EVENTPROCESS, HAPUtilityJson.buildArrayJson(eventStrArray.toArray(new String[0])));
		
		jsonMap.put(EXPORTEVENT, HAPManagerSerialize.getInstance().toStringValue(this.m_exportEvents, HAPSerializationFormat.JSON));

		jsonMap.put(EXPORTCOMMAND, HAPManagerSerialize.getInstance().toStringValue(this.m_commandExports, HAPSerializationFormat.JSON));

		jsonMap.put(EXPORTRESOURCE, HAPManagerSerialize.getInstance().toStringValue(this.m_exportResourceInfos, HAPSerializationFormat.JSON));
    }
	
	@Override
	protected void buildJSJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJSJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(MAINBRICK, this.m_mainBrickWrapper.toStringValue(HAPSerializationFormat.JAVASCRIPT));
		jsonMap.put(VALUESTRUCTUREDOMAIN, this.m_valueStructureDomain.toStringValue(HAPSerializationFormat.JAVASCRIPT));
		jsonMap.put(DYNAMIC, this.m_dynamicInfo.toStringValue(HAPSerializationFormat.JAVASCRIPT));
		jsonMap.put(EXTRADATA, HAPManagerSerialize.getInstance().toStringValue(m_extraData, HAPSerializationFormat.JAVASCRIPT));
		jsonMap.put(ALIASMAPPING, HAPUtilityJson.buildJsonStringValue(this.m_aliasMapping, HAPSerializationFormat.JAVASCRIPT));

		Map<String, String> branchJsonMap = new LinkedHashMap<String, String>();
		for(String branch : this.m_branchBricks.keySet()) {
			branchJsonMap.put(branch, this.m_branchBricks.get(branch).toStringValue(HAPSerializationFormat.JAVASCRIPT));
		}
		jsonMap.put(BRANCHBRICKS, HAPUtilityJson.buildMapJson(branchJsonMap));

		jsonMap.put(EVENTPROCESS, HAPManagerSerialize.getInstance().toStringValue(this.m_eventProcesses, HAPSerializationFormat.JSON)); 

		jsonMap.put(EXPORTEVENT, HAPManagerSerialize.getInstance().toStringValue(this.m_exportEvents, HAPSerializationFormat.JSON));

		jsonMap.put(EXPORTCOMMAND, HAPManagerSerialize.getInstance().toStringValue(this.m_commandExports, HAPSerializationFormat.JAVASCRIPT));

		jsonMap.put(EXPORTRESOURCE, HAPManagerSerialize.getInstance().toStringValue(this.m_exportResourceInfos, HAPSerializationFormat.JSON));
	}
	
	@Override
	public void buildResourceDependency(List<HAPResourceDependency> dependency, HAPRuntimeInfo runtimeInfo) {
		this.m_mainBrickWrapper.buildResourceDependency(dependency, runtimeInfo);
	}

}

