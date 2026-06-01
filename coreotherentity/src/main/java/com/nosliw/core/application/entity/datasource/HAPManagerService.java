package com.nosliw.core.application.entity.datasource;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.core.application.common.datadefinition.HAPDefinitionParm;
import com.nosliw.core.application.common.interactive.HAPInteractiveTask;
import com.nosliw.core.application.common.interactive.HAPResultInteractiveTask;
import com.nosliw.core.application.entity.taskinterface.HAPManagerServiceInterface;
import com.nosliw.core.data.HAPData;
import com.nosliw.core.runtime.HAPRuntimeInfo;
import com.nosliw.core.service.entityparse.HAPServiceParseEntity;

//service manager, it is used for runtime purpose
@HAPEntityWithAttribute
@Component
public class HAPManagerService{

	//all service definition
	private Map<String, HAPInfoService> m_servicesInfo;
	
	private Map<String, HAPFactoryService> m_serviceFactorys;
	
	//all service runtime
	private Map<String, HAPInstanceService> m_serviceInstances;
	
	private HAPManagerServiceInterface m_serviceInterfaceMan;
	
	@Autowired
	private HAPServiceParseEntity m_entityParseService;
	
	public HAPManagerService(HAPManagerServiceInterface serviceInterfaceMan){
		this.m_serviceInterfaceMan = serviceInterfaceMan;
//		this.m_servicesInfo = new LinkedHashMap<String, HAPInfoService>();
		this.m_serviceInstances = new LinkedHashMap<String, HAPInstanceService>();
		this.m_serviceFactorys = new LinkedHashMap<String, HAPFactoryService>();
	}
	

	public HAPManagerServiceInterface getServiceInterfaceManager() { 	return this.m_serviceInterfaceMan;	}
	
	public void registerServiceInfo(HAPInfoService serviceInfo){	this.getAllServicesInfo().put(serviceInfo.getServiceProfileInfo().getId(), serviceInfo);	}
	
	public HAPInfoService getServiceInfo(String id){
		HAPInfoService out = this.getAllServicesInfo().get(id);
//		if(!out.isProcessed()) {
//			out.process(this.m_runtimeEnv);
//		}
		return out;
	}
	
	public HAPServiceProfile getServiceProfile(String id, HAPRuntimeInfo runtimeInfo) {
		return this.getAllServicesInfo().get(id).getServiceProfileInfo();
	}
	
	public List<HAPServiceProfile> queryDefinition(HAPQueryServiceDefinition query, HAPRuntimeInfo runtimeInfo){
		List<HAPServiceProfile> out = new ArrayList<HAPServiceProfile>();
		for(String id : this.getAllServicesInfo().keySet()) {
			boolean found = true;
			HAPServiceProfile profile = this.getAllServicesInfo().get(id).getServiceProfileInfo();
			List<String> tags = profile.getTags();
			for(String keyword : query.getKeywords()) { 
				if(!tags.contains(keyword)) {
					found = false;
				}
			}
			
			if(found) {
				out.add(profile);
			}
		}
		return out;
	}

	private Map<String, HAPInfoService> getAllServicesInfo(){
		if(this.m_servicesInfo==null) {
			this.m_servicesInfo = new LinkedHashMap<String, HAPInfoService>();
			List<HAPInfoService> defs = HAPImporterDataSourceDefinition.loadDataSourceDefinition(this.m_entityParseService);
			for(HAPInfoService def : defs) {
				this.registerServiceInfo(def);
			}
		}
		return this.m_servicesInfo;
	}

	
	
	public void registerServiceInstance(String id, HAPInstanceService serviceInstance){		this.m_serviceInstances.put(id, serviceInstance);	}

	public void registerServiceFactory(String name, HAPFactoryService serviceFactory){		this.m_serviceFactorys.put(name, serviceFactory);	}
	
	//service query is used to find service provider
	public HAPResultInteractiveTask execute(HAPQueryService serviceQuery, Map<String, HAPData> parms){
		//get service instance according to serviceId
		HAPInstanceService serviceInstance = this.m_serviceInstances.get(serviceQuery.getServiceId());
		if(serviceInstance==null){
			try{
				//not exists, then create one using factory
				HAPInfoService serviceInfo = this.getServiceInfo(serviceQuery.getServiceId());
				HAPExecutableService serviceExe;
				String imp = serviceInfo.getServiceRuntimeInfo().getImplementation();
				if(imp.contains(".")){
					//it is class name
					serviceExe = (HAPExecutableService)Class.forName(imp).newInstance();
				}
				else{
					//it is factory name
					serviceExe = this.m_serviceFactorys.get(imp).newService(serviceInfo.getServiceProfileInfo());
				}
				serviceInstance = new HAPInstanceService(serviceInfo.getServiceProfileInfo(), serviceExe);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			if(serviceInstance!=null) {
				this.registerServiceInstance(serviceQuery.getServiceId(), serviceInstance);
			}
		}
		
		//execute service instance
		HAPResultInteractiveTask out = null;
		if(serviceInstance!=null) {
			Map<String, HAPData> serviceParms = new LinkedHashMap<String, HAPData>();
			
			HAPInteractiveTask serviceInterface = serviceInstance.getDefinition().getInterface();
			for(HAPDefinitionParm parm : serviceInterface.getRequestParms()) {
				String parmName = parm.getId();
				HAPData parmData = null;
				if(parms!=null) {
					parmData = parms.get(parmName);
				}
				if(parmData==null)
				 {
					parmData = parm.getDataDefinition().getInitData();   //not provide, use default 
				}
				serviceParms.put(parmName, parmData);
			}
			out = serviceInstance.getExecutable().execute(serviceParms);
		}
		return out;
	}

}
