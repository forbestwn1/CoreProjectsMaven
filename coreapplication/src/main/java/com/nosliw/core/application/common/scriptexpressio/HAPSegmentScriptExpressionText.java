package com.nosliw.core.application.common.scriptexpressio;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;

public class HAPSegmentScriptExpressionText extends HAPSegmentScriptExpression{

	@HAPAttribute
	public static String CONTENT = "content";

	private String m_content;

	public HAPSegmentScriptExpressionText() {}

	public HAPSegmentScriptExpressionText(String id, String content) {
		super(id);
		this.m_content = content;
	}
	
	@Override
	public String getType() {  return HAPConstantShared.EXPRESSION_SEG_TYPE_TEXT;  }

	public String getContent() {    return this.m_content;     }
	public void setContent(String content) {      this.m_content = content;        }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(CONTENT, this.getContent());
	}
}

@Component
class HAPSegmentScriptExpressionText_parser extends HAPSegmentScriptExpression_parser{

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPSegmentScriptExpressionText out = new HAPSegmentScriptExpressionText();
		JSONObject jsonObj = (JSONObject)obj;
		this.parseToSegmentJson(jsonObj, out, parseService);
		out.setContent((String)jsonObj.opt(HAPSegmentScriptExpressionText.CONTENT));
		return out;
	}

	@Override
	public String getSubName() {   return HAPConstantShared.EXPRESSION_SEG_TYPE_TEXT;   }
	
}