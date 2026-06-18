package com.nosliw.core.application.entity.uitag;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.application.common.datadefinition.HAPDataDefinition;
import com.nosliw.core.application.common.datadefinition.HAPDataDefinitionWritable;
import com.nosliw.core.application.common.datadefinition.HAPParserDataDefinition;
import com.nosliw.core.data.criteria.HAPDataTypeCriteria;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPUITageQueryData extends HAPSerializableImp{

	@HAPAttribute
	final public static String DATADEFINITION = "dataDefinition";

	private HAPDataDefinition m_dataDefinition;
	
	public HAPUITageQueryData() {	}

	public HAPUITageQueryData(HAPDataDefinition dataDefinition) {
		this.m_dataDefinition = dataDefinition;
	}
	
	public HAPUITageQueryData(HAPDataTypeCriteria dataTypeCriteria) {
		this(new HAPDataDefinitionWritable(dataTypeCriteria));
	}
	
	public void setDataDefinition(HAPDataDefinition dataDefinition) {    this.m_dataDefinition = dataDefinition;         }
	public HAPDataDefinition getDataDefinition() {     return this.m_dataDefinition;      }
	
	public HAPDataTypeCriteria getDataTypeCriterai() {   return this.m_dataDefinition.getCriteria();  }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(DATADEFINITION, this.m_dataDefinition.toStringValue(HAPSerializationFormat.LITERATE));
	}
	
	public HAPUITageQueryData parseUITagQueryData(JSONObject jsonOb, HAPServiceParseEntity entityParseService) {
		HAPUITageQueryData out = new HAPUITageQueryData();
		out.setDataDefinition(HAPParserDataDefinition.parseDataDefinition(jsonOb.getJSONObject(DATADEFINITION), entityParseService));
		return out;
	}

}
