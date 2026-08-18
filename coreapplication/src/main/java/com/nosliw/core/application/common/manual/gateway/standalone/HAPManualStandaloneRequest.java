package com.nosliw.core.application.common.manual.gateway.standalone;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;

@HAPEntityWithAttribute
public class HAPManualStandaloneRequest extends HAPSerializableImp{

	@HAPAttribute
	public static final String PROVIDERNAME = "providerName";

	@HAPAttribute
	public static final String PROVIDERREQUEST = "providerRequest";

	private String m_providerName;
	
	private HAPManualStandaloneProviderRequest m_providerRequest;
	
	public String getProviderName() {     return this.m_providerName;      }

	public HAPManualStandaloneProviderRequest getProviderRequest() {     return this.m_providerRequest;        }


	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(PROVIDERNAME, this.m_providerName);
		jsonMap.put(PROVIDERREQUEST, HAPManagerSerialize.getInstance().toStringValue(this.m_providerRequest, HAPSerializationFormat.JSON));
	}
	
	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		this.m_providerName = (String)jsonObj.opt(PROVIDERNAME);
		
		JSONObject providerRequestJsonObj = jsonObj.optJSONObject(PROVIDERREQUEST);
		if(providerRequestJsonObj!=null) {
			this.m_providerRequest = new HAPManualStandaloneProviderRequest();
			this.m_providerRequest.buildObject(providerRequestJsonObj, HAPSerializationFormat.JSON);
		}
		return true;  
	}
}
