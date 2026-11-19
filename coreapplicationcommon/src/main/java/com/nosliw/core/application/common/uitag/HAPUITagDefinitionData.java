package com.nosliw.core.application.common.uitag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;

public class HAPUITagDefinitionData extends HAPUITagDefinition{

	@HAPAttribute
	public static final String ATTRIBUTEFORDATA = "attributeForData";

	@HAPAttribute
	public static final String IOMODE = "ioMode";
	
	@HAPAttribute
	public static final String DATAMODE = "dataMode";
	
	private List<String> m_attributeForData;
	
	private Set<String> m_ioModes;
	
	private Set<String> m_dataModes;
	
	public HAPUITagDefinitionData() {
		this.m_attributeForData = new ArrayList<String>();
		this.m_ioModes = new HashSet<String>();
		this.m_dataModes = new HashSet<String>();
	}

	@Override
	public String getType() {  return HAPConstantShared.UITAG_TYPE_DATA;    }
	
	public List<String> getAttributeForData() {     return this.m_attributeForData;      }
	public void addAttributeForData(String attributeForData) {    this.m_attributeForData.add(attributeForData);         }

	public void addIOMode(String ioMode) {    this.m_ioModes.add(ioMode);       }
	public Set<String> getIOModes() {     return this.m_ioModes;       }
	
	public Set<String> getDataModes() {    return this.m_dataModes;      }
	public void addDataMode(String dataMode) {     this.m_dataModes.add(dataMode);        }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		
		jsonMap.put(ATTRIBUTEFORDATA, HAPManagerSerialize.getInstance().toStringValue(this.m_attributeForData, HAPSerializationFormat.JSON));
		jsonMap.put(DATAMODE, HAPManagerSerialize.getInstance().toStringValue(this.m_dataModes, HAPSerializationFormat.JSON));
		jsonMap.put(IOMODE, HAPManagerSerialize.getInstance().toStringValue(this.m_ioModes, HAPSerializationFormat.JSON));
	}
}

@Component
class HAPUITagDefinitionData_parser extends HAPUITagDefinition_parser{

	@Override
	public String getSubName() {    return HAPConstantShared.UITAG_TYPE_DATA;     }
	
	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPUITagDefinitionData out = new HAPUITagDefinitionData();
		JSONObject jsonObj = (JSONObject)obj;
		this.parseToUITagDefinitionJson(out, jsonObj, parseService);
		
		JSONArray dataModeArray = jsonObj.optJSONArray(HAPUITagDefinitionData.DATAMODE);
		for(int i=0; i<dataModeArray.length(); i++) {
			out.addDataMode(dataModeArray.getString(i));
		}
		if(out.getDataModes().size()==0) {
			out.addDataMode(HAPConstantShared.UITAG_DATAMODE_SINGLE);
		}
		
		JSONArray ioModeArray = jsonObj.optJSONArray(HAPUITagDefinitionData.IOMODE);
		for(int i=0; i<ioModeArray.length(); i++) {
			out.addIOMode(ioModeArray.getString(i));
		}
		
		JSONArray attrForDatasArray = jsonObj.optJSONArray(HAPUITagDefinitionData.ATTRIBUTEFORDATA);
		for(int i=0; i<attrForDatasArray.length(); i++) {
			out.addAttributeForData(attrForDatasArray.getString(i));
		}
		
		return out;
	}

}
