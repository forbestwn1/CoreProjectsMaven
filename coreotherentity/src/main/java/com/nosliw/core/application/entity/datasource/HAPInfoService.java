package com.nosliw.core.application.entity.datasource;

public class HAPInfoService {

	public static final String RUNTIME = "runtime";

	public static final String PROFILE = "profile";

	private HAPInfoServiceRuntime m_serviceRuntime;
	
	private HAPServiceProfile m_serviceProfile;
	
	
	public HAPInfoService(HAPServiceProfile serviceProfile, HAPInfoServiceRuntime serviceRuntime) {
		this.m_serviceProfile = serviceProfile;
		this.m_serviceRuntime = serviceRuntime;
	}
	
	public HAPInfoServiceRuntime getServiceRuntimeInfo() {  	return this.m_serviceRuntime;	}
	
	public HAPServiceProfile getServiceProfileInfo() {    return this.m_serviceProfile;     }

}
