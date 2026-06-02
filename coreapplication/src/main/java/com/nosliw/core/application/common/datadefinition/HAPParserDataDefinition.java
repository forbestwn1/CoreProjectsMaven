package com.nosliw.core.application.common.datadefinition;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.common.info.HAPUtilityEntityInfo;
import com.nosliw.core.application.entity.datarule.HAPDataRule;
import com.nosliw.core.application.entity.datarule.HAPUtilityDataRule;
import com.nosliw.core.data.HAPUtilityData;
import com.nosliw.core.data.criteria.HAPParserCriteriaImp;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPParserDataDefinition {

	public static HAPDataDefinition parseDataDefinition(JSONObject jsonObj, HAPServiceParseEntity entityParseService) {
		
	}
	
	public static HAPDataDefinitionWritableWithInit parseDataDefinitionWritableWithInit(Object obj, HAPServiceParseEntity entityParseService) {
		HAPDataDefinitionWritableWithInit out = new HAPDataDefinitionWritableWithInit();
		
		parseToDataDefinitionWritable(obj, out, entityParseService);

		if(obj instanceof String) {
		}
		else if(obj instanceof JSONObject){
			JSONObject jsonValue = (JSONObject)obj;
			
			Object initDataObj = jsonValue.opt(HAPDataDefinitionWritableWithInit.INITDATA);
			if(initDataObj!=null) {
				out.setInitData(HAPUtilityData.buildDataWrapperFromObject(initDataObj));
			}
		}
		return out;
	}
	
	public static HAPDataDefinitionWritable parseDataDefinitionWritable(Object obj, HAPServiceParseEntity entityParseService) {
		HAPDataDefinitionWritable out = new HAPDataDefinitionWritable();
		parseToDataDefinitionWritable(obj, out, entityParseService);
		return out;
	}

	private static void parseToDataDefinitionWritable(Object obj, HAPDataDefinitionWritable dataDefWritable, HAPServiceParseEntity entityParseService) {
		parseToDataDefinition(obj, dataDefWritable);

		if(obj instanceof String) {
		}
		else if(obj instanceof JSONObject){
			JSONObject jsonValue = (JSONObject)obj;
			
			JSONArray ruleJsonArray = jsonValue.optJSONArray(HAPDataDefinitionWritable.RULE);
			if(ruleJsonArray!=null) {
				for(int i=0; i<ruleJsonArray.length(); i++) {
					JSONObject dataRuleDefJson = ruleJsonArray.getJSONObject(i);
					if(HAPUtilityEntityInfo.isEnabled(dataRuleDefJson)) {
						HAPDefinitionDataRule dataRuleDef = new HAPDefinitionDataRule();
						
						dataRuleDef.buildEntityInfoByJson(dataRuleDefJson);
						
						String rulePath = (String)dataRuleDefJson.opt(HAPDefinitionDataRule.PATH);
						if(rulePath!=null) {
							dataRuleDef.setPath(rulePath);
						}
						
						JSONObject dataRuleJson = dataRuleDefJson.optJSONObject(HAPDefinitionDataRule.DATARULE);
						if(dataRuleJson==null) {
							dataRuleJson = dataRuleDefJson;
						}
						HAPDataRule dataRule = HAPUtilityDataRule.parseDataRule(dataRuleJson, entityParseService); 
						dataRuleDef.setDataRule(dataRule);
						
						dataDefWritable.addRule(dataRuleDef);
					}
				}
			}
			
//			JSONObject ruleMatchersObj = jsonValue.optJSONObject(RULEMATCHERS);
//			if(ruleMatchersObj!=null) {
//				this.m_ruleMatchers = new HAPMatchersCombo();
//				this.m_ruleMatchers.buildObject(ruleMatchersObj, HAPSerializationFormat.JSON);
//			}
//
//			String ruleCriteriaStr = (String)jsonValue.opt(RULECRITERIA);
//			if(ruleCriteriaStr!=null) {
//				this.m_ruleCriteria = HAPParserCriteriaImp.getInstance().parseCriteria(ruleCriteriaStr);
//			}
			
		}
	}

	public static HAPDataDefinitionReadonly parseDataDefinitionReadonly(Object obj) {
		HAPDataDefinitionReadonly out = new HAPDataDefinitionReadonly();
		parseToDataDefinition(obj, out);
		return out;
	}
	
	private static void parseToDataDefinition(Object obj, HAPDataDefinition dataDefinition) {
		if(obj instanceof String) {
			dataDefinition.setCriteria(HAPParserCriteriaImp.getInstance().parseCriteria((String)obj));
		}
		else if(obj instanceof JSONObject){
			JSONObject jsonValue = (JSONObject)obj;
			dataDefinition.setCriteria(HAPParserCriteriaImp.getInstance().parseCriteria((String)jsonValue.opt(HAPDataDefinition.CRITERIA)));
		}
	}
	
}
