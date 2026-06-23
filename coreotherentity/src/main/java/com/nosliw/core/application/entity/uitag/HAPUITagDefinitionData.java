package com.nosliw.core.application.entity.uitag;

import java.util.ArrayList;
import java.util.List;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.utils.HAPConstantShared;

public class HAPUITagDefinitionData extends HAPUITagDefinition{

	@HAPAttribute
	public static final String ATTRIBUTEFORDATA = "attributeForData";
	
	private List<String> m_attributeForData;
	
	public HAPUITagDefinitionData() {
		this.m_attributeForData = new ArrayList<String>();
	}

	@Override
	public String getType() {  return HAPConstantShared.UITAG_TYPE_DATA;    }
	
	public List<String> getAttributeForData() {     return this.m_attributeForData;      }
	public void addAttributeForData(String attributeForData) {    this.m_attributeForData.add(attributeForData);         }

}
