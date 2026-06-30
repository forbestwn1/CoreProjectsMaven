package com.nosliw.core.application.entity.uitag;

import java.util.ArrayList;
import java.util.List;

import com.nosliw.common.constant.HAPAttribute;
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
	
}
