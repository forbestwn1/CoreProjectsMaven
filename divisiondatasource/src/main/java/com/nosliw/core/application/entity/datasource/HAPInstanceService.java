package com.nosliw.core.application.entity.datasource;

import com.nosliw.core.application.common.datasource.HAPExecutableService;
import com.nosliw.core.application.common.datasource.HAPServiceProfile;

//service instance
public class HAPInstanceService {

	//object that contain all information related with this service
	private HAPServiceProfile m_definition;
	
	//object that answer the service call  
	private HAPExecutableService m_executable;
	
	public HAPInstanceService(HAPServiceProfile definition, HAPExecutableService executable) {
		this.m_definition = definition;
		this.m_executable = executable;
	}
	
	public HAPServiceProfile getDefinition() {		return this.m_definition;	}
	
	public HAPExecutableService getExecutable() {   return this.m_executable;    }
	
}
