package com.nosliw.core.application.common.command;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;

public class HAPCommandProcess extends HAPSerializableImp{

	@HAPAttribute
	public final static String DEFINITION = "definition"; 

	@HAPAttribute
	public final static String HANDLER = "handler"; 

	private HAPCommandDefinition m_commandDefinition;

	private HAPCommandHandlerReference m_commandHandlerReference;
	
}
