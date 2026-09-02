package com.nosliw.core.application.common.dataexpression;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.container.HAPContainer;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPParserEntity;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.data.expression.HAPExpressionData;

@HAPEntityWithAttribute
public class HAPContainerDataExpression extends HAPContainer<HAPItemInContainerDataExpression>{

	public static final String ENTITYNAMEFORSERIALIZE = "HAPContainerDataExpression";

	public HAPContainerDataExpression() {
	}

	public String addDataExpression(HAPExpressionData dataExpression) {
		HAPItemInContainerDataExpression item = new HAPItemInContainerDataExpression(dataExpression);
		return this.addItem(item);
	}
	
}

@Component
class HAPContainerDataExpression_parser implements HAPParserEntity{

	@Override
	public String getEntityType() {    return HAPContainerDataExpression.ENTITYNAMEFORSERIALIZE;   }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPContainerDataExpression out = new HAPContainerDataExpression();
		
		JSONObject jsonObj = (JSONObject)obj;
		
		JSONObject itemsJsonObj = jsonObj.optJSONObject(HAPContainer.ITEM);
		for(Object key : itemsJsonObj.keySet()) {
			String name = (String)key;
			out.addItem((HAPItemInContainerDataExpression)parseService.parseEntityJSONExplicit(itemsJsonObj.getJSONObject(name), HAPItemInContainerDataExpression.ENTITYNAMEFORSERIALIZE));
		}
		
		return out;
	}
	
}
