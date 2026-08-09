package com.nosliw.core.application.division.manual.brick.wrappertask;

import org.springframework.stereotype.Component;

import com.nosliw.common.interfac.HAPEntityOrReference;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.brick.wrappertask.HAPBlockTaskWrapper;
import com.nosliw.core.application.common.brick.serialize.valueparser.HAParserPValueInAttributeImpBlockTaskWrapper;
import com.nosliw.core.application.division.manual.core.HAPManualBrickImp;
import com.nosliw.core.application.division.manual.core.HAPManualBrick_parser;

public class HAPManualBlockTaskWrapper extends HAPManualBrickImp implements HAPBlockTaskWrapper{

	public HAPManualBlockTaskWrapper() {
		super(HAPEnumBrickType.TASKWRAPPER_100);
	}

	@Override
	public void init() {
		super.init();
	}
	
	@Override
	public String getTaskType() {	return (String)this.getAttributeValueOfValue(TASKTYPE);	}
	public void setTaskType(String taskType) {    this.setAttributeValueWithValue(TASKTYPE, taskType);     }

	@Override
	public HAPEntityOrReference getTask() {     return this.getAttributeValueOfBrick(HAPBlockTaskWrapper.TASK);        }
	public void setTask(HAPEntityOrReference entityOrRef) {      this.setAttributeValueWithBrick(HAPBlockTaskWrapper.TASK, entityOrRef);        }

}

@Component
class HAPManualBlockTaskWrapper_parser extends HAPManualBrick_parser{

	public HAPManualBlockTaskWrapper_parser() {
		super(HAPManualBlockTaskWrapper.class, HAPEnumBrickType.TASKWRAPPER_100);
		this.addAttributeValueParser(new HAParserPValueInAttributeImpBlockTaskWrapper());
	}

}
