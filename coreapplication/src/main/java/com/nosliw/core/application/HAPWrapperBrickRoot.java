package com.nosliw.core.application;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.application.common.serialize.HAPUtilityExport;
import com.nosliw.core.resource.HAPResourceDependency;
import com.nosliw.core.resource.HAPWithResourceDependency;
import com.nosliw.core.runtime.HAPRuntimeInfo;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPParserEntity;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPWrapperBrickRoot extends HAPEntityInfoImp implements HAPWithBrick, HAPWithResourceDependency, HAPEntityParsable{

	private HAPBrick m_brick;

	public HAPWrapperBrickRoot() {}
	
	public HAPWrapperBrickRoot(HAPBrick brick) {
		this.m_brick = brick;
	}
	
	@Override
	public HAPBrick getBrick() {   return this.m_brick;     }
	public void setEntity(HAPBrick entity) {     this.m_brick = entity;     }

	@Override
	protected void buildJSJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJSJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(BRICK, this.m_brick.toStringValue(HAPSerializationFormat.JAVASCRIPT));
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(BRICK, this.m_brick.toStringValue(HAPSerializationFormat.JSON));
	}

	@Override
	public void buildResourceDependency(List<HAPResourceDependency> dependency, HAPRuntimeInfo runtimeInfo) {
		this.m_brick.buildResourceDependency(dependency, runtimeInfo);
	}

}

@Component
class HAPWrapperBrickRoot_parser implements HAPParserEntity{

	@Autowired
	private HAPManagerApplicationBrick m_brickMan;
	
	@Override
	public String getEntityType() {    return HAPWrapperBrickRoot.class.getName();   }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		JSONObject jsonObj = (JSONObject)obj;
		
		HAPWrapperBrickRoot out = new HAPWrapperBrickRoot();
		
		out.setEntity(HAPUtilityExport.parseBrickJson(jsonObj.getJSONObject(HAPWithBrick.BRICK), m_brickMan));
		return out;
	}
	
}
