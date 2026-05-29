package com.nosliw.core.application.division.story.definition.element;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.interactive.HAPInteractiveTask;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementAccessory;
import com.nosliw.core.application.division.story.definition.HAPStoryElementWithEndPoint;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;
import com.nosliw.core.application.division.story.definition.HAPStoryUtilityStory;

public class HAPStoryElementAccessoryCommand extends HAPStoryElementAccessory{

	public final static String CHILD_REQUEST = "request";
	
	public final static String CHILD_RESPONSE = "request";
	
	private HAPInteractiveTask m_taskInterface;
	
	public HAPStoryElementAccessoryCommand() {
		this(null, null);
	}
	
	public HAPStoryElementAccessoryCommand(HAPInteractiveTask taskInterface, HAPEntityInfo commandInfo) {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_COMMAND), commandInfo);
		this.m_taskInterface = taskInterface;
	}

	public static HAPPath getAddRequestParmChildPath() {		return HAPStoryUtilityStory.buildChildPathForElement(new HAPPath(CHILD_REQUEST));  }
	public static HAPPath getRequestParmEndPointPath(String parmName) {
		return new HAPPath(new String[] {CHILD_REQUEST, parmName, HAPStoryElementWithEndPoint.CHILD_ENDPOINT});
	}

	public static HAPPath getAddResponseParmChildPath(String resultName, String parName) {		return HAPStoryUtilityStory.buildChildPathForElement(new HAPPath(new String[] {CHILD_RESPONSE, resultName}));	}
	
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

}
