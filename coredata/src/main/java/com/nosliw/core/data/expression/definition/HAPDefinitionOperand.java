package com.nosliw.core.data.expression.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPParserEntityImpWithDomain;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPServiceParseEntity;

public abstract class HAPDefinitionOperand extends HAPSerializableImp implements HAPEntityParsable{

	public static final String PARSABLEENTITYDOMAIN = "core.dataexpression.definition";
	
	public static String TYPE = "type";
	
	private String m_type;
	
	public HAPDefinitionOperand(String type) {
		this.m_type = type;
	}
	
	public String getType(){ return this.m_type;  }

	public List<HAPDefinitionOperand> getChildren(){   return new ArrayList<HAPDefinitionOperand>();    }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(TYPE, this.getType());
	}
	
	public static HAPDefinitionOperand parseOperandDefinition(JSONObject jsonObj, HAPServiceParseEntity parseService) {
		return (HAPDefinitionOperand)parseService.parseEntityJSONImplicitAttribute(jsonObj, TYPE, PARSABLEENTITYDOMAIN);
	}
}

abstract class HAPDefinitionOperand__HAPEntityParsable extends HAPParserEntityImpWithDomain{

	@Override
	public String getDomain() {   return HAPDefinitionOperand.PARSABLEENTITYDOMAIN;   }

	protected void parseToEntity(JSONObject jsonObj, HAPDefinitionOperand operandDefinition, HAPServiceParseEntity parseService) {
	}
	
}
