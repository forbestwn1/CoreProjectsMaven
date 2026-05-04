package com.nosliw.core.application.division.story.brick;

import java.util.Set;

//element represent 
// standalone stuff.   module, page, 
public class HAPStoryElement {

	private HAPStoryIdElement m_id;
	
	private String m_alias;
	
	//element may container children element
	private Set<HAPStoryContainerElement> m_children;
	
	private HAPStoryContainer<HAPStoryVariable> m_state;

	
	//element may have commands in order to receive from external 
	private HAPStoryContainer<HAPStoryCommand> m_commands;
	
	//element may have events in order to send something to external
	private HAPStoryContainer<HAPStoryEvent> m_events;
	
}
