package com.nosliw.core.application.division.manual.common.valuecontext;

import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.HAPDomainValueStructure;

public abstract class HAPManualPartInValueContext extends HAPEntityInfoImp{
	
	public static final String TYPE = "type";

	public static final String PARTINFO = "partInfo";
	
	private HAPManualInfoPartInValueContext m_partInfo;

	public HAPManualPartInValueContext() {}

	public HAPManualPartInValueContext(HAPManualInfoPartInValueContext partInfo) {
		this.m_partInfo = processPartInfo(partInfo);
	}

	public HAPManualInfoPartInValueContext getPartInfo() {    return this.m_partInfo;    }
	
	abstract public String getPartType();
	
//	abstract public HAPManualPartInValueContext inheritValueContextPart(HAPDomainValueStructure valueStructureDomain, String mode, String[] groupTypeCandidates);
	abstract public HAPManualPartInValueContext cloneValueContextPart();
	
	abstract public void cleanValueStucture(Set<String> valueStrucutreIds);
	
	abstract public boolean isEmptyOfValueStructure(HAPDomainValueStructure valueStructureDomain);

	abstract public boolean isEmpty();
	
	public static HAPManualPartInValueContext parse(JSONObject jsonObj) {
		HAPManualPartInValueContext out = null;
		String type = jsonObj.getString(TYPE);
		if(type.equals(HAPConstantShared.VALUESTRUCTUREPART_TYPE_SIMPLE)) {
			out = new HAPManualPartInValueContextSimple();
		}
		else if(type.equals(HAPConstantShared.VALUESTRUCTUREPART_TYPE_GROUP_WITHENTITY)) {
			out = new HAPManualPartInValueContextGroupWithEntity();
		}
		out.buildObject(jsonObj, HAPSerializationFormat.JSON);
		return out;
	}
	
	public void cloneToPartValueContext(HAPManualPartInValueContext part) {
		this.cloneToEntityInfo(part);
	}
	
	private HAPManualInfoPartInValueContext processPartInfo(HAPManualInfoPartInValueContext partInfo) {
		HAPManualInfoPartInValueContext out = partInfo;
		if(out==null) {
			out = HAPManualUtilityValueContext.createPartInfoDefault();
		}  
		return out;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(TYPE, this.getPartType());
		jsonMap.put(PARTINFO, this.m_partInfo.toStringValue(HAPSerializationFormat.JSON));
	}

	@Override
	protected boolean buildObjectByJson(Object json){
		JSONObject jsonObj = (JSONObject)json;
		
		JSONObject partInfoJsonObj = jsonObj.optJSONObject(PARTINFO);
		if(partInfoJsonObj!=null) {
			this.m_partInfo = new HAPManualInfoPartInValueContext();
			this.m_partInfo.buildObject(partInfoJsonObj, HAPSerializationFormat.JSON);
		}
		
		return true;  
	}
	
}
