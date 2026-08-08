package com.nosliw.core.application.division.manual.core;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.info.HAPInfoImpSimple;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.core.application.HAPIdBrickType;
import com.nosliw.core.application.HAPManagerApplicationBrick;

public class HAPManualBrickWithEntityInfo_parser extends HAPManualBrick_parser{

	public HAPManualBrickWithEntityInfo_parser(HAPManagerApplicationBrick brickManager,
			Class<? extends HAPManualBrick> manualBrickClass, HAPIdBrickType brickTypeId) {
		super(brickManager, manualBrickClass, brickTypeId);
	}

	@Override
	protected Object parseValueInAttribute(String attrName, Object obj, HAPServiceParseEntity parseService) {     
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
