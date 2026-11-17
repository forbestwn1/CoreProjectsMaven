package com.nosliw.core.application.spec.brick.service.profile;

import java.util.ArrayList;
import java.util.List;

import com.nosliw.common.path.HAPPath;
import com.nosliw.core.application.common.brick.HAPBrick;
import com.nosliw.core.application.common.brick.HAPInfoExportBrick;
import com.nosliw.core.application.common.brick.HAPPluginBrick;
import com.nosliw.core.application.common.interactive.HAPWithBlockInteractiveTask;
import com.nosliw.core.application.spec.HAPEnumBrickType;

public class HAPPluginBrickServiceProfile extends HAPPluginBrick{

	public HAPPluginBrickServiceProfile() {
		super(HAPEnumBrickType.SERVICEPROFILE_100);
	}

	@Override
	public List<HAPInfoExportBrick> getExposeResourceInfo(HAPBrick brick){
		List<HAPInfoExportBrick> out = new ArrayList<HAPInfoExportBrick>();
		
		HAPInfoExportBrick exposeInteractiveInterface = new HAPInfoExportBrick(new HAPPath(HAPWithBlockInteractiveTask.TASKINTERFACE));
		exposeInteractiveInterface.setName(HAPBlockServiceProfile.CHILD_INTERFACE);
		out.add(exposeInteractiveInterface);

		return out;
	}
}
