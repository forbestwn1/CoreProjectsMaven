package com.nosliw.core.application.common.command;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.core.application.HAPIdBrickInBundle;

public class HAPCommandHandlerReferenceCommand {

	@HAPAttribute
	public static final String BRICKID = "brickId";

	@HAPAttribute
	public static final String COMMANDNAME = "commandName";

	private HAPIdBrickInBundle m_brickId;

	private String m_commandName;
	
	

}
