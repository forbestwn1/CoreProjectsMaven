package com.nosliw.core.application.division.story.definition;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.path.HAPPath;

//entity element (module, page, datasource, ....)
public abstract class HAPStoryElementEntityComplex 
                        extends HAPStoryElementEntity 
                        implements HAPStoryElementWithCommand, 
                                   HAPStoryElementWithConstant, 
                                   HAPStoryElementWithVariable, 
                                   HAPStoryElementWithEvent{

	private Map<String, HAPStoryIdElement> m_commands;
	
	private Map<String, HAPStoryIdElement> m_variables;
	
	private Map<String, HAPStoryIdElement> m_constants;
	
	private Map<String, HAPStoryIdElement> m_events;
	
	public HAPStoryElementEntityComplex(HAPStoryIdElementType elementType) {
		super(elementType);
		this.m_commands = new LinkedHashMap<String, HAPStoryIdElement>();
		this.m_variables = new LinkedHashMap<String, HAPStoryIdElement>();
		this.m_constants = new LinkedHashMap<String, HAPStoryIdElement>();
		this.m_events = new LinkedHashMap<String, HAPStoryIdElement>();
	}

	@Override
	public HAPStoryIdElement getChild(String childName) {
		HAPPath path = this.parseChildNameToPath(childName);
		String[] segs = path.getPathSegments();
		if(HAPStoryElementWithCommand.CHILD_COMMAND.equals(segs[0])){
			return this.m_commands.get(segs[1]);
		}
		else if(HAPStoryElementWithConstant.CHILD_CONSTANT.equals(segs[0])){
			return this.m_constants.get(segs[1]);
		}
		else if(HAPStoryElementWithVariable.CHILD_VARIABLE.equals(segs[0])){
			return this.m_variables.get(segs[1]);
		}
		else if(HAPStoryElementWithEvent.CHILD_EVENT.equals(segs[0])){
			return this.m_events.get(segs[1]);
		}
		return null;
	}
	
	@Override
	public boolean addChild(HAPStoryElement ele, String childName) {
		HAPPath path = this.parseChildNameToPath(childName);
		String[] segs = path.getPathSegments();
		if(HAPStoryElementWithCommand.CHILD_COMMAND.equals(segs[0])){
			this.m_commands.put(segs[1], ele.getElementId());
			return true;
		}
		else if(HAPStoryElementWithConstant.CHILD_CONSTANT.equals(segs[0])){
			this.m_constants.put(segs[1], ele.getElementId());
			return true;
		}
		else if(HAPStoryElementWithVariable.CHILD_VARIABLE.equals(segs[0])){
			this.m_variables.put(segs[1], ele.getElementId());
			return true;
		}
		else if(HAPStoryElementWithEvent.CHILD_EVENT.equals(segs[0])){
			this.m_events.put(segs[1], ele.getElementId());
			return true;
		}
		return false;
	}
	
	protected void cloneToStoryElement(HAPStoryElementEntityComplex storyEle) {
		super.cloneToStoryElement(storyEle);
		
		storyEle.m_commands.putAll(this.m_commands);
		storyEle.m_constants.putAll(this.m_constants);
		storyEle.m_variables.putAll(this.m_variables);
		storyEle.m_events.putAll(this.m_events);
	}
	
}
