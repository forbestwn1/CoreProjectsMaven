package com.nosliw.core.application.common.brick.serialize.valueparser;

import com.nosliw.common.parm.HAPParms;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.brick.task.script.task.HAPBlockTaskTaskScript;
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
		case HAPBlockTaskTaskScript.PARM:
		{
			HAPParms parms = new HAPParms();
			parms.buildObject(obj, HAPSerializationFormat.JSON);
			return parms;
		}
		}
		return null;
	}

}
