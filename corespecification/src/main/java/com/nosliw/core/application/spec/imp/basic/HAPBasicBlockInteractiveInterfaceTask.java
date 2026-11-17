package com.nosliw.core.application.spec.imp.basic;

import org.springframework.stereotype.Component;

import com.nosliw.core.application.common.interactive.HAPInteractiveTask;
import com.nosliw.core.application.entity.brick.serialize.HAParserPValueInAttributeImpBlockInteractiveInterfaceTask;
import com.nosliw.core.application.spec.HAPEnumBrickType;
import com.nosliw.core.application.spec.brick.interactive.interfacee.task.HAPBlockInteractiveInterfaceTask;

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
