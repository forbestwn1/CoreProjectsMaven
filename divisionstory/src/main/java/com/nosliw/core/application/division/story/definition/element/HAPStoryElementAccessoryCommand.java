package com.nosliw.core.application.division.story.definition.element;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.path.HAPPath;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.interactive.HAPInteractiveTask;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementAccessory;
import com.nosliw.core.application.division.story.definition.HAPStoryElementImpWithEntityInfoParser;
import com.nosliw.core.application.division.story.definition.HAPStoryElementWithEndPoint;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;
import com.nosliw.core.application.division.story.definition.HAPStoryUtilityElement;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryElementAccessoryCommand extends HAPStoryElementAccessory{

	public final static String TASKINTERFACE = "taskInterface";
	
	public final static String CHILD_REQUEST = "request";
	
	public final static String CHILD_RESPONSE = "response";
	
	private HAPInteractiveTask m_taskInterface;
	
	public HAPStoryElementAccessoryCommand() {
		this(null, null);
	}
	
	public HAPStoryElementAccessoryCommand(HAPInteractiveTask taskInterface, HAPEntityInfo commandInfo) {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_COMMAND), commandInfo);
		this.m_taskInterface = taskInterface;
	}

    public void setTaskInterface(HAPInteractiveTask taskInterface) {    this.m_taskInterface = taskInterface;        }
	
	public static HAPPath getAddRequestParmChildPath() {		return HAPStoryUtilityElement.buildChildPathForElement(new HAPPath(CHILD_REQUEST));  }
	public static HAPPath getRequestParmEndPointPath(String parmName) {
		return new HAPPath(new String[] {CHILD_REQUEST, parmName, HAPStoryElementWithEndPoint.CHILD_ENDPOINT});
	}

	public static HAPPath getAddResponseParmChildPath(String resultName, String parName) {		return HAPStoryUtilityElement.buildChildPathForElement(new HAPPath(new String[] {CHILD_RESPONSE, resultName}));	}
	
	protected void cloneToStoryElement(HAPStoryElementAccessoryCommand storyEle) {
		super.cloneToStoryElement(storyEle);
		storyEle.m_taskInterface = this.m_taskInterface;
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		HAPStoryElementAccessoryCommand out = new HAPStoryElementAccessoryCommand();
	    this.cloneToStoryElement(out);
		return out;
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		super.buildJsonMap(jsonMap, typeJsonMap);
		if(this.m_taskInterface!=null) {
			jsonMap.put(TASKINTERFACE, this.m_taskInterface.toStringValue(HAPSerializationFormat.JSON));
		}
	}
	
}

@Component
class HAPStoryElementAccessoryCommand__HAPEntityParsable extends HAPStoryElementImpWithEntityInfoParser{

	@Override
	public String getSubName() {    return HAPConstantShared.STORYNODE_TYPE_COMMAND;    }

	protected void parseToEntity(JSONObject jsonObj, HAPStoryElementAccessoryCommand element, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, element, parseService);
		JSONObject taskInterfaceJsonObj = jsonObj.optJSONObject(HAPStoryElementAccessoryCommand.TASKINTERFACE);
		if(taskInterfaceJsonObj!=null) {
			element.setTaskInterface(HAPInteractiveTask.parse(taskInterfaceJsonObj, parseService));
		}
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryElementAccessoryCommand out = new HAPStoryElementAccessoryCommand();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}
