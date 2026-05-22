package com.nosliw.core.application.division.story.definition.element;

import java.util.Map;

import com.nosliw.core.application.division.story.definition.HAPStoryElementRunnable;
import com.nosliw.core.application.division.story.definition.HAPStoryIdElement;
import com.nosliw.core.application.division.story.definition.HAPStoryReferenceElement;

public class HAPStoryElementRunnableCommand extends HAPStoryElementRunnable{

	//command element id
	private HAPStoryReferenceElement m_command;
	
	//data association for rquest
	private HAPStoryReferenceElement m_requestDataAssociation;
	
	//data association for response
	private Map<String, HAPStoryIdElement> m_responseDataAssociation;
	
}
