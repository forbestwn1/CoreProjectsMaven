package com.nosliw.core.application.division.story.design;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPParserEntity;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

@HAPEntityWithAttribute
public class HAPStoryBuilderRequest extends HAPSerializableImp implements HAPEntityParsable{

	@HAPAttribute
	public static final String PARSABLEENTITYTYPE = "story.builder.request";
	
	@HAPAttribute
	public static final String DESIGNID = "designId";
	
	@HAPAttribute
	public static final String COMMAND = "command";
	
	@HAPAttribute
	public static final String REQUESTDATA = "requestData";
	
	private String m_designId;
	
	private String m_command;
	
	private Object m_requestData;
	
    public String getDesignId() {     return this.m_designId;      }
    public void setDesignId(String designId) {     this.m_designId = designId;     }
	
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
	    out.setDesignId(jsonObj.getString(HAPStoryBuilderRequest.DESIGNID));
	    out.setCommand(jsonObj.getString(HAPStoryBuilderRequest.COMMAND));
	    out.setRequestData(jsonObj.optJSONObject(HAPStoryBuilderRequest.REQUESTDATA));
		
		return out;
	}

}