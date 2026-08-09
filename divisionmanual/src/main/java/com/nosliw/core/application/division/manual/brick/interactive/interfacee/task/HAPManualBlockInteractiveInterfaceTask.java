package com.nosliw.core.application.division.manual.brick.interactive.interfacee.task;

import org.springframework.stereotype.Component;

import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.spec.interactive.interfacee.task.HAPBlockInteractiveInterfaceTask;
import com.nosliw.core.application.common.brick.serialize.valueparser.HAParserPValueInAttributeImpBlockInteractiveInterfaceTask;
import com.nosliw.core.application.common.interactive.HAPInteractiveTask;
import com.nosliw.core.application.division.manual.core.HAPManualBrickImp;
import com.nosliw.core.application.division.manual.core.HAPManualBrick_parser;

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
