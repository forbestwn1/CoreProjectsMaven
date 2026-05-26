package com.nosliw.core.application.division.story.definition.element;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.interactive.HAPInteractiveTask;
import com.nosliw.core.application.division.story.definition.HAPStoryContainerChildrenElementsMap;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementAccessory;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;
import com.nosliw.core.application.division.story.definition.HAPStoryUtilityElement;

public class HAPStoryElementAccessoryCommand extends HAPStoryElementAccessory{

	public final static String REQUEST = "request";
	
	public final static String RESPONSE = "request";
	
	
	private HAPInteractiveTask m_taskInterface;
	
	public HAPStoryElementAccessoryCommand() {
		this(null, null);
	}
	
	public HAPStoryElementAccessoryCommand(HAPInteractiveTask taskInterface, HAPEntityInfo commandInfo) {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_COMMAND), commandInfo);
		this.m_taskInterface = taskInterface;
		
		//request end points
		this.addChildContainer(REQUEST, new HAPStoryContainerChildrenElementsMap());
		//response end points
		this.addChildContainer(RESPONSE, new HAPStoryContainerChildrenElementsMap());
		
	}

	public static HAPPath buildPathForRequestEndPoint(String parName) {
		return new HAPPath(new String[]{HAPStoryUtilityElement.buildChildNameFromPath(new String[]{REQUEST, parName}), HAPStoryElementAccessoryVariable.CHILD_ENDPOINT});	
	}
	public static HAPPath buildPathForResponseEndPoint(String resultName, String parName) {
		return new HAPPath(new String[]{HAPStoryUtilityElement.buildChildNameFromPath(new String[]{REQUEST, resultName, parName}), HAPStoryElementAccessoryVariable.CHILD_ENDPOINT});	
	}
	
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
