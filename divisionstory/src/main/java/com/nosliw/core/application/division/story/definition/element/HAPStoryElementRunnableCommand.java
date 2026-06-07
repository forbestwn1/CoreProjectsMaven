package com.nosliw.core.application.division.story.definition.element;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementParser;
import com.nosliw.core.application.division.story.definition.HAPStoryElementRunnable;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;
import com.nosliw.core.service.entityparse.HAPEntityParsable;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

public class HAPStoryElementRunnableCommand extends HAPStoryElementRunnable{

	//command element id
	public final static String CHILD_COMMAND = "command";

	//data association for request
	public final static String CHILD_REQUESTDATAASSOCIATION = "requestDataAssociation";
	
	//data association for response
	public final static String CHILD_RESPONSEDATAASSOCIATION = "responseDataAssociation";

	
	public HAPStoryElementRunnableCommand() {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_TASK_COMMAND));
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		HAPStoryElementRunnableCommand out = new HAPStoryElementRunnableCommand();
		this.cloneToStoryElement(out);
		return out;
	}

}

@Component
class HAPStoryElementRunnableCommand__HAPEntityParsable extends HAPStoryElementParser{

	@Override
	public String getSubName() {    return HAPConstantShared.STORYNODE_TYPE_TASK_COMMAND;    }

	protected void parseToEntity(JSONObject jsonObj, HAPStoryElementRunnableCommand element, HAPServiceParseEntity parseService) {
		super.parseToEntity(jsonObj, element, parseService);
	}

	@Override
	public HAPEntityParsable parseEntityJson(Object obj, HAPServiceParseEntity parseService) {
		HAPStoryElementRunnableCommand out = new HAPStoryElementRunnableCommand();
		this.parseToEntity((JSONObject)obj, out, parseService);
		return out;
	}

}
