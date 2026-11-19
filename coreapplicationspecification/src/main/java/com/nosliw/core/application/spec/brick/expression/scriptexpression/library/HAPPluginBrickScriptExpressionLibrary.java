package com.nosliw.core.application.spec.brick.expression.scriptexpression.library;

import java.util.ArrayList;
import java.util.List;

import com.nosliw.common.path.HAPPath;
import com.nosliw.core.application.common.brick.HAPAttributeInBrick;
import com.nosliw.core.application.common.brick.HAPBrick;
import com.nosliw.core.application.common.brick.HAPInfoExportBrick;
import com.nosliw.core.application.common.brick.HAPPluginBrick;
import com.nosliw.core.application.spec.HAPEnumBrickType;
import com.nosliw.core.application.spec.brick.wrappertask.HAPBlockTaskWrapper;

public class HAPPluginBrickScriptExpressionLibrary extends HAPPluginBrick{

	public HAPPluginBrickScriptExpressionLibrary() {
		super(HAPEnumBrickType.SCRIPTEXPRESSIONLIB_100);
	}

	@Override
	public List<HAPInfoExportBrick> getExposeResourceInfo(HAPBrick brick){

		List<HAPInfoExportBrick> out = new ArrayList<HAPInfoExportBrick>();
		
		HAPBlockScriptExpressionLibrary library = (HAPBlockScriptExpressionLibrary)brick;
		List<HAPAttributeInBrick> eleAttrs = library.getItems().getElements();
		for(HAPAttributeInBrick eleAttr : eleAttrs) {
			HAPInfoExportBrick exposeInteractiveInterface = new HAPInfoExportBrick(new HAPPath(HAPBlockScriptExpressionLibrary.ITEM).appendSegment(eleAttr.getName()).appendSegment(HAPBlockTaskWrapper.TASK));
			exposeInteractiveInterface.setName(eleAttr.getName());
			out.add(exposeInteractiveInterface);
		}

		return out;
	}
	
}
