package com.nosliw.core.application.common.command;

import java.util.List;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;

@HAPEntityWithAttribute
public interface HAPCommandWithExport {

	@HAPAttribute
	public final static String EXPORTCOMMAND = "commandExport"; 
	
	List<String> getCommandExportNames();

	HAPCommandProcess getCommandExport(String name);

}
