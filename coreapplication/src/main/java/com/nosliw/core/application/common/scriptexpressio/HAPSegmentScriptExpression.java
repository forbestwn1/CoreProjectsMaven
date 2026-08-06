package com.nosliw.core.application.common.scriptexpressio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPParserEntityImpWithDomain;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.resource.infrastructure.HAPExecutableImp;

@HAPEntityWithAttribute
abstract public class HAPSegmentScriptExpression extends HAPExecutableImp implements HAPEntityParsable{
	
	public static final String DOMAIN_PARSER = "scriptexpression.segment";
	
	@HAPAttribute
	public static String TYPE = "type";

	@HAPAttribute
	public static String ID = "id";

	private String m_id;

	public HAPSegmentScriptExpression() {} 

	public HAPSegmentScriptExpression(String id) {
		this.m_id = id;
	}
	
	public abstract String getType();

	public String getId() {    return this.m_id;   }
	public void setId(String id) {    this.m_id = id;      }

	public List<HAPSegmentScriptExpression> getChildren(){     return new ArrayList<HAPSegmentScriptExpression>();      }
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(TYPE, this.getType());
		jsonMap.put(ID, m_id);
	}
	
	public static HAPSegmentScriptExpression parsScriptExpressionSegment(JSONObject jsonObj, HAPServiceParseEntity parseService) {
		return (HAPSegmentScriptExpression)parseService.parseEntityJSONImplicitAttribute(jsonObj, HAPSegmentScriptExpression.TYPE, HAPSegmentScriptExpression.DOMAIN_PARSER);
	}
}


abstract class HAPSegmentScriptExpression_parser extends HAPParserEntityImpWithDomain{

	@Override
	public String getDomain() {    return HAPSegmentScriptExpression.DOMAIN_PARSER;   }

	public void parseToSegmentJson(JSONObject jsonObj, HAPSegmentScriptExpression segment, HAPServiceParseEntity parseService) {
		segment.setId((String)jsonObj.get(HAPSegmentScriptExpression.ID));
	}
}
