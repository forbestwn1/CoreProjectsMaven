package com.nosliw.core.application.common.command;

import java.util.Set;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializable;

@HAPEntityWithAttribute
public interface HAPCommandWithDefinition extends HAPSerializable{

	@HAPAttribute
	public static final String COMMAND = "command";
	
	public Set<String> getCommandNames();
	
	public HAPCommandDefinition getCommandDefinition(String name);

}
