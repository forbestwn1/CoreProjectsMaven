package com.nosliw.core.application.division.story.definition.element;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.path.HAPPath;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.common.interactive.HAPInteractiveTask;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementAccessory;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElementType;
import com.nosliw.core.application.division.story.definition.HAPStoryUtilityElement;

public class HAPStoryElementAccessoryCommand extends HAPStoryElementAccessory{

	public final static String REQUEST = "request";
	
	public final static String RESPONSE = "request";
	
	
	private HAPInteractiveTask m_taskInterface;
	
	//request end points
	private Map<String, HAPStoryIdElement> m_requestIOEndPoints;
	
	//response end points
	private Map<String, Map<String, HAPStoryIdElement>> m_responseIOEndPoints;
	
	public HAPStoryElementAccessoryCommand() {
		this(null, null);
	}
	
	public HAPStoryElementAccessoryCommand(HAPInteractiveTask taskInterface, HAPEntityInfo commandInfo) {
		super(new HAPStoryIdElementType(HAPConstantShared.STORYNODE_TYPE_COMMAND), commandInfo);
		this.m_taskInterface = taskInterface;
		this.m_requestIOEndPoints = new LinkedHashMap<String, HAPStoryIdElement>();
		this.m_responseIOEndPoints = new LinkedHashMap<String, Map<String, HAPStoryIdElement>>();
	}

	@Override
	public HAPStoryIdElement getChild(String childName) {
		HAPPath path = HAPStoryUtilityElement.parseChildNameToPath(childName);
		String[] segs = path.getPathSegments();
		if(REQUEST.equals(segs[0])){
			return this.m_requestIOEndPoints.get(segs[1]);
		}
		else if(RESPONSE.equals(segs[0])){
			return this.m_responseIOEndPoints.get(segs[1]).get(segs[2]);
		}
		return null;
	}
	
	@Override
	public boolean addChild(HAPStoryElement ele, String childName) {
		HAPPath path = HAPStoryUtilityElement.parseChildNameToPath(childName);
		String[] segs = path.getPathSegments();
		if(REQUEST.equals(segs[0])){
			this.m_requestIOEndPoints.put(segs[1], ele.getElementId());
			return true;
		}
		else if(RESPONSE.equals(segs[0])){
			Map<String, HAPStoryIdElement> result = this.m_responseIOEndPoints.get(segs[1]);
			if(result==null) {
				result = new LinkedHashMap<String, HAPStoryIdElement>();
				this.m_responseIOEndPoints.put(segs[1], result);
			}
			result.put(segs[2], ele.getElementId());
			return true;
		}
		else {
			return false;
		}
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
		storyEle.m_requestIOEndPoints.putAll(this.m_requestIOEndPoints);
		storyEle.m_responseIOEndPoints.putAll(this.m_responseIOEndPoints);
	}

	@Override
	public HAPStoryElement cloneStoryElement() {
		HAPStoryElementAccessoryCommand out = new HAPStoryElementAccessoryCommand();
	    this.cloneToStoryElement(out);
		return out;
	}

}
