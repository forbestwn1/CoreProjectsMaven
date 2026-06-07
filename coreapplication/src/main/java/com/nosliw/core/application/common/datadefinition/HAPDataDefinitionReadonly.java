package com.nosliw.core.application.common.datadefinition;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPDataDefinitionReadonly extends HAPDataDefinition{

	public HAPDataDefinitionReadonly(HAPDataDefinition dataDefinition) {
		this();
		dataDefinition.cloneToDataDefinition(this);
	}
	
	public HAPDataDefinitionReadonly() {
		super(HAPConstantShared.DATADEFINITION_TYPE_READONLY);
	}
	
	public HAPDataDefinitionReadonly cloneDataDefinitionWritable() {
		HAPDataDefinitionReadonly out = new HAPDataDefinitionReadonly();
		this.cloneToDataDefinition(out);
		return out;
	}
	
}

@Component
class HAPDataDefinitionReadonly__HAPEntityParsable extends HAPDataDefinition__HAPEntityParsable{

	@Override
	public String getSubName() {     return HAPConstantShared.DATADEFINITION_TYPE_READONLY;    }
	
	protected void parseToEntity(JSONObject jsonObj, HAPDataDefinitionReadonly dataDefinition, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, dataDefinition, parseService);
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPDataDefinitionReadonly out = new HAPDataDefinitionReadonly();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}
