package com.nosliw.core.application.spec.imp.basic;

import org.springframework.stereotype.Component;

import com.nosliw.common.interfac.HAPEntityOrReference;
import com.nosliw.core.application.entity.brick.serialize.HAParserPValueInAttributeImpBlockTaskWrapper;
import com.nosliw.core.application.spec.HAPEnumBrickType;
import com.nosliw.core.application.spec.brick.wrappertask.HAPBlockTaskWrapper;

public class HAPBasicBlockTaskWrapper extends HAPBasicBrick implements HAPBlockTaskWrapper{

	public HAPBasicBlockTaskWrapper() {
		super(HAPEnumBrickType.TASKWRAPPER_100);
	}
	
	@Override
	public String getTaskType() {	return (String)this.getAttributeValueOfValue(TASKTYPE);	}
	public void setTaskType(String taskType) {    this.setAttributeValueWithValue(TASKTYPE, taskType);     }

	@Override
	public HAPEntityOrReference getTask() {     return this.getAttributeValueOfBrick(HAPBlockTaskWrapper.TASK);        }
	public void setTask(HAPEntityOrReference entityOrRef) {      this.setAttributeValueWithBrick(HAPBlockTaskWrapper.TASK, entityOrRef);        }

}

@Component
class HAPBasicBlockTaskWrapper_parser extends HAPBasicBrick_parser{

	public HAPBasicBlockTaskWrapper_parser() {
		super(HAPBasicBlockTaskWrapper.class, HAPEnumBrickType.TASKWRAPPER_100);
		this.addAttributeValueParser(new HAParserPValueInAttributeImpBlockTaskWrapper());
	}

}
