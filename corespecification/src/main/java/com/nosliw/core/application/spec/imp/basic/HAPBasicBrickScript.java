package com.nosliw.core.application.spec.imp.basic;

import java.util.List;

import com.nosliw.common.serialization.HAPJsonTypeScript;
import com.nosliw.core.application.common.brick.HAPAttributeInBrick;
import com.nosliw.core.application.common.brick.HAPBrickImp;
import com.nosliw.core.application.common.brick.HAPIdBrickType;
import com.nosliw.core.application.common.brick.HAPWrapperValueOfValue;
import com.nosliw.core.application.common.script.HAPWithScript;
import com.nosliw.core.application.common.valueport.HAPContainerValuePorts;
import com.nosliw.core.resource.HAPResourceDependency;
import com.nosliw.core.runtime.HAPRuntimeInfo;

public class HAPBasicBrickScript extends HAPBrickImp implements HAPWithScript{

	public HAPBasicBrickScript(HAPIdBrickType brickTypeId, String division) {
		super(brickTypeId, division);
	}
	
	public void setScript(String script) {
		this.setAttribute(new HAPAttributeInBrick(SCRIPT, new HAPWrapperValueOfValue(new HAPJsonTypeScript(script))));
	}

	@Override
	public HAPContainerValuePorts getInternalValuePorts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HAPContainerValuePorts getExternalValuePorts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void buildResourceDependency(List<HAPResourceDependency> dependency, HAPRuntimeInfo runtimeInfo) {
		// TODO Auto-generated method stub
		
	}
}
