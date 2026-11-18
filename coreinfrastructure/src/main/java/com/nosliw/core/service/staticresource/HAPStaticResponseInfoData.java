package com.nosliw.core.service.staticresource;

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

@HAPEntityWithAttribute
public class HAPStaticResponseInfoData extends HAPStaticResponseInfo{

	@HAPAttribute
	public static final String DATA = "data";

	private Object m_data;
	
	public HAPStaticResponseInfoData() {
		super(HAPConstantShared.STATIC_RESPONSE_TYPE_DATA);
	}
	
	public HAPStaticResponseInfoData(Object data) {
		this();
		this.m_data = data;
	}
	
	public Object getData() {       return this.m_data;      }
	public void setData(Object data) {      this.m_data = data;      }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(DATA, HAPManagerSerialize.getInstance().toStringValue(m_data, HAPSerializationFormat.JSON));
	}
	
}

@Component
class HAPStaticResponseInfoData__HAPEntityParsable extends HAPStaticResponseInfo__HAPEntityParsable{

	@Override
	public String getSubName() {     return HAPConstantShared.STATIC_RESPONSE_TYPE_DATA;    }
	
	protected void parseToEntity(JSONObject jsonObj, HAPStaticResponseInfoData staticResponseInfoData, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, staticResponseInfoData, parseService);
		try {
			staticResponseInfoData.setData(HAPStaticResponseInfoData.DATA);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStaticResponseInfoData out = new HAPStaticResponseInfoData();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}
