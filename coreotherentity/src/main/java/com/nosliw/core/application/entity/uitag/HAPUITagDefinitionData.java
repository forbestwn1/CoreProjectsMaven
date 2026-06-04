package com.nosliw.core.application.entity.uitag;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.utils.HAPConstantShared;

public class HAPUITagDefinitionData extends HAPUITagDefinition{

	@HAPAttribute
	public static final String ATTRIBUTEFORDATA = "attributeForData";
	
	private String m_attributeForData;
	
	public HAPUITagDefinitionData() {
	}

	@Override
	public String getType() {  return HAPConstantShared.UITAG_TYPE_DATA;    }
	
	public String getAttributeForData() {     return this.m_attributeForData;      }
	public void setAttributeForData(String attributeForData) {    this.m_attributeForData = attributeForData;         }

}
