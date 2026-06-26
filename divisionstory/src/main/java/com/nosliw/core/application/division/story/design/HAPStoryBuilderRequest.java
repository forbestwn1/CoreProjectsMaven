package com.nosliw.core.application.division.story.design;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.core.application.HAPIdBrick;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPParserEntity;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPStoryBuilderRequest extends HAPSerializableImp implements HAPEntityParsable{

	@HAPAttribute
	public static final String PARSABLEENTITYTYPE = "story.builder.request";
	
	@HAPAttribute
	public static final String BRICKID = "brickId";
	
	@HAPAttribute
	public static final String COMMAND = "command";
	
	@HAPAttribute
	public static final String REQUESTDATA = "requestData";
	
	private HAPIdBrick m_brickId;
	
	private String m_command;
	
	private Object m_requestData;
	
    public HAPIdBrick getBrickId() {     return this.m_brickId;      }
    public void setBrickId(HAPIdBrick brickId) {     this.m_brickId = brickId;     }
	
	public String getCommand() {	return this.m_command;	}
	public void setCommand(String command) {    this.m_command = command;       }

	public Object getRequestData() {		return this.m_requestData;	}
	public void setRequestData(Object requestData) {    this.m_requestData = requestData;        }
	
}

@Component
class HAPStoryBuilderRequest_HAPEntityParsable implements HAPParserEntity{

	@Override
	public String getEntityType() {   return HAPStoryBuilderRequest.PARSABLEENTITYTYPE;  }

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
	    JSONObject jsonObj = (JSONObject)obj;

	    HAPStoryBuilderRequest out = new HAPStoryBuilderRequest();
	    
	    HAPIdBrick brickId = new HAPIdBrick();
	    brickId.buildObject(jsonObj.get(HAPStoryBuilderRequest.BRICKID), HAPSerializationFormat.JSON);
	    out.setBrickId(brickId);
	    
	    out.setCommand(jsonObj.getString(HAPStoryBuilderRequest.COMMAND));
	    out.setRequestData(jsonObj.optJSONObject(HAPStoryBuilderRequest.REQUESTDATA));
		
		return out;
	}

}
