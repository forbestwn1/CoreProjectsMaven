package com.nosliw.api.statichost;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;

@HAPEntityWithAttribute
public class HAPStaticRequest extends HAPSerializableImp{

	@HAPAttribute
	public static final String STATICINFO = "staticInfo";
	
	private List<HAPStaticInfo> m_staticInfo;
	
	public HAPStaticRequest() {
		this.m_staticInfo = new ArrayList<HAPStaticInfo>();
	}
	
	public List<HAPStaticInfo> getStaticInfos(){
		return this.m_staticInfo;
	}
	
	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		
		JSONArray statiInfoArray = jsonObj.getJSONArray(STATICINFO);
        for(int i=0; i<statiInfoArray.length(); i++) {
        	HAPStaticInfo info = new HAPStaticInfo();
        	info.buildObject(statiInfoArray.get(i), HAPSerializationFormat.JSON);
        	this.m_staticInfo.add(info);
        }
		
		return true;  
	}
}
