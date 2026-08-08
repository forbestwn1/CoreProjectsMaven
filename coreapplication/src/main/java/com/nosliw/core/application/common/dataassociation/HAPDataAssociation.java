package com.nosliw.core.application.common.dataassociation;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPParserEntityImpWithDomain;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPDataAssociation extends HAPSerializableImp implements HAPEntityParsable{

	public static final String DOMAIN_PARSER = "dataassociation";
	
	@HAPAttribute
	public static String TYPE = "type";

	private String m_dataAssociationType;
	
	public HAPDataAssociation(String dataAssociationType) {
		this.m_dataAssociationType = dataAssociationType;
	}
	
	public String getDataAssociationType() {   return this.m_dataAssociationType;     }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(TYPE, this.getDataAssociationType());
	}
	
	public static HAPDataAssociation parseDataAssociation(Object obj, HAPServiceParseEntity parseService) {
		return (HAPDataAssociation)parseService.parseEntityJSONImplicitAttribute((JSONObject)obj, TYPE, DOMAIN_PARSER);
	}
}

abstract class HAPDataAssociation_parser extends HAPParserEntityImpWithDomain{

	@Override
	public String getDomain() {   return HAPDataAssociation.DOMAIN_PARSER;   }
	
}
