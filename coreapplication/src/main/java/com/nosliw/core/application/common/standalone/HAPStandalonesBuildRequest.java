package com.nosliw.core.application.common.standalone;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;

@HAPEntityWithAttribute
public class HAPStandalonesBuildRequest  extends HAPSerializableImp{

	@HAPAttribute
	public static final String ITEM = "item";

	private List<HAPStandaloneRequest> m_Items;
	
	public HAPStandalonesBuildRequest() {
		this.m_Items = new ArrayList<HAPStandaloneRequest>();
	}
	
	public List<HAPStandaloneRequest> getItems(){      return this.m_Items;    }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(ITEM, HAPManagerSerialize.getInstance().toStringValue(this.m_Items, HAPSerializationFormat.JSON));
	}
	
	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		
		JSONArray providerRequestJsonArray = jsonObj.optJSONArray(ITEM);
		if(providerRequestJsonArray!=null) {
			for(int i=0; i<providerRequestJsonArray.length(); i++) {
				HAPStandaloneRequest item = new HAPStandaloneRequest();
				item.buildObject(providerRequestJsonArray.get(i), HAPSerializationFormat.JSON);
				this.m_Items.add(item);
			}
		}
		return true;  
	}

}