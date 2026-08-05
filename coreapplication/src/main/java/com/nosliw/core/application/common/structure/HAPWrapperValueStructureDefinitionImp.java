package com.nosliw.core.application.common.structure;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPParserEntity;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPWrapperValueStructureDefinitionImp extends HAPEntityInfoImp implements HAPWrapperValueStructureDefinition, HAPEntityParsable{

	private HAPValueStructure m_valueStructure;
	
	private HAPInfoStructureInWrapper m_valueStructureInfo;
	
	public HAPWrapperValueStructureDefinitionImp() {
		this.m_valueStructureInfo = new HAPInfoStructureInWrapper(); 
	}
	
	public HAPWrapperValueStructureDefinitionImp(HAPValueStructure valueStructure) {
		this();
		this.m_valueStructure = valueStructure;
	}
	
	@Override
	public HAPValueStructure getValueStructure() {   return  this.m_valueStructure;  } 

	@Override
	public void setValueStructure(HAPValueStructure valueStructure) {   this.m_valueStructure = valueStructure;  }

	@Override
	public HAPInfoStructureInWrapper getStructureInfo() {   return this.m_valueStructureInfo;   }

	@Override
	public void setStructureInfo(HAPInfoStructureInWrapper info) {   this.m_valueStructureInfo = info;   }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(VALUESTRUCTURE, HAPManagerSerialize.getInstance().toStringValue(this.m_valueStructure, HAPSerializationFormat.JSON));
		jsonMap.put(VALUESTRUCTUREINFO, HAPManagerSerialize.getInstance().toStringValue(this.m_valueStructureInfo, HAPSerializationFormat.JSON));
	}

}

@Component
class HAPWrapperValueStructureDefinitionImp_parser implements HAPParserEntity{

	@Override
	public String getEntityType() {     return HAPWrapperValueStructureDefinitionImp.class.getName();   }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPWrapperValueStructureDefinitionImp out = new HAPWrapperValueStructureDefinitionImp();
		
		JSONObject jsonObj = (JSONObject)obj;
		out.buildEntityInfoByJson(jsonObj);
		
		JSONObject vsJsonObj = jsonObj.optJSONObject(HAPWrapperValueStructureDefinition.VALUESTRUCTURE);
		if(vsJsonObj!=null) {
			HAPValueStructureImp vs = new HAPValueStructureImp(); 
			HAPUtilityParserStructure.parseValueStructureJson(vsJsonObj, vs, parseService);
			out.setValueStructure(vs);
		}
		
		JSONObject vsInfoJsonObj = jsonObj.optJSONObject(HAPWrapperValueStructureDefinition.VALUESTRUCTUREINFO);
		if(vsInfoJsonObj!=null) {
			
			out.setStructureInfo(HAPUtilityParserStructure.parseValueStructureWrapper(vsInfoJsonObj, parseService));
			
			HAPInfoStructureInWrapper vsInfo = new HAPInfoStructureInWrapper(); 
			out.setValueStructure(vs);
		}

		
		return out;
	}
	
}

