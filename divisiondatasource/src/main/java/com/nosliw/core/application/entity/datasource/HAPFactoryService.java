package com.nosliw.core.application.entity.datasource;

import com.nosliw.core.application.common.datasource.HAPExecutableService;
import com.nosliw.core.application.common.datasource.HAPServiceProfile;

//service instance factory that generate service instance according to service definition
public interface HAPFactoryService {

	//create service instance
	HAPExecutableService newService(HAPServiceProfile serviceDefinition);
	
}
