package com.nosliw.core.application;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.path.HAPPath;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.core.application.valueport.HAPIdElement;

@HAPEntityWithAttribute
public class HAPBundleForExecute extends HAPSerializableImp{

	@HAPAttribute
	public final static String BRICK = "brick"; 

	@HAPAttribute
	public final static String SUPPORTBRICKS = "supportBricks"; 

	@HAPAttribute
	public final static String ALIASMAPPING = "aliasMapping"; 

	@HAPAttribute
	public static final String VALUESTRUCTUREDOMAIN = "valueStructureDomain";

	@HAPAttribute
	public final static String EXPORTBRICKINFO = "exportBrickInfo"; 
	
	@HAPAttribute
	public final static String EXPORTVARIABLEINFO = "exportVariableInfo"; 
	
	private HAPBrick m_brick;
	
	private Map<String, HAPBrick> m_supportBricks;
	
	private Map<String, HAPPath> m_aliasMapping;
	
	//processed value structure
	private HAPDomainValueStructure m_valueStructureDomain;
	
	private HAPInfoExportBrick m_exportBrickInfo;
	
	private Map<String, HAPIdElement> m_exportVariableInfos;
	
	public HAPBundleForExecute() {
		this.m_supportBricks = new LinkedHashMap<String, HAPBrick>();
		this.m_aliasMapping = new LinkedHashMap<String, HAPPath>();
		this.m_exportVariableInfos = new LinkedHashMap<String, HAPIdElement>();
	}

	public HAPBrick getBrick() {    return this.m_brick;     }
	public void setBrick(HAPBrick brick) {      this.m_brick = brick;      }
	
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
	}
	
	@Override
	protected void buildJSJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		this.buildJsonMap(jsonMap, typeJsonMap);
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
	}
}
