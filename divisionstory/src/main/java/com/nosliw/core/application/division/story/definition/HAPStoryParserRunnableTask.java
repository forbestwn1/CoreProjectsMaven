package com.nosliw.core.application.division.story.definition;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.division.story.definition.runnable.HAPStoryDataAssociationForTask;

abstract public class HAPStoryParserRunnableTask extends HAPStoryParserRunnable{

	protected void parseToEntity(JSONObject jsonObj, HAPStoryRunnableTask runnable, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, runnable, parseService);
		
		JSONObject daJsonObj = jsonObj.optJSONObject(HAPStoryRunnableTask.DATAASSOCIATION);
		if(daJsonObj!=null) {
			HAPStoryDataAssociationForTask da = new HAPStoryDataAssociationForTask();
			da.buildObject(daJsonObj, HAPSerializationFormat.JSON);
			runnable.setDataAssociation(da);
		}
	}

}
