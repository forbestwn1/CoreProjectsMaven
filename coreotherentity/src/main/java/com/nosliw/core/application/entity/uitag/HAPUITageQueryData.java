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

	@HAPAttribute
	final public static String IOMODE = "ioMode";

	@HAPAttribute
	final public static String DATAMODE = "dataMode";

	private HAPDataDefinition m_dataDefinition;

	private String m_ioMode;
	
	private String m_dataMode;
	
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

	public void setIOMode(String ioMode) {    this.m_ioMode = ioMode;       }
	public String getIOMode() {     return this.m_ioMode;       }
	
	public void setDataMode(String dataMode) {     this.m_dataMode = dataMode;       }
	public String getDataMode() {      return this.m_dataMode;      }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(DATADEFINITION, this.m_dataDefinition.toStringValue(HAPSerializationFormat.LITERATE));
		jsonMap.put(IOMODE, this.m_ioMode);
		jsonMap.put(DATAMODE, this.m_dataMode);
	}
	
	public static HAPUITageQueryData parseUITagQueryData(JSONObject jsonOb, HAPServiceParseEntity entityParseService) {
		HAPUITageQueryData out = new HAPUITageQueryData();
		out.setDataDefinition(HAPParserDataDefinition.parseDataDefinition(jsonOb.getJSONObject(DATADEFINITION), entityParseService));
		out.setIOMode((String)jsonOb.opt(IOMODE));
		out.setDataMode((String)jsonOb.opt(DATAMODE));
		return out;
	}

}
