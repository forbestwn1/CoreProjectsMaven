package com.nosliw.core.application.common.event;

import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPEntityParsable;
import com.nosliw.common.serialization.HAPParserEntityImpWithDomain;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.serialization.HAPServiceParseEntity;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.common.utils.HAPUtilityNamingConversion;
import com.nosliw.core.application.brick.HAPPackageBrickInBundle;

@HAPEntityWithAttribute
public abstract class HAPEventHandlerReference extends HAPSerializableImp implements HAPEntityParsable{

	public static final String DOMAIN_PARSER = "event.handler";
	
	@HAPAttribute
	public static final String TYPE = "type";
	
	public abstract String getHandlerType();
	
	public static HAPEventHandlerReference parseHandlerInfo(Object obj, HAPServiceParseEntity parseService) {
		if(obj instanceof String) {
			String str = (String)obj;
			String[] segs = HAPUtilityNamingConversion.parseDetails(str);
			String handlerType = segs[0];
			if(HAPConstantShared.EVENT_HANDLERTYPE_TASK.equals(handlerType)) {
				HAPEventHandlerReferenceTask out = new HAPEventHandlerReferenceTask();
				HAPPackageBrickInBundle taskPackage = new HAPPackageBrickInBundle();
				taskPackage.buildObject(segs[1], HAPSerializationFormat.LITERATE);
				out.setTaskBrickPackage(taskPackage);
				return out;
			}
			else if(HAPConstantShared.EVENT_HANDLERTYPE_SCRIPT.equals(handlerType)) {
				HAPEventHandlerReferenceScript out = new HAPEventHandlerReferenceScript();
				out.setFunctionName(segs[1]);
				return out;
			}
		}
		else if(obj instanceof JSONObject) {
			return (HAPEventHandlerReference)parseService.parseEntityJSONImplicitAttribute((JSONObject)obj, TYPE, DOMAIN_PARSER);
		}
		return null;
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(TYPE, this.getHandlerType());
	}
	
}

abstract class HAPEventHandlerReference_parser extends HAPParserEntityImpWithDomain{

	@Override
	public String getDomain() {    return HAPEventHandlerReference.DOMAIN_PARSER;    }

}

