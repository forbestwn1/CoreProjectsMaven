package com.nosliw.core.application.division.story.definition.element;

import java.util.Map;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.path.HAPPath;
import com.nosliw.core.application.common.interactive.HAPInteractiveTask;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryElementAccessory;
import com.nosliw.core.application.division.story.definition.HAPStoryElementImp;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;
import com.nosliw.core.application.division.story.definition.HAPStoryReferenceElement;

public class HAPStoryElementAccessoryCommand extends HAPStoryElementAccessory{

	public final static String REQUEST = "request";
	
	public final static String RESPONSE = "request";
	
	
	private HAPInteractiveTask m_taskInterface;
	
	//request end points
	private Map<String, HAPStoryIdElement> m_requestIOEndPoints;
	
	//response end points
	private Map<String, Map<String, HAPStoryIdElement>> m_responseIOEndPoints;
	
	
	public HAPStoryElementAccessoryCommand(HAPInteractiveTask taskInterface, HAPEntityInfo commandInfo) {
		super();
		
		
	}

	public static HAPPath buildPathForRequestEndPoint(String parName) {}
	
	
	
	@Override
	public HAPStoryElementImp cloneStoryElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addChild(HAPStoryElement ele, HAPPath path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HAPStoryReferenceElement getChild(HAPPath path) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
