package com.nosliw.core.application.entity.datarule;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;

@HAPEntityWithAttribute
public class HAPDataRuleImplementationLocal extends HAPSerializableImp implements HAPDataRuleImplementation{

	@HAPAttribute
    public static final String PATHID = "pathId";
	
	private String m_pathId;
	
	public HAPDataRuleImplementationLocal() {}
	
	public HAPDataRuleImplementationLocal(String pathId) {
		this.m_pathId = pathId;
	}
	
	@Override
	public String getImmplementationType() {		return HAPConstantShared.DATARULE_IMPLEMENTATION_LOCAL;	}
	
	public void setPathId(String pathId) {     this.m_pathId = pathId;      }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(TYPE, this.getImmplementationType());
        jsonMap.put(PATHID, this.m_pathId);
    }
}

@Component
class HAPDataRuleImplementationLocal_parser extends HAPDataRuleImplementation_parser{

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPDataRuleImplementationLocal out = new HAPDataRuleImplementationLocal();
		
		out.setPathId((String)((JSONObject)obj).opt(HAPDataRuleImplementationLocal.PATHID));
		
		return out;
	}

	@Override
	public String getSubName() {    return HAPConstantShared.DATARULE_IMPLEMENTATION_LOCAL;   }
	
}
