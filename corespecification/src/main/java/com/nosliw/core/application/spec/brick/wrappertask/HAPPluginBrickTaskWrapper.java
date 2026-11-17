package com.nosliw.core.application.spec.brick.wrappertask;

import java.util.ArrayList;
import java.util.List;

import com.nosliw.common.path.HAPPath;
import com.nosliw.core.application.common.brick.HAPBrick;
import com.nosliw.core.application.common.brick.HAPInfoExportBrick;
import com.nosliw.core.application.common.brick.HAPPluginBrick;
import com.nosliw.core.application.spec.HAPEnumBrickType;

public class HAPPluginBrickTaskWrapper extends HAPPluginBrick{

	public HAPPluginBrickTaskWrapper() {
		super(HAPEnumBrickType.TASKWRAPPER_100);
	}

	@Override
	public List<HAPInfoExportBrick> getExposeResourceInfo(HAPBrick brick){
		List<HAPInfoExportBrick> out = new ArrayList<HAPInfoExportBrick>();
		HAPInfoExportBrick exposeTaskResourceInfo = new HAPInfoExportBrick(new HAPPath(HAPBlockTaskWrapper.TASK));
		exposeTaskResourceInfo.setName(HAPBlockTaskWrapper.CHILD_TASK);
		out.add(exposeTaskResourceInfo);
		return out;
	}
	
}
