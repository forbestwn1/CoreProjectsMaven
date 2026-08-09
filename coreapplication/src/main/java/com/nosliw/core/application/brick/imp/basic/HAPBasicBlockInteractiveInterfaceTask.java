package com.nosliw.core.application.brick.imp.basic;

import org.springframework.stereotype.Component;

import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.spec.interactive.interfacee.task.HAPBlockInteractiveInterfaceTask;
import com.nosliw.core.application.common.brick.serialize.valueparser.HAParserPValueInAttributeImpBlockInteractiveInterfaceTask;
import com.nosliw.core.application.common.interactive.HAPInteractiveTask;

public class HAPBasicBlockInteractiveInterfaceTask extends HAPBasicBrick implements HAPBlockInteractiveInterfaceTask{

	public HAPBasicBlockInteractiveInterfaceTask(){
		super(HAPEnumBrickType.INTERACTIVETASKINTERFACE_100);
	}
	
	@Override
	public HAPInteractiveTask getValue() {    return (HAPInteractiveTask)this.getAttributeValueOfValue(VALUE);  }
	public void setValue(HAPInteractiveTask value) {   this.setAttributeValueWithValue(VALUE, value);      }

}

@Component
class HAPBasicBlockInteractiveInterfaceTask_parser extends HAPBasicBrick_parser{

	public HAPBasicBlockInteractiveInterfaceTask_parser() {
		super(HAPBasicBlockInteractiveInterfaceTask.class, HAPEnumBrickType.INTERACTIVETASKINTERFACE_100);
		this.addAttributeValueParser(new HAParserPValueInAttributeImpBlockInteractiveInterfaceTask());
	}
	
}
