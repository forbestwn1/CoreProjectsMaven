package com.nosliw.core.application.brick;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityNamingConversion;
import com.nosliw.core.resource.HAPFactoryResourceTypeId;
import com.nosliw.core.resource.HAPIdResourceType;
import com.nosliw.core.resource.HAPResourceId;
import com.nosliw.core.resource.HAPResourceIdSimple;

public class HAPUtilityBrickId {

	public static HAPIdBrickType parseBrickTypeId(Object obj) {
		HAPIdBrickType out = null;
		if(obj instanceof String) {
			out = new HAPIdBrickType((String)obj);
		}
		else if(obj instanceof JSONObject) {
			out = new HAPIdBrickType();
			out.buildObject(obj, HAPSerializationFormat.JSON);
		}
		return out;
	}
	
	public static HAPIdBrick parseBrickId(Object obj) {
		HAPIdBrick out = null;
		if(obj instanceof String) {
			out = new HAPIdBrick();
			out.buildObject(obj, HAPSerializationFormat.LITERATE);
		}
		else if(obj instanceof JSONObject) {
			out = new HAPIdBrick();
			out.buildObject(obj, HAPSerializationFormat.JSON);
		}
		else if(obj instanceof HAPResourceIdSimple) {
			out = fromResourceId2BrickId((HAPResourceIdSimple)obj);
		}
		return out;
	}

	public static HAPIdBrick fromResourceId2BrickId(HAPResourceIdSimple resourceId) {
		String[] segs = HAPUtilityNamingConversion.parseLevel1(resourceId.getId());
		return new HAPIdBrick(new HAPIdBrickType(resourceId.getResourceTypeId().getResourceType(), resourceId.getResourceTypeId().getVersion()), segs.length>1?segs[1]:null, segs[0]);
	}
	
	public static HAPResourceIdSimple fromBrickId2ResourceId(HAPIdBrick brickId) {
		return new HAPResourceIdSimple(brickId.getBrickTypeId().getBrickType(), brickId.getBrickTypeId().getVersion(), HAPUtilityNamingConversion.cascadeElements(brickId.getId(), brickId.getDivision(), HAPConstantShared.SEPERATOR_LEVEL1));
	}

	public static HAPIdBrickType getBrickTypeIdFromResourceId(HAPResourceId resourceId) {
		return new HAPIdBrickType(resourceId.getResourceTypeId().getResourceType(), resourceId.getResourceTypeId().getVersion());
	}
	
	public static HAPIdBrickType getBrickTypeIdFromResourceTypeId(HAPIdResourceType resourceTypeId) {
		return new HAPIdBrickType(resourceTypeId.getResourceType(), resourceTypeId.getVersion());
	}
	
	public static HAPIdResourceType getResourceTypeIdFromBrickTypeId(HAPIdBrickType brickTypeId) {
		return HAPFactoryResourceTypeId.newInstance(brickTypeId.getBrickType(), brickTypeId.getVersion());
	}
}
