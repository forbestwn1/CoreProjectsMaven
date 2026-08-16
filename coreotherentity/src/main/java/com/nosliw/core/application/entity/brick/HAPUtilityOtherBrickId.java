package com.nosliw.core.application.entity.brick;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.application.HAPIdBrick;
import com.nosliw.core.application.HAPIdBrickType;
import com.nosliw.core.application.HAPUtilityBrickId;

public class HAPUtilityOtherBrickId {

	public static HAPIdBrickType parseBrickTypeId(Object entityTypeObj, HAPIdBrickType entityTypeIfNotProvided, HAPManagerApplicationBrick entityManager) {
		String entityType = null;
		String entityTypeVersion = null;
		if(entityTypeObj!=null) {
			HAPIdBrickType entityTypeId1 = HAPUtilityBrickId.parseBrickTypeId(entityTypeObj);
			entityType = entityTypeId1.getBrickType();
			entityTypeVersion = entityTypeId1.getVersion();
		}
		//try with entityTypeIfNotProvided
		if(entityTypeIfNotProvided!=null) {
			if(entityType==null) {
				entityType = entityTypeIfNotProvided.getBrickType();
			}
			if(entityTypeVersion==null) {
				entityTypeVersion = entityTypeIfNotProvided.getVersion();
			}
		}
		
		if(entityType==null) {
			return null;
		}
		
		//if version not provided, then use latest version
		if(entityTypeVersion==null) {
			entityTypeVersion = entityManager.getLatestVersion(entityType).getVersion();
		}
		return new HAPIdBrickType(entityType, entityTypeVersion);
	}
	
	public static HAPIdBrickType parseBrickTypeIdAggresive(Object obj, HAPManagerApplicationBrick brickMan) {
		HAPIdBrickType brickTypeId = HAPUtilityBrickId.parseBrickTypeId(obj);
		return normalizeBrickTypeId(brickTypeId, brickMan);
	}

	public static HAPIdBrick parseBrickIdAgressive(Object obj, HAPIdBrickType brickTypeIfNotProvided, String defaultDivision, HAPManagerApplicationBrick brickMan) {
		HAPIdBrick out = new HAPIdBrick();
		
		if(obj instanceof String) {
			out.buildObject(obj, HAPSerializationFormat.LITERATE);
		}
		else if(obj instanceof JSONObject) {
			out.buildObject(obj, HAPSerializationFormat.JSON);
		}
		
		if(out.getBrickTypeId()==null) {
			out.setBrickTypeId(brickTypeIfNotProvided);
		}
		
		out.setBrickTypeId(normalizeBrickTypeId(out.getBrickTypeId(), brickMan));
		if(out.getDivision()==null) {
			out.setDivision(defaultDivision);
		}
		
		return out;
	}
	

	
	public static HAPIdBrickType normalizeBrickTypeId(HAPIdBrickType brickTypeId, HAPManagerApplicationBrick brickMan) {
		HAPIdBrickType out = brickTypeId;
		if(out.getVersion()==null) {
			out = brickMan.getLatestVersion(brickTypeId.getBrickType());
		}
		return out;
	}

}
