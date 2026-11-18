package com.nosliw.core.application.division.manual.brick.interactive.interfacee.task;

import org.springframework.stereotype.Component;

import com.nosliw.com.application.spec.common.serialize.HAParserPValueInAttributeImpBlockInteractiveInterfaceTask;
import com.nosliw.core.application.common.interactive.HAPInteractiveTask;
import com.nosliw.core.application.division.manual.core.HAPManualBrickImp;
import com.nosliw.core.application.division.manual.core.HAPManualBrick_parser;
import com.nosliw.core.application.spec.HAPEnumBrickType;
import com.nosliw.core.application.spec.brick.interactive.interfacee.task.HAPBlockInteractiveInterfaceTask;

public class HAPManualBlockInteractiveInterfaceTask extends HAPManualBrickImp implements HAPBlockInteractiveInterfaceTask{

	public HAPManualBlockInteractiveInterfaceTask() {
		super(HAPEnumBrickType.INTERACTIVETASKINTERFACE_100);
	}

	@Override
	public HAPInteractiveTask getValue() {   return (HAPInteractiveTask)this.getAttributeValueOfValue(HAPBlockInteractiveInterfaceTask.VALUE);  }

	public void setValue(HAPInteractiveTask taskInteractive) {      this.setAttributeValueWithValue(HAPManualBlockInteractiveInterfaceTask.VALUE, taskInteractive);       }

}

@Component
class HAPManualBlockInteractiveInterfaceTask_parser extends HAPManualBrick_parser{

	public HAPManualBlockInteractiveInterfaceTask_parser() {
		super(HAPManualBlockInteractiveInterfaceTask.class, HAPEnumBrickType.INTERACTIVETASKINTERFACE_100);
		this.addAttributeValueParser(new HAParserPValueInAttributeImpBlockInteractiveInterfaceTask());
	}
	
}
