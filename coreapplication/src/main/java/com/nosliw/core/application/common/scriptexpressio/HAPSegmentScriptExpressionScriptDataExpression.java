package com.nosliw.core.application.common.scriptexpressio;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;

public class HAPSegmentScriptExpressionScriptDataExpression extends HAPSegmentScriptExpressionScript{

	@HAPAttribute
	public static String DATAEXPRESSIONID = "dataExpressionId";

	private String m_dataExpressionId;

	public HAPSegmentScriptExpressionScriptDataExpression() {}
	
	public HAPSegmentScriptExpressionScriptDataExpression(String id, String dataExpressionId) {
		super(id);
		this.m_dataExpressionId = dataExpressionId;
	}
	
	@Override
	public String getType() {  return HAPConstantShared.EXPRESSION_SEG_TYPE_DATAEXPRESSION;  }

	public String getDataExpressionId() {    return this.m_dataExpressionId;     }
	public void setDataExpressionId(String dataExpressionId) {       this.m_dataExpressionId = dataExpressionId;          }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(DATAEXPRESSIONID, this.getDataExpressionId());
	}

}

@Component
class HAPSegmentScriptExpressionScriptDataExpression_parser extends HAPSegmentScriptExpression_parser{

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPSegmentScriptExpressionScriptDataExpression out = new HAPSegmentScriptExpressionScriptDataExpression();
		JSONObject jsonObj = (JSONObject)obj;
		
		this.parseToSegmentJson(jsonObj, out, parseService);
		
		out.setDataExpressionId((String)jsonObj.opt(HAPSegmentScriptExpressionScriptDataExpression.DATAEXPRESSIONID));
		
		return out;
	}

	@Override
	public String getSubName() {   return HAPConstantShared.EXPRESSION_SEG_TYPE_DATAEXPRESSION;   }
	
}
