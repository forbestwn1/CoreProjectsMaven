package com.nosliw.core.application.entity.datasource;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nosliw.common.utils.HAPConstantShared;
import com.nosliw.core.application.HAPBundleForBrick;
import com.nosliw.core.application.HAPIdBrick;
import com.nosliw.core.application.HAPIdBrickType;
import com.nosliw.core.application.HAPWrapperBrickRoot;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.imp.basic.HAPBasicBlockInteractiveInterfaceTask;
import com.nosliw.core.application.brick.spec.service.profile.HAPBlockServiceProfile;
import com.nosliw.core.application.common.datasource.HAPServiceProfile;
import com.nosliw.core.application.entity.brick.HAPPluginDivision;
import com.nosliw.core.runtime.HAPRuntimeInfo;

@Component
public class HAPDivisionService implements HAPPluginDivision{

	@Autowired
	private HAPServiceDataSource m_dataSourceService;
	
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
			bundle.setMainBrickWrapper(new HAPWrapperBrickRoot(fromObjToBlockServiceProfile(m_dataSourceService.getServiceProfile(brickId.getId()))));
			return bundle;
		} 
		else if(brickTypeId.equals(HAPEnumBrickType.SERVICEINTERFACE_100)) {
//			HAPBundleForBrick bundle = HAPBundleForBrick.newBundleForBrick();
//			bundle.setMainBrickWrapper(new HAPWrapperBrickRoot(HAPUtilityTaskInterface.buildTaskInterfaceBlock(m_taskInterfaceConfigure, brickId.getId())));
//			return bundle;
		}
		return null;
	}

	private HAPBlockServiceProfile fromObjToBlockServiceProfile(HAPServiceProfile serviceProfile) {
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
