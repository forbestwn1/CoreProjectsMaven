package com.nosliw.core.application.entity.datarule.enum1;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.entity.datarule.HAPDataRule;
import com.nosliw.core.application.entity.datarule.HAPParserDataRule;
import com.nosliw.core.data.HAPUtilityData;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPDataRuleEnumCode extends HAPDataRuleEnum{

	@HAPAttribute
	public static String ENUMCODE = "enumCode";

	private String m_enumCode;

	public HAPDataRuleEnumCode() {}

	public String getEnumCode() {    return this.m_enumCode;    }
	public void setEnumCode(String enumCode) {   this.m_enumCode = enumCode;     }

	@Override
	public HAPDataRule cloneDataRule() {
		HAPDataRuleEnumCode out = new HAPDataRuleEnumCode();
		this.cloneToDataRule(out);
		out.m_enumCode = this.m_enumCode;
		return out;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		jsonMap.put(ENUMCODE, this.getEnumCode());
	}
	
	@Override
	public boolean buildObjectByJson(Object value) {
		JSONObject valueObj = (JSONObject)value;
		super.buildObjectByJson(valueObj);
		this.m_enumCode = valueObj.getString(ENUMCODE);
		return true;
	}
}

@Component
class HAPDataRuleEnumCode_Parser extends HAPParserDataRule{

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
	public String getSubName() {   return HAPConstantShared.DATARULE_TYPE_ENUM;   }

}
