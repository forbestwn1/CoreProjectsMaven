package com.nosliw.core.application.division.story.definition.element;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementRunnable;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;

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
