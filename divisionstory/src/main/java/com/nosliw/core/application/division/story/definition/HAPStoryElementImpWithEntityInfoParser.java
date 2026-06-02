package com.nosliw.core.application.division.story.definition;

import org.json.JSONObject;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.info.HAPEntityInfoImp;
import com.nosliw.common.info.HAPWithEntityInfo;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

abstract public class HAPStoryElementImpWithEntityInfoParser extends HAPStoryElementParser{

	protected void parseToEntity(JSONObject jsonObj, HAPStoryElementImpWithEntityInfo element, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, element, parseService);
		JSONObject entityInfoJsonObj = jsonObj.optJSONObject(HAPWithEntityInfo.ENTITYINFO);
		if(entityInfoJsonObj!=null) {
			HAPEntityInfo entityInfo = new HAPEntityInfoImp();
			entityInfo.buildObject(entityInfoJsonObj, HAPSerializationFormat.JSON);
			element.setEntityInfo(entityInfo);
		}
	}

}
