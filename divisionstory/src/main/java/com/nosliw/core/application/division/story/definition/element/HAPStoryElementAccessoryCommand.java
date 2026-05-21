package com.nosliw.core.application.division.story.definition.element;

import java.util.List;
import java.util.Map;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.path.HAPPath;
import com.nosliw.core.application.division.story.definition.HAPStoryElement;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;

public class HAPStoryElementAccessoryCommand extends HAPStoryElement{

	public final static String REQUEST = "request";
	
	public final static String RESPONSE = "request";
	
	private HAPEntityInfo m_entityInfo;
	
	//request end points
	private List<HAPStoryIdElement> m_requestIOEndPoints;
	
	//response end points
	private Map<String, List<HAPStoryIdElement>> m_responseIOEndPoints;
	
	
	public HAPStoryElementAccessoryCommand(HAPEntityInfo entityInfo) {
		super();
		this.m_entityInfo = entityInfo;
		
		
	}

	public static HAPPath buildPathForRequestEndPoint(String parName) {}
	
	
	
	@Override
	public HAPStoryElement cloneStoryElement() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
