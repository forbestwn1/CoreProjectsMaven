package com.nosliw.core.service.staticresource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;

@HAPEntityWithAttribute
public class HAPStaticRequest extends HAPSerializableImp{

	@HAPAttribute
	public static final String REQUESTID = "requestId";

	@HAPAttribute
	public static final String ISSCRIPTFILECONSOLIDATED = "isScriptFileConsolidated";

	@HAPAttribute
	public static final String STATICINFO = "staticInfo";

	private String m_requestId;
	
	private Boolean m_isScriptFileConsolidated = false;
	
	private List<HAPStaticRequestInfo> m_staticInfo;
	
	public HAPStaticRequest() {
		this.m_staticInfo = new ArrayList<HAPStaticRequestInfo>();
	}
	
	public String getRequestId() {     return this.m_requestId;       }
	public void setRequestId(String id) {     this.m_requestId = id;        }
	
	public Boolean isScriptFileConsolidated() {		return m_isScriptFileConsolidated;	}

	public void isScriptFileConsolidated(Boolean isScriptFileConsolidated) {		this.m_isScriptFileConsolidated = isScriptFileConsolidated;	}

	public List<HAPStaticRequestInfo> getStaticInfos(){	return this.m_staticInfo;	}
	public void addStaticInfo(HAPStaticRequestInfo staticInfo) {    this.m_staticInfo.add(staticInfo);      }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(REQUESTID, m_requestId);
		jsonMap.put(STATICINFO, HAPUtilityJson.buildJson(this.m_staticInfo, HAPSerializationFormat.JSON));
		jsonMap.put(ISSCRIPTFILECONSOLIDATED, this.m_isScriptFileConsolidated+"");
		typeJsonMap.put(ISSCRIPTFILECONSOLIDATED, Boolean.class);
	}

}
