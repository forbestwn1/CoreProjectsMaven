package com.nosliw.core.application;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.runtime.HAPRuntimeInfo;

@Component
public class HAPPluginDivisionImp implements HAPPluginDivision{

	@Override
	public String getDivisionName() {   return HAPConstantShared.NAME_DEFAULT;    }

	@Override
	public HAPBundleForBrick getBundle(HAPIdBrick brickId, HAPRuntimeInfo runtimeInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<HAPIdBrickType> getBrickTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HAPBrick deserializeBrick(Object obj, HAPSerializationFormat format) {
		// TODO Auto-generated method stub
		return null;
	}

}
