package com.nosliw.core.application.common.event;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.application.common.structure.HAPStructure;

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
    
}
