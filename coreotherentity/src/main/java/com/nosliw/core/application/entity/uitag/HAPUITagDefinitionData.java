package com.nosliw.core.application.entity.uitag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;

public class HAPUITagDefinitionData extends HAPUITagDefinition{

	@HAPAttribute
	public static final String ATTRIBUTEFORDATA = "attributeForData";

	@HAPAttribute
	public static final String IOMODE = "ioMode";
	
	@HAPAttribute
	public static final String DATAMODE = "dataMode";
	
	private List<String> m_attributeForData;
	
	private String m_ioMode;
	
	private String m_dataMode;
	
	public HAPUITagDefinitionData() {
		this.m_attributeForData = new ArrayList<String>();
	}

	@Override
	public String getType() {  return HAPConstantShared.UITAG_TYPE_DATA;    }
	
	public List<String> getAttributeForData() {     return this.m_attributeForData;      }
	public void addAttributeForData(String attributeForData) {    this.m_attributeForData.add(attributeForData);         }

	public String getIOMode() {    return this.m_ioMode;      }
	public void setIOMode(String ioMode) {     this.m_ioMode = ioMode;        }
	
	public String getDataMode() {    return this.m_dataMode;      }
	public void setDataMode(String dataMode) {     this.m_dataMode = dataMode;        }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		
		jsonMap.put(ATTRIBUTEFORDATA, HAPManagerSerialize.getInstance().toStringValue(this.m_attributeForData, HAPSerializationFormat.JSON));
		jsonMap.put(DATAMODE, this.m_dataMode);
		jsonMap.put(IOMODE, this.m_ioMode);
	}
}
