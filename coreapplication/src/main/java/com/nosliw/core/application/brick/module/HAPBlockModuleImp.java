package com.nosliw.core.application.brick.module;

import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.container.HAPBrickContainer;
import com.nosliw.core.application.brick.container.HAPBrickContainerImp;
import com.nosliw.core.application.common.brick.HAPBrickImp;

public class HAPBlockModuleImp extends HAPBrickImp implements HAPBlockModule{

	public HAPBlockModuleImp(String division) {
		super(HAPEnumBrickType.MODULE_100, division);
		this.setAttributeValueWithBrick(HAPBlockModule.COMMAND, new HAPBrickContainerImp(division));
		this.setAttributeValueWithBrick(HAPBlockModule.PAGE, new HAPBrickContainerImp(division));
	}
	
	@Override
	public HAPBrickContainer getTasks() {   return (HAPBrickContainer)this.getAttributeValueOfBrick(HAPBlockModule.TASK);   }   

	@Override
	public HAPBrickContainer getCommands() {   return (HAPBrickContainer)this.getAttributeValueOfBrick(HAPBlockModule.COMMAND);   }

	@Override
	public HAPBrickContainer getPages() {   return (HAPBrickContainer)this.getAttributeValueOfBrick(HAPBlockModule.PAGE);   }

}
