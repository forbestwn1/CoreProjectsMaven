package com.nosliw.core.xxx.application.division.manual.core.b;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.application.HAPIdBrickType;
import com.nosliw.core.application.division.manual.core.HAPManualBrick;
import com.nosliw.core.application.entity.brick.HAPManagerApplicationBrick;
import com.nosliw.core.application.entity.brick.HAPUtilityOtherBrickId;

public class HAPUtilitySerializeJson {

	public static HAPManualBrick buildBrick(JSONObject jsonObj, HAPIdBrickType entityTypeIfNotProvided, HAPManagerApplicationBrick brickMan) {
		HAPIdBrickType brickType = HAPUtilityOtherBrickId.parseBrickTypeId(jsonObj.opt(HAPManualBrick.BRICKTYPE), entityTypeIfNotProvided, brickMan);
		HAPManualBrick out = brickMan.newBrickInstance(brickType);
		out.buildBrick(jsonObj, HAPSerializationFormat.JSON, brickMan);
		return out;
	}
	
}
