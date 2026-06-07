package com.nosliw.core.application.common.datadefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.info.HAPUtilityEntityInfo;
import com.nosliw.common.serialization.HAPManagerSerialize;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPUtilityJson;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityBasic;
import com.nosliw.core.application.entity.datarule.HAPDataRule;
import com.nosliw.core.application.entity.datarule.HAPUtilityDataRule;
import com.nosliw.core.data.criteria.HAPDataTypeCriteria;
import com.nosliw.core.data.criteria.HAPUtilityCriteria;
import com.nosliw.core.data.matcher.HAPMatchers;
import com.nosliw.core.data.matcher.HAPMatchersCombo;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPDataDefinitionWritable extends HAPDataDefinition{

	@HAPAttribute
	public static String RULE = "rule";

	@HAPAttribute
	public static String RULEMATCHERS = "ruleMatchers";

	@HAPAttribute
	public static String RULECRITERIA = "ruleCriteria";

	//rules that apply constrain for the value
	private List<HAPDefinitionDataRule> m_rules;
	
	//matchers that apply to rule
	private HAPMatchersCombo m_ruleMatchers;

	private HAPDataTypeCriteria m_ruleCriteria;

	public HAPDataDefinitionWritable(HAPDataDefinition dataDefinition) {
		this();
		dataDefinition.cloneToDataDefinition(this);
	}
	
	HAPDataDefinitionWritable(String type) { 
		super(type);
		this.m_rules = new ArrayList<HAPDefinitionDataRule>();
	}

	public HAPDataDefinitionWritable() { 
		super(HAPConstantShared.DATADEFINITION_TYPE_WRITABLE);
		this.m_rules = new ArrayList<HAPDefinitionDataRule>();
	}
	
	public HAPDataDefinitionWritable(HAPDataTypeCriteria criteria) {
		super(HAPConstantShared.DATADEFINITION_TYPE_WRITABLE, criteria);
		this.m_rules = new ArrayList<HAPDefinitionDataRule>();
	}
	
	public List<HAPDefinitionDataRule> getRules(){   return this.m_rules;   }
	public void addRule(HAPDefinitionDataRule rule) {   
		if(rule!=null) {
			this.m_rules.add(rule);
		}    
	}
	
	public HAPMatchersCombo getRuleMatchers() {    return this.m_ruleMatchers;    }
	public void setRuleMatchers(HAPMatchers matchers, HAPDataTypeCriteria ruleCriteria) {
		if(matchers!=null) {
			this.m_ruleMatchers = new HAPMatchersCombo(matchers);
			this.m_ruleCriteria = ruleCriteria;
		}
		else {
			this.m_ruleMatchers = null;
			this.m_ruleCriteria = null;
		}
	}
	public HAPDataTypeCriteria getRuleCriteria() {   return this.m_ruleCriteria;   }
	
	@Override
	public boolean equals(Object obj){
		boolean out = false;
		if(obj instanceof HAPDataDefinitionWritable){
			HAPDataDefinitionWritable dataInfo = (HAPDataDefinitionWritable)obj;
			if(super.equals(dataInfo) 
				&& HAPUtilityBasic.isEqualLists(this.getRules(), dataInfo.getRules()) 
				&& HAPUtilityBasic.isEquals(this.getRuleMatchers(), dataInfo.getRuleMatchers())
			){
				out = true;
			}
		}
		return out;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(RULE, HAPUtilityJson.buildJson(m_rules, HAPSerializationFormat.JSON));
		if(this.m_ruleMatchers!=null) {
			jsonMap.put(RULEMATCHERS, this.m_ruleMatchers.toStringValue(HAPSerializationFormat.JSON));
		}
		if(this.getRuleCriteria()!=null) {
			jsonMap.put(RULECRITERIA, HAPManagerSerialize.getInstance().toStringValue(this.getRuleCriteria(), HAPSerializationFormat.LITERATE));
		}
	}
	
	protected void cloneToDataDefinition(HAPDataDefinitionWritable out) {
		super.cloneToDataDefinition(out);
		out.m_rules.addAll(this.m_rules);
		if(this.m_ruleMatchers!=null) {
			out.m_ruleMatchers = this.m_ruleMatchers.cloneMatchers();
		}
		if(this.m_ruleCriteria!=null) {
			out.m_ruleCriteria = HAPUtilityCriteria.cloneDataTypeCriteria(this.m_ruleCriteria);
		}
	}
	
	public HAPDataDefinitionWritable cloneDataDefinitionWritable() {
		HAPDataDefinitionWritable out = new HAPDataDefinitionWritable();
		this.cloneToDataDefinition(out);
		return out;
	}
	
	@Override
	public String toString(){		return this.toStringValue(HAPSerializationFormat.JSON);	}
}

@Component
class HAPDataDefinitionWritable__HAPEntityParsable extends HAPDataDefinition__HAPEntityParsable{

	@Override
	public String getSubName() {     return HAPConstantShared.DATADEFINITION_TYPE_WRITABLE;    }
	
	protected void parseToEntity(JSONObject jsonObj, HAPDataDefinitionWritable dataDefinition, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, dataDefinition, parseService);
		
		JSONArray ruleJsonArray = jsonObj.optJSONArray(HAPDataDefinitionWritable.RULE);
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
					HAPDataRule dataRule = HAPUtilityDataRule.parseDataRule(dataRuleJson, parseService); 
					dataRuleDef.setDataRule(dataRule);
					
					dataDefinition.addRule(dataRuleDef);
				}
			}
		}
		
//		JSONObject ruleMatchersObj = jsonValue.optJSONObject(RULEMATCHERS);
//		if(ruleMatchersObj!=null) {
//			this.m_ruleMatchers = new HAPMatchersCombo();
//			this.m_ruleMatchers.buildObject(ruleMatchersObj, HAPSerializationFormat.JSON);
//		}
//
//		String ruleCriteriaStr = (String)jsonValue.opt(RULECRITERIA);
//		if(ruleCriteriaStr!=null) {
//			this.m_ruleCriteria = HAPParserCriteriaImp.getInstance().parseCriteria(ruleCriteriaStr);
//		}
		
		
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPDataDefinitionWritable out = new HAPDataDefinitionWritable();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}

