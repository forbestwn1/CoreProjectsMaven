package com.nosliw.core.application.common.scriptexpressio;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.container.HAPItemWrapper;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPParserEntity;
import com.nosliw.common.serialization.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPItemInContainerScriptExpression extends HAPItemWrapper implements HAPEntityParsable{

	@HAPAttribute
	public static String SCRIPTEXPRESSION = "scriptExpression";

	public HAPItemInContainerScriptExpression() {}
	
	public HAPItemInContainerScriptExpression(HAPExpressionScript scriptExpression) {
		super(scriptExpression);
	}
	
	public HAPExpressionScript getScriptExpression() {    return (HAPExpressionScript)this.getValue();     }
	public void setScriptExpression(HAPExpressionScript expression) {    this.setValue(expression);;      }
	
	@Override
	protected void buildJSJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJSJsonMap(jsonMap, typeJsonMap);
	}
}

@Component
class HAPItemInContainerScriptExpression_parser implements HAPParserEntity{

	@Override
	public String getEntityType() {    return HAPItemInContainerScriptExpression.class.getName();   }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPItemInContainerScriptExpression out = new HAPItemInContainerScriptExpression();
		
		JSONObject jsonObj = (JSONObject)obj;
		out.buildEntityInfoByJson(jsonObj);
		out.setScriptExpression((HAPExpressionScript)parseService.parseEntityJSONExplicit(jsonObj.optJSONObject(HAPItemWrapper.VALUE), HAPExpressionScriptImp.class.getName()));
		
		return out;
	}
	
}
