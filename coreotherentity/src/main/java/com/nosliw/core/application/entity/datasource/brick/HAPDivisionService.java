package com.nosliw.core.application.entity.datasource.brick;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.HAPBundleForBrick;
import com.nosliw.core.application.HAPIdBrick;
import com.nosliw.core.application.HAPIdBrickType;
import com.nosliw.core.application.HAPPluginDivision;
import com.nosliw.core.application.HAPWrapperBrickRoot;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.imp.basic.HAPBasicBlockInteractiveInterfaceTask;
import com.nosliw.core.application.brick.spec.service.profile.HAPBlockServiceProfile;
import com.nosliw.core.application.entity.datasource.HAPManagerService;
import com.nosliw.core.application.entity.datasource.HAPServiceProfile;
import com.nosliw.core.application.entity.taskinterface.HAPTaskInterfaceConfigure;
import com.nosliw.core.application.entity.taskinterface.HAPUtilityTaskInterface;
import com.nosliw.core.runtime.HAPRuntimeInfo;

@Component
public class HAPDivisionService implements HAPPluginDivision{

	@Autowired
	private HAPTaskInterfaceConfigure m_taskInterfaceConfigure;

	@Autowired
    private HAPManagerService m_serviceMan;

	@Override
	public String getDivisionName() {   return HAPConstantShared.BRICK_DIVISION_SERVICE;   }
	
	@Override
	public Set<HAPIdBrickType> getBrickTypes() {  
		Set<HAPIdBrickType> out = new HashSet<HAPIdBrickType>();
		out.add(HAPEnumBrickType.SERVICEINTERFACE_100);
		out.add(HAPEnumBrickType.SERVICEPROFILE_100);
		return out;
	}

	@Override
	public HAPBundleForBrick getBundle(HAPIdBrick brickId, HAPRuntimeInfo runtimeInfo) {
		HAPIdBrickType brickTypeId = brickId.getBrickTypeId();
		if(brickTypeId.equals(HAPEnumBrickType.SERVICEPROFILE_100)) {
			HAPBundleForBrick bundle = HAPBundleForBrick.newBundleForBrick();
			bundle.setMainBrickWrapper(new HAPWrapperBrickRoot(fromObjToBlockServiceProfile(m_serviceMan.getServiceInfo(brickId.getId()).getServiceProfileInfo(), runtimeInfo)));
			return bundle;
		} 
		else if(brickTypeId.equals(HAPEnumBrickType.SERVICEINTERFACE_100)) {
			HAPBundleForBrick bundle = HAPBundleForBrick.newBundleForBrick();
			bundle.setMainBrickWrapper(new HAPWrapperBrickRoot(HAPUtilityTaskInterface.buildTaskInterfaceBlock(m_taskInterfaceConfigure, brickId.getId())));
			return bundle;
		}
		return null;
	}

	private HAPBlockServiceProfile fromObjToBlockServiceProfile(HAPServiceProfile serviceProfile, HAPRuntimeInfo runtimeInfo) {
		HAPBlockServiceProfileImp blockServiceProfile = new HAPBlockServiceProfileImp(this.getDivisionName());
		serviceProfile.cloneToEntityInfo(blockServiceProfile);
		blockServiceProfile.setTags(serviceProfile.getTags());
		blockServiceProfile.setDisplayResource(serviceProfile.getDisplayResource());
		
		HAPBasicBlockInteractiveInterfaceTask interfacBlock = new HAPBasicBlockInteractiveInterfaceTask();
		interfacBlock.setValue(serviceProfile.getInterface());
		blockServiceProfile.setTaskInterface(interfacBlock);
		return blockServiceProfile;
	}

}
