package com.nosliw.core.application.common.command;

import com.nosliw.common.constant.HAPAttribute;

public class HAPCommandHandlerReference {

	@HAPAttribute
	public static final String HANDLERTYPE = "handlerType";
	
	private String m_handlerType;
  
	public String getHandlerType() {     return this.m_handlerType;     }
	
	
}
