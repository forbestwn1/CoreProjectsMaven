package com.nosliw.core.application.division.manual.brick.service.provider;

import org.apache.commons.lang3.tuple.Pair;

import com.nosliw.common.path.HAPPath;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.HAPIdBrick;
import com.nosliw.core.application.brick.HAPUtilityBrickId;
import com.nosliw.core.application.brick.spec.interactive.interfacee.task.HAPBlockInteractiveInterfaceTask;
import com.nosliw.core.application.brick.spec.service.profile.HAPBlockServiceProfile;
import com.nosliw.core.application.brick.spec.service.provider.HAPKeyService;
import com.nosliw.core.application.division.manual.common.task.HAPManualUtilityTask;
import com.nosliw.core.application.division.manual.core.HAPManualBrick;
import com.nosliw.core.application.division.manual.core.definition.HAPManualDefinitionBrick;
import com.nosliw.core.application.division.manual.core.process.HAPManualContextProcessBrick;
import com.nosliw.core.application.division.manual.core.process.HAPManualPluginProcessorBlockImp;
import com.nosliw.core.application.entity.brick.HAPManagerApplicationBrick;
import com.nosliw.core.application.entity.brick.HAPUtilityOtherBrick;
import com.nosliw.core.resource.HAPResourceIdSimple;
import com.nosliw.core.resource.HAPUtilityResourceId;

public class HAPManualPluginProcessorBlockSimpleImpServiceProvider extends HAPManualPluginProcessorBlockImp{

	private HAPManagerApplicationBrick m_brickMan;
	
	public HAPManualPluginProcessorBlockSimpleImpServiceProvider(HAPManagerApplicationBrick brickMan) {
		super(HAPEnumBrickType.SERVICEPROVIDER_100, HAPManualBlockSimpleServiceProvider.class);
		this.m_brickMan = brickMan;
	}

	@Override
	public void processInit(HAPPath pathFromRoot, HAPManualContextProcessBrick processContext) {
		super.processInit(pathFromRoot, processContext);
		Pair<HAPManualDefinitionBrick, HAPManualBrick> blockPair = this.getBrickPair(pathFromRoot, processContext);
		HAPManualDefinitionBlockSimpleServiceProvider serviceProviderDef = (HAPManualDefinitionBlockSimpleServiceProvider)blockPair.getLeft();
		HAPManualBlockSimpleServiceProvider serviceProviderExe = (HAPManualBlockSimpleServiceProvider)blockPair.getRight();
	
		HAPKeyService serviceKey = serviceProviderDef.getServiceKey();

		//service key
		serviceProviderExe.setServiceKey(serviceKey);

		//service interactive interface
//		HAPResourceIdSimple serviceProfileResourceId = HAPUtilityBrickId.fromBrickId2ResourceId(new HAPIdBrick(HAPEnumBrickType.SERVICEPROFILE_100, null, serviceKey.getServiceId()));
//		HAPBlockServiceProfile serviceProfileBlock = (HAPBlockServiceProfile)HAPUtilityBrick.getBrickByResource(HAPUtilityResourceId.normalizeResourceId(serviceProfileResourceId), m_brickMan, processContext.getRuntimeInfo());
//		serviceProviderExe.setTaskInterface(serviceProfileBlock.getServiceInterface());
		
//		HAPBlockInteractiveInterfaceTask taskInterfaceBlock = (HAPBlockInteractiveInterfaceTask)HAPUtilityBrick.getBrick(serviceProfileBlock.getServiceInterface(), brickMan);
//		HAPInteractiveTask taskInteractive = taskInterfaceBlock.getValue();
//		serviceProviderExe.setTaskInterface(taskInteractive);
	}
	
	@Override
	public void processOtherValuePortBuild(HAPPath pathFromRoot, HAPManualContextProcessBrick processContext) {
		super.processOtherValuePortBuild(pathFromRoot, processContext);
		Pair<HAPManualDefinitionBrick, HAPManualBrick> blockPair = this.getBrickPair(pathFromRoot, processContext);
		HAPManualDefinitionBlockSimpleServiceProvider definitionBlock = (HAPManualDefinitionBlockSimpleServiceProvider)blockPair.getLeft();
		HAPManualBlockSimpleServiceProvider serviceProviderExe = (HAPManualBlockSimpleServiceProvider)blockPair.getRight();

//		HAPBlockInteractiveInterfaceTask taskInterfaceBlock = (HAPBlockInteractiveInterfaceTask)HAPUtilityBrick.getBrick(serviceProviderExe.getTaskInterface(), brickMan);

		HAPResourceIdSimple serviceProfileResourceId = HAPUtilityBrickId.fromBrickId2ResourceId(new HAPIdBrick(HAPEnumBrickType.SERVICEPROFILE_100, null, serviceProviderExe.getServiceKey().getServiceId()));
		HAPBlockServiceProfile serviceProfileBlock = (HAPBlockServiceProfile)HAPUtilityOtherBrick.getBrickByResource(HAPUtilityResourceId.normalizeResourceId(serviceProfileResourceId), m_brickMan, processContext.getRuntimeInfo());
		HAPBlockInteractiveInterfaceTask taskInterfaceBlock = (HAPBlockInteractiveInterfaceTask)HAPUtilityOtherBrick.getBrick(serviceProfileBlock.getTaskInterface(), m_brickMan, processContext.getRuntimeInfo());
		
		HAPManualUtilityTask.buildValuePortGroupForInteractiveTask(serviceProviderExe, taskInterfaceBlock.getValue(), processContext.getCurrentBundle().getValueStructureDomain());
	}
}
