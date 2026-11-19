package com.nosliw.core.application.common.scriptexpressio;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.container.HAPContainer;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPParserEntity;
import com.nosliw.common.serialization.HAPServiceParseEntity;

public class HAPContainerScriptExpression extends HAPContainer<HAPItemInContainerScriptExpression>{

	public static final String ENTITYNAMEFORSERIALIZE = "HAPContainerScriptExpression";

	public String addScriptExpression(HAPExpressionScript scriptExpression) {
		HAPItemInContainerScriptExpression item = new HAPItemInContainerScriptExpression(scriptExpression);
		return this.addItem(item);
	}
}

@Component
class HAPContainerScriptExpression_parser implements HAPParserEntity{

	@Override
	public String getEntityType() {    return HAPContainerScriptExpression.ENTITYNAMEFORSERIALIZE;   }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPContainerScriptExpression out = new HAPContainerScriptExpression();
		
		JSONObject jsonObj = (JSONObject)obj;
		
		JSONObject itemsJsonObj = jsonObj.optJSONObject(HAPContainer.ITEM);
		for(Object key : itemsJsonObj.keySet()) {
			String name = (String)key;
			out.addItem((HAPItemInContainerScriptExpression)parseService.parseEntityJSONExplicit(itemsJsonObj.getJSONObject(name), HAPItemInContainerScriptExpression.ENTITYNAMEFORSERIALIZE));
		}
		
		return out;
	}
	
}
