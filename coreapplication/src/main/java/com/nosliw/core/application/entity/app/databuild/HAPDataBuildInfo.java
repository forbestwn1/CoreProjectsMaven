package com.nosliw.core.application.entity.app.databuild;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.common.datadefinition.HAPDataDefinition;
import com.nosliw.core.application.common.datadefinition.HAPParserDataDefinition;

@HAPEntityWithAttribute
public class HAPDataBuildInfo extends HAPSerializableImp{

	@HAPAttribute
	public static final String DATADEFINITION = "dataDefinition";
	
	@HAPAttribute
	public static final String DATABUILD = "dataBuild";
	
	private HAPDataDefinition m_dataDefinition;
	
	private HAPDataBuild m_dataBuild;
	
	public HAPDataDefinition getDataDefinition() {      return this.m_dataDefinition;         }
	public void setDataDefinition(HAPDataDefinition dataDefinition) {    this.m_dataDefinition = dataDefinition;         }
	
	public HAPDataBuild getDataBuild() {     return this.m_dataBuild;      }
	public void setDataBuild(HAPDataBuild dataBuild) {     this.m_dataBuild = dataBuild;       }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		
		if(this.m_dataDefinition!=null) {
			jsonMap.put(DATADEFINITION, HAPManagerSerialize.getInstance().toStringValue(this.m_dataDefinition, HAPSerializationFormat.JSON));
		}
		
		if(this.m_dataBuild!=null) {
			jsonMap.put(DATABUILD, HAPManagerSerialize.getInstance().toStringValue(this.m_dataBuild, HAPSerializationFormat.JSON));
		}
	}

	public static HAPDataBuildInfo buildDataBuildInfo(JSONObject jsonObj, HAPServiceParseEntity parseService) {
		HAPDataBuildInfo out = new HAPDataBuildInfo();
		out.setDataDefinition(HAPParserDataDefinition.parseDataDefinition(jsonObj.optJSONObject(DATADEFINITION), parseService));
		out.setDataBuild(HAPDataBuild.buildDataBuild(jsonObj.optJSONObject(DATABUILD), parseService));
		return out;
	}
}
