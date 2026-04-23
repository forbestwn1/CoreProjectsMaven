package com.nosliw.core.application.division.story.brick;

import java.util.List;
import java.util.Map;
import java.util.Set;

//element represent 
// standalone stuff.   module, page, 
public class HAPStoryElement {

	//element may container children element
	private Set<HAPStoryContainerElement> m_children;
	
	//element may have commands in order to receive from external 
	private HAPStoryContainer<HAPStoryCommand> m_commands;
	
	//element may have events in order to send something to external
	private HAPStoryContainer<HAPStoryEvent> m_events;
	
	private HAPStoryContainer<HAPStoryVariable> m_state;
	
}
