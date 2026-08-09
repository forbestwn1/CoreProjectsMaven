package com.nosliw.core.application.brick.imp.basic;

import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.spec.container.HAPBrickContainer;
import com.nosliw.core.application.brick.spec.module.HAPBlockModule;

public class HAPBasicBlockModule extends HAPBasicBrick implements HAPBlockModule{

	public HAPBasicBlockModule() {
		super(HAPEnumBrickType.MODULE_100);
		this.setAttributeValueWithBrick(HAPBlockModule.COMMAND, new HAPBasicBrickContainer());
		this.setAttributeValueWithBrick(HAPBlockModule.PAGE, new HAPBasicBrickContainer());
	}
	
	@Override
	public HAPBrickContainer getTasks() {   return (HAPBrickContainer)this.getAttributeValueOfBrick(HAPBlockModule.TASK);   }   

	@Override
	public HAPBrickContainer getCommands() {   return (HAPBrickContainer)this.getAttributeValueOfBrick(HAPBlockModule.COMMAND);   }

	@Override
	public HAPBrickContainer getPages() {   return (HAPBrickContainer)this.getAttributeValueOfBrick(HAPBlockModule.PAGE);   }

}
