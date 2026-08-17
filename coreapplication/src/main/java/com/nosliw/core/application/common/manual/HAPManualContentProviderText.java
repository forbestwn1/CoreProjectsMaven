package com.nosliw.core.application.common.manual;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPParserEntity;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.brick.HAPIdBrick;
import com.nosliw.core.application.common.command.HAPCommandProcess;
import com.nosliw.core.application.common.event.HAPEventEmitter;
import com.nosliw.core.application.dynamic.HAPDynamicDefinitionContainer;

public class HAPManualContentProviderText extends HAPSerializableImp implements HAPManualContentProvider, HAPEntityParsable{

	public static final String MAINCONENTINFO = "mainContentInfo";
	
	public static final String BRANCHCONTENTINFO = "branchContentInfo";
	
	public static final String LOCALCONTENTINFO = "localContentInfo";
	
	public static final String EVENTEXPOSE = "eventExpose";
	
	public static final String COMMANDEXPOSE = "commandExpose";
	
	private HAPManualInfoContent m_contentInfo;
	
	private Map<String, HAPManualInfoContent> m_branchContentInfos;
	
	private Map<String, HAPManualInfoContent> m_localContentInfos;
	
	private List<HAPEventEmitter> m_eventExpose;
	
	private List<HAPCommandProcess> m_commandExpose;
	
	private HAPDynamicDefinitionContainer m_dynamicDef;
	
	public HAPManualContentProviderText() {
		this.m_branchContentInfos = new LinkedHashMap<String, HAPManualInfoContent>();
		this.m_localContentInfos = new LinkedHashMap<String, HAPManualInfoContent>();
		this.m_eventExpose = new ArrayList<HAPEventEmitter>();
		this.m_commandExpose = new ArrayList<HAPCommandProcess>();
		this.m_dynamicDef = new HAPDynamicDefinitionContainer();
	}
	
	@Override
	public HAPDynamicDefinitionContainer getDynamicDefinition() {		return this.m_dynamicDef; 	}
	public void setDyanmicDefinition(HAPDynamicDefinitionContainer dynamicDef) {     this.m_dynamicDef = dynamicDef;      }

	@Override
	public HAPManualInfoContent getMainContent() {		return this.m_contentInfo;	}
	
	public void setMainContent(HAPManualInfoContent contentInfo) {		this.m_contentInfo = contentInfo;	}

	@Override
	public Map<String, HAPManualInfoContent> getBranchContents() {		return this.m_branchContentInfos;	}
	
	public void addBranchContent(String name, HAPManualInfoContent contentInfo) {		this.m_branchContentInfos.put(name, contentInfo);	}

	@Override
	public HAPManualInfoContent getLocalBrickContent(HAPIdBrick brickId) {		return this.m_localContentInfos.get(brickId.getKey());	}
	
	public Map<String, HAPManualInfoContent> getLocalBrickContents(){		return this.m_localContentInfos;	}
	
	public void addLocalBrickContent(HAPIdBrick brickId, HAPManualInfoContent contentInfo) {		this.m_localContentInfos.put(brickId.getKey(), contentInfo);	}

	@Override
	public List<HAPEventEmitter> getExposedEvent() {		return this.m_eventExpose;	}
	public void addExposedEvent(HAPEventEmitter exposedEvent) {		this.m_eventExpose.add(exposedEvent);	}

	@Override
	public List<HAPCommandProcess> getExposedCommand(){     return this.m_commandExpose;      }
	public void addExposedCommand(HAPCommandProcess exposedCommand) {    this.m_commandExpose.add(exposedCommand);         }
	
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(MAINCONENTINFO, HAPManagerSerialize.getInstance().toStringValue(this.m_contentInfo, HAPSerializationFormat.JSON));
		jsonMap.put(BRANCHCONTENTINFO, HAPManagerSerialize.getInstance().toStringValue(this.m_branchContentInfos, HAPSerializationFormat.JSON));
		jsonMap.put(LOCALCONTENTINFO, HAPManagerSerialize.getInstance().toStringValue(this.m_localContentInfos, HAPSerializationFormat.JSON));
		jsonMap.put(EVENTEXPOSE, HAPManagerSerialize.getInstance().toStringValue(this.m_eventExpose, HAPSerializationFormat.JSON));
		jsonMap.put(COMMANDEXPOSE, HAPManagerSerialize.getInstance().toStringValue(this.m_commandExpose, HAPSerializationFormat.JSON));
	}

}

@Component
class HAPManualContentProviderText_Parser implements HAPParserEntity{

	@Override
	public String getEntityType() {   return HAPManualContentProviderText.class.getName();   }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPManualContentProviderText out = new HAPManualContentProviderText();
		JSONObject jsonObj = (JSONObject)obj;
		
		JSONObject mainContentInfoJsonObj =  jsonObj.optJSONObject(HAPManualContentProviderText.MAINCONENTINFO);
		if(mainContentInfoJsonObj!=null) {
			HAPManualInfoContent content = new HAPManualInfoContent();
			content.buildObject(mainContentInfoJsonObj, HAPSerializationFormat.JSON);
			out.setMainContent(content);
		}
		
		JSONObject branchContentInfosJsonObj =  jsonObj.optJSONObject(HAPManualContentProviderText.BRANCHCONTENTINFO);
		if(branchContentInfosJsonObj!=null) {
			for(Object key : branchContentInfosJsonObj.keySet()) {
				String name = (String)key;
				HAPManualInfoContent content = new HAPManualInfoContent();
				content.buildObject(branchContentInfosJsonObj.getJSONObject(name), HAPSerializationFormat.JSON);
				out.addBranchContent(name, content);
			}
		}
		
		JSONArray eventExposeJsonArray = jsonObj.optJSONArray(HAPManualContentProviderText.EVENTEXPOSE);
		for(int i=0; i<eventExposeJsonArray.length(); i++) {
			out.addExposedEvent((HAPEventEmitter)parseService.parseEntityJSONExplicit(eventExposeJsonArray.getJSONObject(i), HAPEventEmitter.class.getName()));
		}

		JSONArray commandExposeJsonArray = jsonObj.optJSONArray(HAPManualContentProviderText.COMMANDEXPOSE);
		for(int i=0; i<commandExposeJsonArray.length(); i++) {
			out.addExposedCommand((HAPCommandProcess)parseService.parseEntityJSONExplicit(commandExposeJsonArray.getJSONObject(i), HAPCommandProcess.class.getName()));
		}
		
		return out;
	}
}