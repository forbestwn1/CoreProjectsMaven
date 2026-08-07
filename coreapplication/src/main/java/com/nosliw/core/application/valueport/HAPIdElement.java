package com.nosliw.core.application.valueport;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.path.HAPPath;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityBasic;
import com.nosliw.common.utils.HAPUtilityNamingConversion;
import com.nosliw.common.variable.HAPIdVariable;
import com.nosliw.common.variable.HAPIdVariable_parser;

@HAPEntityWithAttribute
public class HAPIdElement extends HAPSerializableImp implements HAPIdVariable{

	@HAPAttribute
	public static final String ROOTELEMENTID = "rootElementId";
	
	@HAPAttribute
	public static final String ELEMENTPATH = "elementPath";
	
	@HAPAttribute
	public static final String KEY = "key";

	private HAPIdRootElement m_rootElementId;
	
	private HAPPath m_elementPath;

	public HAPIdElement() {}

	public HAPIdElement(HAPIdRootElement rootEleId, String elePath) {
		this.m_rootElementId = rootEleId;
		this.m_elementPath = new HAPPath(elePath);
	}
	
	@Override
	public String getType() {   return HAPConstantShared.VARIABLEID_TYPE_VALUEPORT;   }
	
	public HAPIdRootElement getRootElementId() {    return this.m_rootElementId;     }
	public void setRootElementId(HAPIdRootElement rootEleId) {     this.m_rootElementId = rootEleId;     }
	
	public HAPPath getElementPath() {    return this.m_elementPath;   }
	public void setElementPath(HAPPath path) {     this.m_elementPath = path;          }

	public String getKey() {
		return HAPUtilityNamingConversion.cascadeElements(new String[] {this.m_elementPath.toString(), this.getRootElementId().getKey()}, HAPConstantShared.SEPERATOR_LEVEL1); 
	}
	
	@Override
	public int hashCode() {
		return this.getKey().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof HAPIdElement) {
			HAPIdElement varId = (HAPIdElement)obj;
			if(this.m_rootElementId.equals(varId.m_rootElementId)) {
				if(HAPUtilityBasic.isEquals(this.m_elementPath, varId.m_elementPath)) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(TYPE, this.getType());
		jsonMap.put(ROOTELEMENTID, this.m_rootElementId.toStringValue(HAPSerializationFormat.JSON));
		jsonMap.put(ELEMENTPATH, this.m_elementPath.getPath());
		jsonMap.put(KEY, this.getKey());
	}

}

@Component
class HAPIdElement_parser extends HAPIdVariable_parser{

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPIdElement out = new HAPIdElement();
		JSONObject jsonObj = (JSONObject)obj;
		
		out.setElementPath(new HAPPath((String)jsonObj.opt(HAPIdElement.ELEMENTPATH)));
		
		JSONObject rootEleIdJsonObj = jsonObj.optJSONObject(HAPIdElement.ROOTELEMENTID);
		if(rootEleIdJsonObj!=null) {
			HAPIdRootElement rootEleId = new HAPIdRootElement();
			rootEleId.buildObject(rootEleIdJsonObj, HAPSerializationFormat.JSON);
			out.setRootElementId(rootEleId);
		}
		
		return out;
	}

	@Override
	public String getSubName() {   return HAPConstantShared.VARIABLEID_TYPE_VALUEPORT;   }
	
}
