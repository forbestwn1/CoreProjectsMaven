package com.nosliw.core.application.common.brick.serialize.valueparser;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.info.HAPInfoImpSimple;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.common.brick.serialize.HAParserPValueInAttribute;

public class HAParserPValueInAttributeImpEntityInfo implements HAParserPValueInAttribute{

	@Override
	public Object parseValueInAttribute(String attrName, Object obj, HAPServiceParseEntity parseService) {
		if(attrName.equals(HAPEntityInfo.NAME)) {
			return obj;
		}
		else if(attrName.equals(HAPEntityInfo.ID)) {
			return obj;
		}
		else if(attrName.equals(HAPEntityInfo.DESCRIPTION)) {
			return obj;
		}
		else if(attrName.equals(HAPEntityInfo.STATUS)) {
			return obj;
		}
		else if(attrName.equals(HAPEntityInfo.INFO)) {
			HAPInfoImpSimple info = new HAPInfoImpSimple();
			info.buildObject(obj, HAPSerializationFormat.JSON);
			return info;
		}
		return null;
	}

}
