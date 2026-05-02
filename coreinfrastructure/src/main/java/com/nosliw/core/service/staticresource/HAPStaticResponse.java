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
public class HAPStaticResponse extends HAPSerializableImp{

	@HAPAttribute
	public static final String ITEM = "item";

	private List<HAPStaticResponseInfo> m_items;
	
	public HAPStaticResponse() {
		this.m_items = new ArrayList<HAPStaticResponseInfo>();
	}
	
	public void addItem(HAPStaticResponseInfo item) {
		this.m_items.add(item);
	}
	
	public List<HAPStaticResponseInfo> getItems(){
		return this.m_items;
	}
	
	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		JSONArray uriArray = jsonObj.getJSONArray(ITEM);
        for(int i=0; i<uriArray.length(); i++) {
    		HAPStaticResponseInfo item = new HAPStaticResponseInfo();
    		item.buildObject(uriArray.getJSONObject(i), HAPSerializationFormat.JSON);
			this.m_items.add(item);
        }
		return true;  
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(ITEM, HAPUtilityJson.buildJson(m_items, HAPSerializationFormat.JSON));
	}
	
}
