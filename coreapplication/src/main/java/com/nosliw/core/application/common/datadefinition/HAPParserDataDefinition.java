package com.nosliw.core.application.common.datadefinition;

import org.json.JSONObject;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.data.criteria.HAPParserCriteriaImp;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPParserDataDefinition {

	public static HAPDataDefinition parseDataDefinition(JSONObject jsonObj, HAPServiceParseEntity entityParseService) {
		return (HAPDataDefinition)entityParseService.parseEntityJSONImplicitAttribute(jsonObj, HAPDataDefinition.TYPE, HAPDataDefinition.PARSABLEENTITYDOMAIN);
	}
	
	public static HAPDataDefinitionReadonly parseDataDefinitionReadonly(Object obj, HAPServiceParseEntity entityParseService) {
		HAPDataDefinitionReadonly out = new HAPDataDefinitionReadonly();
		if(obj instanceof String) {
			parseStringToDataDefinition((String)obj, out);
		}
		else {
			out = (HAPDataDefinitionReadonly)entityParseService.parseEntityJSONExplicit((JSONObject)obj, HAPConstantShared.DATADEFINITION_TYPE_READONLY, HAPDataDefinition.PARSABLEENTITYDOMAIN);
		}
		return out;
	}
	
	public static HAPDataDefinitionWritable parseDataDefinitionWritable(Object obj, HAPServiceParseEntity entityParseService) {
		HAPDataDefinitionWritable out = new HAPDataDefinitionWritable();
		if(obj instanceof String) {
			parseStringToDataDefinition((String)obj, out);
		}
		else {
			out = (HAPDataDefinitionWritable)entityParseService.parseEntityJSONExplicit((JSONObject)obj, HAPConstantShared.DATADEFINITION_TYPE_WRITABLE, HAPDataDefinition.PARSABLEENTITYDOMAIN);
		}
		return out;
	}

	public static HAPDataDefinitionWritableWithInit parseDataDefinitionWritableWithInit(Object obj, HAPServiceParseEntity entityParseService) {
		HAPDataDefinitionWritableWithInit out = new HAPDataDefinitionWritableWithInit();
		if(obj instanceof String) {
			parseStringToDataDefinition((String)obj, out);
		}
		else if(obj instanceof JSONObject){
			out = (HAPDataDefinitionWritableWithInit)entityParseService.parseEntityJSONExplicit((JSONObject)obj, HAPConstantShared.DATADEFINITION_TYPE_WRITEABLEWITHINIT, HAPDataDefinition.PARSABLEENTITYDOMAIN);
		}
		return out;
	}
	
	private static void parseStringToDataDefinition(String str, HAPDataDefinition dataDefinition) {
		dataDefinition.setCriteria(HAPParserCriteriaImp.getInstance().parseCriteria(str));
	}
}
