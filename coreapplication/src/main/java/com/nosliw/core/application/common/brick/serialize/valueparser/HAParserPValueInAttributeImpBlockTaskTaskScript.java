package com.nosliw.core.application.common.brick.serialize.valueparser;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.parm.HAPParms;
import com.nosliw.common.parm.HAPWithParms;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.brick.HAPIdBrickInBundle;
import com.nosliw.core.application.brick.HAPWithBrickReferences;
import com.nosliw.core.application.brick.spec.task.script.task.HAPBlockTaskTaskScript;
import com.nosliw.core.application.common.brick.serialize.HAParserPValueInAttribute;
import com.nosliw.core.resource.HAPFactoryResourceId;

public class HAParserPValueInAttributeImpBlockTaskTaskScript implements HAParserPValueInAttribute{

	@Override
	public Object parseValueInAttribute(String attrName, Object obj, HAPServiceParseEntity parseService) {
		switch(attrName){
		case HAPBlockTaskTaskScript.SCRIPTRESOURCEID:
		{
			return HAPFactoryResourceId.newInstance(obj);
		}
		case HAPWithParms.PARM:
		{
			HAPParms parms = new HAPParms();
			parms.buildObject(obj, HAPSerializationFormat.JSON);
			return parms;
		}
		case HAPWithBrickReferences.BRICKREFERENCES:
		{
			Map<String, HAPIdBrickInBundle> brickRefs = new LinkedHashMap<String, HAPIdBrickInBundle>();
        	if(obj instanceof JSONObject) {
        		JSONObject brickRefsJsonObj = (JSONObject)obj;
        		for(Object key : brickRefsJsonObj.keySet()) {
        			String name = (String)key;
	        		HAPIdBrickInBundle id = new HAPIdBrickInBundle();
	        		id.buildObject(brickRefsJsonObj.getJSONObject(name), HAPSerializationFormat.JSON);
	        		brickRefs.put(name, id);
        		}
        	}
			return brickRefs;
		}
		}
		return null;
	}

}
