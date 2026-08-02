package com.nosliw.core.application.common.command;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.HAPIdBrickInBundle;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPCommandHandlerReferenceCommand extends HAPCommandHandlerReference{

	@HAPAttribute
	public static final String BRICKID = "brickId";

	@HAPAttribute
	public static final String COMMANDNAME = "commandName";

	private HAPIdBrickInBundle m_brickId;

	private String m_commandName;
	
	public HAPCommandHandlerReferenceCommand() {
		super(HAPConstantShared.COMMAND_HANDLER_TYPE_COMMAND);
	}

	public void setCommandName(String commandName) {     this.m_commandName = commandName;        }
	public String getCommandName() {    return this.m_commandName;     }
	
	public void setBrickId(HAPIdBrickInBundle brickId) {      this.m_brickId = brickId;       }
	public HAPIdBrickInBundle getBrickId() {      return this.m_brickId;        }

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		if(this.m_brickId!=null) {
			jsonMap.put(BRICKID, this.m_brickId.toStringValue(HAPSerializationFormat.JSON));
		}
		jsonMap.put(COMMANDNAME, this.m_commandName);
	}
	
}

@Component
class HAPCommandHandlerReferenceCommand_parser extends HAPCommandHandlerReference_parser{

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPCommandHandlerReferenceCommand out = new HAPCommandHandlerReferenceCommand();

		JSONObject jsonObj = (JSONObject)obj;
		
		JSONObject brickIdJsonObj = jsonObj.optJSONObject(HAPCommandHandlerReferenceCommand.BRICKID);
		HAPIdBrickInBundle commandBrickId = new HAPIdBrickInBundle();
		commandBrickId.buildObject(brickIdJsonObj, HAPSerializationFormat.JSON);
		out.setBrickId(commandBrickId);

		out.setCommandName((String)jsonObj.opt(HAPCommandHandlerReferenceCommand.COMMANDNAME));
		
		return out;
	}

	@Override
	public String getSubName() {    return HAPConstantShared.COMMAND_HANDLER_TYPE_COMMAND;   }
	
}
