package com.nosliw.core.application.division.story.design.wizzard.datasourcedrive;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;

@HAPEntityWithAttribute
public class HAPStoryWizzardQuestionChooseService extends HAPSerializableImp{

	@HAPAttribute
	public static final String SERVICENAME = "serviceName";

	private String m_serviceName;
	
	
	
}
