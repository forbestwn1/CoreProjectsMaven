package com.nosliw.core.application.common.dataexpression;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.container.HAPItemWrapper;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPParserEntity;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.data.expression.HAPExpressionData;
import com.nosliw.core.data.expression.imp.basic.HAPBasicExpressionData;

@HAPEntityWithAttribute
public class HAPItemInContainerDataExpression extends HAPItemWrapper implements HAPEntityParsable{

	public static final String ENTITYNAMEFORSERIALIZE = "HAPItemInContainerDataExpression";

	@HAPAttribute
	public static String DATAEXPRESSION = "dataExpression";

	public HAPItemInContainerDataExpression() {}
	
	public HAPItemInContainerDataExpression(HAPExpressionData dataExpression) {
		super(dataExpression);
	}
	
	public HAPExpressionData getDataExpression() {    return (HAPExpressionData)this.getValue();     }
	public void setDataExpression(HAPExpressionData expression) {    this.setValue(expression);      }
	
	@Override
	protected void buildJSJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJSJsonMap(jsonMap, typeJsonMap);
	}
}

@Component
class HAPItemInContainerDataExpression_parser implements HAPParserEntity{

	@Override
	public String getEntityType() {    return HAPItemInContainerDataExpression.ENTITYNAMEFORSERIALIZE;   }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPItemInContainerDataExpression out = new HAPItemInContainerDataExpression();
		JSONObject jsonObj = (JSONObject)obj;
		out.buildEntityInfoByJson(jsonObj);
		out.setDataExpression((HAPExpressionData)parseService.parseEntityJSONExplicit(jsonObj.optJSONObject(HAPItemWrapper.VALUE), HAPBasicExpressionData.ENTITYNAMEFORSERIALIZE));
		
		return out;
	}
	
}
