package com.nosliw.core.application.common.dataassociation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;

@HAPEntityWithAttribute
public class HAPDataAssociationMapping extends HAPDataAssociation{

	@HAPAttribute
	public static String TUNNEL = "tunnel";

	//path mapping for relative node (output path in context - input path in context) during runtime
	private List<HAPTunnel> m_tunnel;
	
	private Set<String> m_inputDependency;

	public HAPDataAssociationMapping() {
		super(HAPConstantShared.DATAASSOCIATION_TYPE_MAPPING);
		this.m_tunnel = new ArrayList<HAPTunnel>();
		this.m_inputDependency = new HashSet<String>();
	}
	
	public Set<String> getInputDependency(){   return this.m_inputDependency;    }
	
	public void addTunnel(HAPTunnel mappingPath) {    this.m_tunnel.add(mappingPath);     }
	public void addRelativePathMappings(List<HAPTunnel> mappingPaths) {    this.m_tunnel.addAll(mappingPaths);    }
	public List<HAPTunnel> getTunnels() {  return this.m_tunnel;  }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(TUNNEL, HAPManagerSerialize.getInstance().toStringValue(this.m_tunnel, HAPSerializationFormat.JSON));
	}

	@Override
	protected boolean buildObjectByJson(Object json){
		return true;  
	}
	
}

@Component
class HAPDataAssociationMapping_parser extends HAPDataAssociation_parser{

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPDataAssociationMapping out = new HAPDataAssociationMapping();

		JSONObject jsonObj = (JSONObject)obj;
		
		JSONArray tunnelJsonArray = jsonObj.optJSONArray(HAPDataAssociationMapping.TUNNEL);
		for(int i=0; i<tunnelJsonArray.length(); i++) {
			out.addTunnel((HAPTunnel)parseService.parseEntityJSONExplicit(tunnelJsonArray.getJSONObject(i), HAPTunnel.class.getName()));
		}
		
		return out;
	}

	@Override
	public String getSubName() {   return HAPConstantShared.DATAASSOCIATION_TYPE_MAPPING;   }
	
}