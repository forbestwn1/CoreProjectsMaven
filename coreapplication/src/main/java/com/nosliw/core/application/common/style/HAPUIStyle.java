package com.nosliw.core.application.common.style;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;

@HAPEntityWithAttribute
public class HAPUIStyle extends HAPSerializableImp{

	@HAPAttribute
	public static final String ID = "id";

	@HAPAttribute
	public static final String DEFINITION = "definition";

	private String m_id;
	private String m_definition;

	public HAPUIStyle() {
	}
	
	public String getId() {   return this.m_id;    }
	public void setId(String id) {     this.m_id = id;      }
	
	public String getDefinition() {   return this.m_definition;   }
	public void setDefinition(String def) {    this.m_definition = def;     }



	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(ID, HAPUtilityJson.buildJson(this.m_id, HAPSerializationFormat.JSON));
		jsonMap.put(DEFINITION, HAPUtilityJson.escape(this.m_definition));
	}

}
