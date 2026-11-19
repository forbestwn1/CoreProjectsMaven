package com.nosliw.core.application.common.scriptexpressio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.common.utils.HAPConstantShared;

public class HAPSegmentScriptExpressionScriptComplex extends HAPSegmentScriptExpressionScript{

	@HAPAttribute
	public static String SEGMENT = "segment";
	
	private List<HAPSegmentScriptExpressionScript> m_children;

	public HAPSegmentScriptExpressionScriptComplex() {
		this.m_children = new ArrayList<HAPSegmentScriptExpressionScript>();
	}

	public HAPSegmentScriptExpressionScriptComplex(String id) {
		super(id);
		this.m_children = new ArrayList<HAPSegmentScriptExpressionScript>();
	}
	
	@Override
	public String getType() {  return HAPConstantShared.EXPRESSION_SEG_TYPE_SCRIPTCOMPLEX;  }
	 
	@Override
	public List<HAPSegmentScriptExpression> getChildren(){     return (List)this.m_children;      }
	public void addChild(HAPSegmentScriptExpressionScript child) {      this.m_children.add(child);         }
	
	public void addSegmentScriptSimple(HAPSegmentScriptExpressionScriptSimple scriptSegment) {	this.m_children.add(scriptSegment);	}
	
	public void addSegmentDataExpression(HAPSegmentScriptExpressionScriptDataExpression dataSegment) {	this.m_children.add(dataSegment);	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		List<String> segmentJsonArray = new ArrayList<String>();
		for(HAPSegmentScriptExpression segment : this.m_children) {
			segmentJsonArray.add(segment.toStringValue(HAPSerializationFormat.JSON));
		}
		jsonMap.put(SEGMENT, HAPUtilityJson.buildArrayJson(segmentJsonArray.toArray(new String[0])));
	}

}

@Component
class HAPSegmentScriptExpressionScriptComplex_parser extends HAPSegmentScriptExpression_parser{

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPSegmentScriptExpressionScriptComplex out = new HAPSegmentScriptExpressionScriptComplex();
		JSONObject jsonObj = (JSONObject)obj;
		this.parseToSegmentJson(jsonObj, out, parseService);
		
		JSONArray segmentJsonArray = jsonObj.optJSONArray(HAPSegmentScriptExpressionScriptComplex.SEGMENT);
		for(int i=0; i<segmentJsonArray.length(); i++) {
			out.addChild((HAPSegmentScriptExpressionScript)HAPSegmentScriptExpressionScript.parsScriptExpressionSegment(segmentJsonArray.getJSONObject(i), parseService));
		}
		
		return out;
	}

	@Override
	public String getSubName() {   return HAPConstantShared.EXPRESSION_SEG_TYPE_SCRIPTCOMPLEX;   }
	
}
