package com.nosliw.core.resource;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializable;
import com.nosliw.common.serialization.HAPSerializationFormat;

@HAPEntityWithAttribute
public class HAPResourceDataImpTransient extends HAPResourceDataImp{

	@HAPAttribute
	public static String VALUE = "value";
	
	private HAPSerializable m_value;
	
	public HAPResourceDataImpTransient(HAPSerializable value) {
		this.m_value = value;
	}
	
	public HAPSerializable getValue() {      return this.m_value;          }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(VALUE, this.m_value.toStringValue(HAPSerializationFormat.JSON));
	}
	
	@Override
	protected void buildJSJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(VALUE, this.m_value.toStringValue(HAPSerializationFormat.JAVASCRIPT));
	}
	
}
