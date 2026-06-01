package com.nosliw.core.application.entity.datasource;

//service instance factory that generate service instance according to service definition
public interface HAPFactoryService {

	//create service instance
	HAPExecutableService newService(HAPServiceProfile serviceDefinition);
	
}
