package com.nosliw.core.service.staticresource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;

@HAPEntityWithAttribute
public class HAPStaticRequest extends HAPSerializableImp{

	@HAPAttribute
	public static final String STATICINFO = "staticInfo";
	
	private List<HAPStaticRequestInfo> m_staticInfo;
	
	public HAPStaticRequest() {
		this.m_staticInfo = new ArrayList<HAPStaticRequestInfo>();
	}
	
	public List<HAPStaticRequestInfo> getStaticInfos(){	return this.m_staticInfo;	}
	public void addStaticInfo(HAPStaticRequestInfo staticInfo) {    this.m_staticInfo.add(staticInfo);      }
	
	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		
		JSONArray statiInfoArray = jsonObj.getJSONArray(STATICINFO);
        for(int i=0; i<statiInfoArray.length(); i++) {
        	HAPStaticRequestInfoLibrary info = new HAPStaticRequestInfoLibrary();
        	info.buildObject(statiInfoArray.get(i), HAPSerializationFormat.JSON);
        	this.m_staticInfo.add(info);
        }
		
		return true;  
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(STATICINFO, HAPUtilityJson.buildJson(this.m_staticInfo, HAPSerializationFormat.JSON));
	}

}
