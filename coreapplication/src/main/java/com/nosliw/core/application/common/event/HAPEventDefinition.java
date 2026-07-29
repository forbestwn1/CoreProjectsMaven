package com.nosliw.core.application.common.event;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.application.common.structure.HAPStructure;
import com.nosliw.core.application.common.structure.HAPStructureImp;
import com.nosliw.core.application.common.structure.HAPUtilityParserStructure;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPEventDefinition extends HAPEntityInfoImp{

	@HAPAttribute
	public static String DATADEFINITION = "dataDefinition";

	private HAPStructure m_dataDefinition;
	
	public HAPEventDefinition() {
		
	}
	
	public HAPStructure getDataDefinition() {    return this.m_dataDefinition;     }
    public void setDataDefinition(HAPStructure dataDef) {    this.m_dataDefinition = dataDef;      }	
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		if(this.m_dataDefinition!=null) {
			jsonMap.put(DATADEFINITION, this.m_dataDefinition.toStringValue(HAPSerializationFormat.JSON));
		}
	}
 
	public static HAPEventDefinition parseEventDefinition(Object obj, HAPServiceParseEntity entityParseService) {
		if(obj==null) {
			return null;
		}
		
		HAPEventDefinition out = new HAPEventDefinition();
		
		if(obj instanceof JSONObject) {
			JSONObject jsonObj = (JSONObject)obj;
			out.buildEntityInfoByJson(jsonObj);
			Object dfObj = jsonObj.opt(HAPEventDefinition.DATADEFINITION);
			if(dfObj!=null) {
				HAPStructure eventDataDef = new HAPStructureImp();
				HAPUtilityParserStructure.parseStuctureJson(dfObj, eventDataDef, entityParseService);
				out.setDataDefinition(eventDataDef);
			}
		}
		
        return out;
	}
	
}
