package com.nosliw.core.application.common.scriptexpressio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.script.HAPJSScriptInfo;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPJsonTypeAsItIs;
import com.nosliw.common.serialization.HAPJsonTypeScript;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPParserEntity;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.variable.HAPWithVariableImp;
import com.nosliw.core.application.common.dataexpression.HAPContainerDataExpression;
import com.nosliw.core.application.common.scriptexpression.serialize.HAPInfoScriptFunction;
import com.nosliw.core.application.common.scriptexpression.serialize.HAPUtilityScriptForExecuteJSScript;

@HAPEntityWithAttribute
public class HAPExpressionScriptImp extends HAPWithVariableImp implements HAPExpressionScript{

	public static final String TYPE = "type";
	
	public static final String SEGMENT = "segment";
	
	public static final String VARKYES = "varKeys";
	
	public static final String DATAEXPRESSIONCONTAINER = "dataExpressionContainer";
	
	private String m_type;
	
	private List<HAPSegmentScriptExpression> m_segments;
	
	private Set<String> m_varKeys = new HashSet<String>();

	private HAPContainerDataExpression m_dataExpressionContainer;

	public HAPExpressionScriptImp() {
		this.m_segments = new ArrayList<HAPSegmentScriptExpression>();
		this.m_dataExpressionContainer = new HAPContainerDataExpression(); 
	}

	public HAPExpressionScriptImp(String type) {
		this();
		this.m_type = type;
	}
	
	@Override
	public String getType() {     return this.m_type;      }
	public void setType(String type) {       this.m_type = type;       } 

	@Override
	public String getWithVariableEntityType() {		return HAPConstantShared.WITHVARIABLE_ENTITYTYPE_SCRIPTEXPRESSION;	}

	@Override
	public HAPContainerDataExpression getDataExpressionContainer() {   return this.m_dataExpressionContainer;  }
	public void setDataExpressionContainer(HAPContainerDataExpression dataExpressionContainer) {      this.m_dataExpressionContainer = dataExpressionContainer;          }
	
	public void addSegment(HAPSegmentScriptExpression segment) {	this.m_segments.add(segment);	}
	public List<HAPSegmentScriptExpression> getSegments(){    return this.m_segments;     }
	
	public Set<String> getVariableKeys(){   return this.m_varKeys;    }
	public void addVariableKey(String key) {   this.m_varKeys.add(key);    }

//	@Override
//	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
//		super.buildJsonMap(jsonMap, typeJsonMap);
//		jsonMap.put(DYNAMICITEMTYPE, this.getType());
//		jsonMap.put(VARIABLEKEYS, HAPUtilityJson.buildJson(this.m_varKeys, HAPSerializationFormat.JSON));
//		jsonMap.put(DATAEXPRESSIONIDS, HAPUtilityJson.buildJson(this.m_dataExpressionId, HAPSerializationFormat.JSON));
//		
//		List<String> segmentArrayStr = new ArrayList<String>();
//		for(HAPSegmentScriptExpression segment : this.m_segments) {
//			segmentArrayStr.add(segment.toStringValue(HAPSerializationFormat.JSON));
//		}
//		jsonMap.put(SEGMENT, HAPUtilityJson.buildArrayJson(segmentArrayStr.toArray(new String[0])));
//	}
	
	@Override
	public void buildJSJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJSJsonMap(jsonMap, typeJsonMap);
		HAPInfoScriptFunction scriptFunctionInfo = HAPUtilityScriptForExecuteJSScript.buildExpressionFunctionInfo(this);
		
		String functionParmValue = "{}";
		List<HAPJSScriptInfo> childrenFun = scriptFunctionInfo.getChildren();
		if(!childrenFun.isEmpty()) {
			Map<String, String> funScriptMap = new LinkedHashMap<String, String>();
			Map<String, Class<?>> funScriptTypeMap = new LinkedHashMap<String, Class<?>>();
			for(HAPJSScriptInfo childFun : childrenFun) {
				funScriptMap.put(childFun.getName(), childFun.getScript());
				funScriptTypeMap.put(childFun.getName(), HAPJsonTypeAsItIs.class);
			}
			functionParmValue = HAPUtilityJson.buildMapJson(funScriptMap, funScriptTypeMap);
		}
		jsonMap.put(SUPPORTFUNCTION, functionParmValue);
		typeJsonMap.put(SUPPORTFUNCTION, HAPJsonTypeScript.class);

		jsonMap.put(SCRIPTFUNCTION, new HAPJsonTypeScript(scriptFunctionInfo.getMainScript().getScript()).toStringValue(HAPSerializationFormat.JSON_FULL));
		typeJsonMap.put(SCRIPTFUNCTION, HAPJsonTypeScript.class);
		
		jsonMap.put(DATAEXPRESSION, this.m_dataExpressionContainer.toStringValue(HAPSerializationFormat.JAVASCRIPT));
	}

//	@Override
//	public void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
//		super.buildJsonMap(jsonMap, typeJsonMap);
//		HAPInfoScriptFunction scriptFunctionInfo = HAPUtilityScriptForExecuteJSScript.buildExpressionFunctionInfo(this);
//		
//		String functionParmValue = "{}";
//		List<HAPJSScriptInfo> childrenFun = scriptFunctionInfo.getChildren();
//		if(!childrenFun.isEmpty()) {
//			Map<String, String> funScriptMap = new LinkedHashMap<String, String>();
//			Map<String, Class<?>> funScriptTypeMap = new LinkedHashMap<String, Class<?>>();
//			for(HAPJSScriptInfo childFun : childrenFun) {
//				funScriptMap.put(childFun.getName(), childFun.getScript());
//				funScriptTypeMap.put(childFun.getName(), HAPJsonTypeAsItIs.class);
//			}
//			functionParmValue = HAPUtilityJson.buildMapJson(funScriptMap, funScriptTypeMap);
//		}
//		jsonMap.put(SUPPORTFUNCTION, functionParmValue);
//		typeJsonMap.put(SUPPORTFUNCTION, HAPJsonTypeScript.class);
//
//		jsonMap.put(SCRIPTFUNCTION, new HAPJsonTypeScript(scriptFunctionInfo.getMainScript().getScript()).toStringValue(HAPSerializationFormat.JSON_FULL));
//		typeJsonMap.put(SCRIPTFUNCTION, HAPJsonTypeScript.class);
//		
//		jsonMap.put(DATAEXPRESSION, this.m_dataExpressionContainer.toStringValue(HAPSerializationFormat.JSON));
//	}

	
	@Override
	public void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJsonMap(jsonMap, typeJsonMap);
		
		jsonMap.put(TYPE, this.m_type);
		jsonMap.put(SEGMENT, HAPManagerSerialize.getInstance().toStringValue(this.m_segments, HAPSerializationFormat.JSON));
		jsonMap.put(VARKYES, HAPManagerSerialize.getInstance().toStringValue(this.m_varKeys, HAPSerializationFormat.JSON));
		jsonMap.put(DATAEXPRESSIONCONTAINER, HAPManagerSerialize.getInstance().toStringValue(this.m_dataExpressionContainer, HAPSerializationFormat.JSON));
	}
	
	
//	@Override
//	protected void buildResourceDependency(List<HAPResourceDependency> dependency, HAPRuntimeInfo runtimeInfo, HAPManagerResource resourceManager) {
//		
//	}

}

@Component
class HAPExpressionScriptImp_parser implements HAPParserEntity{

	@Override
	public String getEntityType() {    return HAPExpressionScriptImp.class.getName();   }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPExpressionScriptImp out = new HAPExpressionScriptImp();
		
		JSONObject jsonObj = (JSONObject)obj;
		
		HAPWithVariableImp.buildToWithVariableImp(out, jsonObj, parseService);
		
		out.setType((String)jsonObj.opt(HAPExpressionScriptImp.TYPE));
		
		JSONArray segmentsJsonArray = jsonObj.optJSONArray(HAPExpressionScriptImp.SEGMENT);
		if(segmentsJsonArray!=null) {
			for(int i=0; i<segmentsJsonArray.length(); i++) {
				out.addSegment(HAPSegmentScriptExpression.parsScriptExpressionSegment(segmentsJsonArray.getJSONObject(i), parseService));
			}
		}
		
		JSONArray varKeysJsonArray = jsonObj.optJSONArray(HAPExpressionScriptImp.VARKYES);
		if(varKeysJsonArray!=null) {
			for(int i=0; i<varKeysJsonArray.length(); i++) {
				out.addVariableKey(varKeysJsonArray.getString(i));
			}
		}

		out.setDataExpressionContainer((HAPContainerDataExpression)parseService.parseEntityJSONExplicit(jsonObj.optJSONObject(HAPExpressionScriptImp.DATAEXPRESSIONCONTAINER), HAPContainerDataExpression.class.getName()));
		
		return out;
	}
	
}

