package com.nosliw.core.application.brick.spec.service.interfacee;

import java.util.ArrayList;
import java.util.List;

import com.nosliw.common.path.HAPPath;
import com.nosliw.core.application.brick.HAPBrick;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.HAPInfoExportBrick;
import com.nosliw.core.application.brick.HAPPluginBrick;

public class HAPPluginBrickServiceInterface extends HAPPluginBrick{

	public HAPPluginBrickServiceInterface() {
		super(HAPEnumBrickType.SERVICEINTERFACE_100);
	}

	@Override
	public List<HAPInfoExportBrick> getExposeResourceInfo(HAPBrick brick){
		List<HAPInfoExportBrick> out = new ArrayList<HAPInfoExportBrick>();
		
		HAPInfoExportBrick exposeInteractiveInterface = new HAPInfoExportBrick(new HAPPath(HAPBlockServiceInterface.INTERFACE));
		exposeInteractiveInterface.setName(HAPBlockServiceInterface.CHILD_INTERFACE);
		out.add(exposeInteractiveInterface);

		return out;
	}
	
}
