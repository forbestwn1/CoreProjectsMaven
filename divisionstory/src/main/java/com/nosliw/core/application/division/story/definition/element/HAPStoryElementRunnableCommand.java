package com.nosliw.core.application.division.story.definition.element;

import java.util.Map;

import com.nosliw.core.application.division.story.definition.HAPStoryElementRunnable;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;

public class HAPStoryElementRunnableCommand extends HAPStoryElementRunnable{

	public final static String CHILD_COMMAND = "command";

	public final static String CHILD_REQUESTDATAASSOCIATION = "requestDataAssociation";
	
	public final static String CHILD_RESPONSEDATAASSOCIATION = "responseDataAssociation";

	
	//command element id
	private HAPStoryIdElement m_command;
	
	//data association for rquest
	private HAPStoryIdElement m_requestDataAssociation;
	
	//data association for response
	private Map<String, HAPStoryIdElement> m_responseDataAssociation;
	
}
