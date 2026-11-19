package com.nosliw.core.application.common.datasource;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.data.HAPData;
import com.nosliw.core.data.HAPUtilityData;

public class HAPAIPRequestExectueDataSource extends HAPSerializableImp{

	@HAPAttribute
	public static String QUERY = "query";

	@HAPAttribute
	public static String PARM = "parm";

	private HAPQueryService m_serviceQuery;
	
	private Map<String, HAPData> m_parms;
	
	public HAPAIPRequestExectueDataSource(HAPQueryService serviceQuery, Map<String, HAPData> parms) {
		this();
		this.m_serviceQuery = serviceQuery;
		this.m_parms.putAll(parms);
	}
	
	public HAPAIPRequestExectueDataSource() {
		this.m_parms = new LinkedHashMap<String, HAPData>();
	}
	
	public HAPQueryService getServiceQuery() {      return this.m_serviceQuery;       }
	
	public Map<String, HAPData> getParms(){      return this.m_parms;       }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(QUERY, this.m_serviceQuery.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(PARM, HAPManagerSerialize.getInstance().toStringValue(this.m_parms, HAPSerializationFormat.JSON));
	}

	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		
		JSONObject queryJsonObj = jsonObj.optJSONObject(QUERY);
		if(queryJsonObj!=null) {
			this.m_serviceQuery = new HAPQueryService();
			this.m_serviceQuery.buildObject(queryJsonObj, HAPSerializationFormat.JSON);
		}
		
		JSONObject parmJsonObj = jsonObj.optJSONObject(PARM);
		if(parmJsonObj!=null) {
			for(Object key : parmJsonObj.keySet()) {
				String name = (String)key;
				this.m_parms.put(name, HAPUtilityData.buildDataWrapperFromObject(parmJsonObj.get(name)));
			}
		}
		
		return true;  
	}

	
}
