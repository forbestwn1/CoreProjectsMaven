package com.nosliw.core.application.brick.wrappertask;

import com.nosliw.common.interfac.HAPEntityOrReference;
import com.nosliw.core.application.brick.HAPEnumBrickType;
import com.nosliw.core.application.common.brick.HAPBrickImp;

public class HAPBlockTaskWrapperImp extends HAPBrickImp implements HAPBlockTaskWrapper{

	public HAPBlockTaskWrapperImp(String division) {
		super(HAPEnumBrickType.TASKWRAPPER_100, division);
	}
	
	@Override
	public String getTaskType() {	return (String)this.getAttributeValueOfValue(TASKTYPE);	}
	public void setTaskType(String taskType) {    this.setAttributeValueWithValue(TASKTYPE, taskType);     }

	@Override
	public HAPEntityOrReference getTask() {     return this.getAttributeValueOfBrick(HAPBlockTaskWrapper.TASK);        }
	public void setTask(HAPEntityOrReference entityOrRef) {      this.setAttributeValueWithBrick(HAPBlockTaskWrapper.TASK, entityOrRef);        }

}
