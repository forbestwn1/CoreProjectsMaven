package com.nosliw.core.application.spec.brick.task.flow;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.core.application.common.brick.HAPBrick;

@HAPEntityWithAttribute
public interface HAPBlockTaskFlowActivity extends HAPBrick, HAPEntityInfo{

	@HAPAttribute
	public static final String NEXT = "next";
	
	HAPTaskFlowNext getNext();
	
}
