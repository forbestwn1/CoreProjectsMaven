package com.nosliw.core.application.valueport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.common.utils.HAPConstantShared;

@HAPEntityWithAttribute
public class HAPValuePort extends HAPEntityInfoImp{

	@HAPAttribute
	public static String VALUESTRUCTURE = "valueStructure";

	@HAPAttribute
	public static String VALUESTRUCTUREINFO = "valueStructureInfo";
	
	@HAPAttribute
	public static String TYPE = "type";

	@HAPAttribute
	public static String IODIRECTION = "ioDirection";

	private List<HAPInfoValueStructure> m_valueStructures;

	private String m_type;
	
	private String m_ioDirection; 

	public HAPValuePort() {
		this.m_valueStructures = new ArrayList<HAPInfoValueStructure>();
	}
	
	public HAPValuePort(String type, String ioDirection) {
		this();
		this.m_type = type;
		this.m_ioDirection = ioDirection;
	}
	
	public List<String> getValueStructureIds(){	return this.m_valueStructures.stream().map(HAPInfoValueStructure::getValueStructureId).collect(Collectors.toList());	}

	public void addValueStructureId(String id) {    this.addValueStructureInfo(new HAPInfoValueStructure(id, HAPConstantShared.VALUESTRUCTURE_PRIORITY_DEFINED));    }
	public void setValueStructuredSorted(List<HAPInfoValueStructure> valueStructureInfos) {		
		this.m_valueStructures.addAll(valueStructureInfos);
	}
	public List<HAPInfoValueStructure> getValueStructureInfos(){     return this.m_valueStructures;       }
	
	public void addValueStructureInfo(HAPInfoValueStructure valueStructureInfo) {
		this.m_valueStructures.add(valueStructureInfo);
		this.m_valueStructures.sort((v1, v2)-> HAPUtilityValueStructure.sortPriority(v1.getPriority(), v2.getPriority()));
		Collections.reverse(m_valueStructures);
	}

	public String getType() {     return this.m_type;    }

	public String getIODirection() {     return this.m_ioDirection;     }
	public void setIODirection(String ioDirection) {   this.m_ioDirection = ioDirection;   }

	public void cleanValueStucture(Set<String> valueStrucutreIds) {
		this.m_valueStructures =  this.m_valueStructures.stream().filter(vsInfo->!valueStrucutreIds.contains(vsInfo.getValueStructureId())).collect(Collectors.toList());
	}

	public boolean isEmpty() {
		return this.m_valueStructures.isEmpty();
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(TYPE, this.getType());
		jsonMap.put(IODIRECTION, this.getIODirection());
		jsonMap.put(VALUESTRUCTURE, HAPUtilityJson.buildArrayJson(this.getValueStructureIds().toArray(new String[0])));
		jsonMap.put(VALUESTRUCTUREINFO, HAPManagerSerialize.getInstance().toStringValue(this.m_valueStructures, HAPSerializationFormat.JSON));
	}

	@Override
	protected boolean buildObjectByJson(Object obj){
		JSONObject jsonObj = (JSONObject)obj;
		this.buildEntityInfoByJson(jsonObj);
		this.m_type = (String)jsonObj.opt(TYPE);
		this.m_ioDirection = (String)jsonObj.opt(IODIRECTION);
		
		JSONArray vsInfosJsonArray = jsonObj.optJSONArray(VALUESTRUCTUREINFO);
		for(int i=0; i<vsInfosJsonArray.length(); i++) {
			HAPInfoValueStructure vsInfo = new HAPInfoValueStructure();
			vsInfo.buildObject(vsInfosJsonArray.getJSONObject(i), HAPSerializationFormat.JSON);
			this.addValueStructureInfo(vsInfo);
		}
		
		return true;  
	}
	
}
