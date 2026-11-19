package com.nosliw.core.application.common.dataassociation;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.data.HAPUtilityData;

@HAPEntityWithAttribute
public class HAPEndPointInTunnelConstant extends HAPEndpointInTunnel{

	@HAPAttribute
	public static String VALUE = "value";

	private Object m_value;

	public HAPEndPointInTunnelConstant() {
		super(HAPConstantShared.TUNNELENDPOINT_TYPE_CONSTANT);
	}

	public HAPEndPointInTunnelConstant(Object value) {
		this();
		this.m_value = value;
	}
	
	public Object getValue() {   return this.m_value;   }
	public void setValue(Object value) {      this.m_value = value;       }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(VALUE, HAPManagerSerialize.getInstance().toStringValue(m_value, HAPSerializationFormat.JSON));
	}
}

@Component
class HAPEndPointInTunnelConstant_parser extends HAPEndpointInTunnel_parser{

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPEndPointInTunnelConstant out = new HAPEndPointInTunnelConstant();

		JSONObject jsonObj = (JSONObject)obj;
		
		Object valueObj = jsonObj.opt(HAPEndPointInTunnelConstant.VALUE);
		out.setValue(HAPUtilityData.buildDataWrapperFromObject(valueObj));
		return out;
	}

	@Override
	public String getSubName() {    return HAPConstantShared.TUNNELENDPOINT_TYPE_CONSTANT;   }
	
}
