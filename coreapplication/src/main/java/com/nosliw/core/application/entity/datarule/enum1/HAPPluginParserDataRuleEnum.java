package com.nosliw.core.application.entity.datarule.enum1;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.entity.datarule.HAPDataRule;
import com.nosliw.core.application.entity.datarule.HAPPluginParserDataRule;
import com.nosliw.core.data.HAPUtilityData;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPParserEntityImpWithDomain;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@Component
public class HAPPluginParserDataRuleEnum extends HAPParserEntityImpWithDomain implements HAPPluginParserDataRule{

	@Override
	public HAPDataRule parse(Object obj) {
		JSONObject jsonObj = (JSONObject)obj;
		
		String enumCode = (String)jsonObj.opt(HAPDataRuleEnumCode.ENUMCODE);
		if(enumCode!=null) {
			HAPDataRuleEnumCode codeEnum = new HAPDataRuleEnumCode();
			codeEnum.setEnumCode(enumCode);
			return codeEnum;
		}
		else{
			HAPDataRuleEnumData dataEnum = new HAPDataRuleEnumData();
			JSONArray dataArray = jsonObj.optJSONArray(HAPDataRuleEnumData.DATASET);
			if(dataArray!=null) {
				for(int i=0; i<dataArray.length(); i++) {
					dataEnum.addData(HAPUtilityData.buildDataWrapperFromObject(dataArray.get(i)));
				}
			}
			return dataEnum;
		}
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		JSONObject jsonObj = (JSONObject)obj;
		
		String enumCode = (String)jsonObj.opt(HAPDataRuleEnumCode.ENUMCODE);
		if(enumCode!=null) {
			HAPDataRuleEnumCode codeEnum = new HAPDataRuleEnumCode();
			codeEnum.setEnumCode(enumCode);
			return codeEnum;
		}
		else{
			HAPDataRuleEnumData dataEnum = new HAPDataRuleEnumData();
			JSONArray dataArray = jsonObj.optJSONArray(HAPDataRuleEnumData.DATASET);
			if(dataArray!=null) {
				for(int i=0; i<dataArray.length(); i++) {
					dataEnum.addData(HAPUtilityData.buildDataWrapperFromObject(dataArray.get(i)));
				}
			}
			return dataEnum;
		}
	}

	@Override
	public String getDomain() {   return HAPDataRule.ENTITYPARSEDOMAIN;  }

	@Override
	public String getSubName() {   return HAPConstantShared.DATARULE_TYPE_ENUM;   }

}
