package com.nosliw.core.application;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.path.HAPPath;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.core.application.common.command.HAPCommandProcess;
import com.nosliw.core.application.common.command.HAPCommandWithExport;
import com.nosliw.core.application.common.event.HAPEventEmitter;
import com.nosliw.core.application.common.event.HAPEventProcess;
import com.nosliw.core.application.valueport.HAPIdElement;

@HAPEntityWithAttribute
public class HAPBundleForExecute extends HAPSerializableImp implements HAPCommandWithExport{

	@HAPAttribute
	public final static String BRICK = "brick"; 

	@HAPAttribute
	public final static String SUPPORTBRICKS = "supportBricks"; 

	@HAPAttribute
	public final static String ALIASMAPPING = "aliasMapping"; 

	@HAPAttribute
	public final static String EVENTPROCESS = "eventProcess"; 

	@HAPAttribute
	public final static String EXPORTEVENT = "eventExport"; 

	@HAPAttribute
	public static final String VALUESTRUCTUREDOMAIN = "valueStructureDomain";

	@HAPAttribute
	public final static String EXPORTBRICKINFO = "exportBrickInfo"; 
	
	@HAPAttribute
	public final static String EXPORTVARIABLEINFO = "exportVariableInfo"; 
	
	private HAPBrick m_brick;
	
	private Map<String, HAPBrick> m_supportBricks;
	
	private Map<String, HAPPath> m_aliasMapping;
	
	//all events and their handler reference pair in bundle
	private Map<String, HAPEventProcess> m_eventProcesses;
	
	//event that will expose to external
	private List<HAPEventEmitter> m_exportEvents;
	
	private Map<String, HAPCommandProcess> m_commandExports;

	//processed value structure
	private HAPDomainValueStructure m_valueStructureDomain;
	
	private HAPInfoExportBrick m_exportBrickInfo;
	
	private Map<String, HAPIdElement> m_exportVariableInfos;
	
	public HAPBundleForExecute() {
		this.m_supportBricks = new LinkedHashMap<String, HAPBrick>();
		this.m_aliasMapping = new LinkedHashMap<String, HAPPath>();
		this.m_exportVariableInfos = new LinkedHashMap<String, HAPIdElement>();
		this.m_eventProcesses = new LinkedHashMap<String, HAPEventProcess>();
		this.m_exportEvents = new ArrayList<HAPEventEmitter>();
		this.m_commandExports = new LinkedHashMap<String, HAPCommandProcess>();
	}

	public HAPBrick getBrick() {    return this.m_brick;     }
	public void setBrick(HAPBrick brick) {      this.m_brick = brick;      }
	
	public void addEventProcess(String id, HAPEventProcess eventProcess) {     this.m_eventProcesses.put(id, eventProcess);       }
	public HAPEventProcess getEventProcess(String id) {       return this.m_eventProcesses.get(id);       }
	public Map<String, HAPEventProcess> getEventProcesses(){    return this.m_eventProcesses;       }
	
	public void addExportEvent(HAPEventEmitter eventEmitter) {     this.m_exportEvents.add(eventEmitter);    }
	public List<HAPEventEmitter> getExportEvents(){    return this.m_exportEvents;      }

	@Override
	public List<String> getCommandExportNames() {    return new ArrayList<>(this.m_commandExports.keySet());  }
	@Override
	public HAPCommandProcess getCommandExport(String name) {   return this.m_commandExports.get(name);   }
	public void addCommandExport(HAPCommandProcess command) {     this.m_commandExports.put(command.getCommandDefinition().getName(), command);         }

	public HAPDomainValueStructure getValueStructureDomain() {   return this.m_valueStructureDomain;    }
	public void setValueStructureDomain(HAPDomainValueStructure valueStructureDomain) {     this.m_valueStructureDomain = valueStructureDomain;      }
	
	public void addSupportBrick(String name, HAPBrick brick) {    this.m_supportBricks.put(name, brick);     }
	public void setExportBrickInfo(HAPInfoExportBrick exportBrickInfo) {    this.m_exportBrickInfo = exportBrickInfo;      }
	
	public void addAliasMapping(String name, HAPPath path) {   this.m_aliasMapping.put(name, path);     }
	public void addAliasMappings(Map<String, HAPPath> mappings) {    this.m_aliasMapping.putAll(mappings);      }
	
	public void addExportVariableInfo(String name, HAPIdElement variableId) {      this.m_exportVariableInfos.put(name, variableId);          }
	
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(BRICK, this.m_brick.toStringValue(HAPSerializationFormat.JSON));
		if(this.m_valueStructureDomain!=null) {
			jsonMap.put(VALUESTRUCTUREDOMAIN, this.m_valueStructureDomain.toStringValue(HAPSerializationFormat.JSON));
		}
		
		Map<String, String> supportBrickMap = new LinkedHashMap<String, String>();
		for(String name : this.m_supportBricks.keySet()) {
			supportBrickMap.put(name, this.m_supportBricks.get(name).toStringValue(HAPSerializationFormat.JSON));
		}
		jsonMap.put(SUPPORTBRICKS, HAPUtilityJson.buildMapJson(supportBrickMap));
		jsonMap.put(ALIASMAPPING, HAPUtilityJson.buildJsonStringValue(this.m_aliasMapping, HAPSerializationFormat.JSON));
		jsonMap.put(EXPORTBRICKINFO, this.m_exportBrickInfo.toStringValue(HAPSerializationFormat.JSON));
		
		Map<String, String> exportVariableMap = new LinkedHashMap<String, String>();
		for(String varName : this.m_exportVariableInfos.keySet()) {
			exportVariableMap.put(varName, this.m_exportVariableInfos.get(varName).toStringValue(HAPSerializationFormat.JSON));
		}
		jsonMap.put(EXPORTVARIABLEINFO, HAPUtilityJson.buildMapJson(exportVariableMap));

		jsonMap.put(EVENTPROCESS, HAPManagerSerialize.getInstance().toStringValue(this.m_eventProcesses, HAPSerializationFormat.JSON));

		jsonMap.put(EXPORTEVENT, HAPManagerSerialize.getInstance().toStringValue(this.m_exportEvents, HAPSerializationFormat.JSON));
    
		jsonMap.put(EXPORTCOMMAND, HAPManagerSerialize.getInstance().toStringValue(this.m_commandExports, HAPSerializationFormat.JSON));
	}
	
	@Override
	protected void buildJSJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJSJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(BRICK, this.m_brick.toStringValue(HAPSerializationFormat.JAVASCRIPT));
		if(this.m_valueStructureDomain!=null) {
			jsonMap.put(VALUESTRUCTUREDOMAIN, this.m_valueStructureDomain.toStringValue(HAPSerializationFormat.JAVASCRIPT));
		}

		Map<String, String> supportBrickMap = new LinkedHashMap<String, String>();
		for(String name : this.m_supportBricks.keySet()) {
			supportBrickMap.put(name, this.m_supportBricks.get(name).toStringValue(HAPSerializationFormat.JAVASCRIPT));
		}
		jsonMap.put(SUPPORTBRICKS, HAPUtilityJson.buildMapJson(supportBrickMap));
		jsonMap.put(ALIASMAPPING, HAPUtilityJson.buildJsonStringValue(this.m_aliasMapping, HAPSerializationFormat.JAVASCRIPT));
		jsonMap.put(EXPORTBRICKINFO, this.m_exportBrickInfo.toStringValue(HAPSerializationFormat.JSON));

		Map<String, String> exportVariableMap = new LinkedHashMap<String, String>();
		for(String varName : this.m_exportVariableInfos.keySet()) {
			exportVariableMap.put(varName, this.m_exportVariableInfos.get(varName).toStringValue(HAPSerializationFormat.JSON));
		}
		jsonMap.put(EXPORTVARIABLEINFO, HAPUtilityJson.buildMapJson(exportVariableMap));

		jsonMap.put(EVENTPROCESS, HAPManagerSerialize.getInstance().toStringValue(this.m_eventProcesses, HAPSerializationFormat.JSON));

		jsonMap.put(EXPORTEVENT, HAPManagerSerialize.getInstance().toStringValue(this.m_exportEvents, HAPSerializationFormat.JSON));
		
		jsonMap.put(EXPORTCOMMAND, HAPManagerSerialize.getInstance().toStringValue(this.m_commandExports, HAPSerializationFormat.JAVASCRIPT));
		
	}
}
