package com.nosliw.core.application.division.story.brick.element;

import java.util.List;

import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.common.path.HAPPath;
import com.nosliw.core.application.division.story.HAPStoryIdElement;
import com.nosliw.core.application.division.story.brick.HAPStoryElement;

public class HAPStoryElementCommand extends HAPStoryElement{

	public final static String REQUEST = "request";
	
	public final static String RESPONSE = "request";
	
	private HAPEntityInfo m_entityInfo;
	
	private List<HAPStoryIdElement> m_requestIOEndPoints;
	
	private List<HAPStoryIdElement> m_responseIOEndPoints;
	
	public HAPStoryElementCommand(HAPEntityInfo entityInfo) {
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
